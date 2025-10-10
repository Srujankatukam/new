package com.nokia.grpc2sztp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nokia.grpc2sztp.config.ConfigurationManager;
import com.nokia.grpc2sztp.dto.*;
import com.nokia.grpc2sztp.exception.SztpApiException;
import com.nokia.grpc2sztp.interceptor.AuthenticationInterceptor;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * Service wrapper for calling SZTP Core App APIs
 * Provides mock responses until the actual API server is implemented
 */
public class SZTPWrapperService {
    private static final Logger logger = LoggerFactory.getLogger(SZTPWrapperService.class);
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    private final OkHttpClient.Builder httpClientBuilder;
    private final Gson gson;
    private final ConfigurationManager config;
    
    // Singleton instance
    private static SZTPWrapperService instance;
    
    private SZTPWrapperService() {
        this.config = ConfigurationManager.getInstance();
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
        
        // Configure HTTP client with logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(logger::debug);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        // Create trust-all SSL context for development
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(config.getApiTimeoutSeconds(), TimeUnit.SECONDS)
            .readTimeout(config.getApiTimeoutSeconds(), TimeUnit.SECONDS)
            .writeTimeout(config.getApiTimeoutSeconds(), TimeUnit.SECONDS);
        
        // Disable SSL verification for development (trust all certificates)
        if (config.getSztpApiBaseUrl().startsWith("https://")) {
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager trustAllCerts = getTrustedManager();

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{trustAllCerts}, new java.security.SecureRandom());

                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                clientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts);
                clientBuilder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                
                logger.warn("SSL certificate verification disabled for development. Do not use in production!");
            } catch (Exception e) {
                logger.error("Failed to configure SSL settings", e);
            }
        }
        
        this.httpClientBuilder = clientBuilder;
        
        logger.info("SZTPWrapperService initialized with base URL: {}", config.getSztpApiBaseUrl());
    }
    
    public static synchronized SZTPWrapperService getInstance() {
        if (instance == null) {
            instance = new SZTPWrapperService();
        }
        return instance;
    }
    
    /**
     * Authenticate user with SZTP API
     */
    public AuthDto.AuthResponse authenticateUser(AuthDto.AuthRequest request) throws SztpApiException {
        logger.info("Authenticating user: {} with orgId: {}", request.getUsername(), request.getOrgId());
        
        try {
            // Try actual API first
            String endpoint = config.getSztpApiBaseUrl() + "/grpc/auth/login";
            String jsonRequest = gson.toJson(request);
            String response = makePostRequest(endpoint, jsonRequest);
            return gson.fromJson(response, AuthDto.AuthResponse.class);
        } catch (IOException e) {
            throw new SztpApiException("Failed to authenticate user", e);
        }
    }
    
    /**
     * Create a new group
     */
    public GroupDto.CreateGroupResponse createGroup(GroupDto.CreateGroupRequest request) throws SztpApiException {
        logger.info("Creating group with parent: {}, description: {}", request.getParent(), request.getDescription());
        
        String endpoint = config.getApiEndpoint("/grpc/groups");
        String jsonRequest = gson.toJson(request);
        
        try {
            String response = makePostRequest(endpoint, jsonRequest);
            return gson.fromJson(response, GroupDto.CreateGroupResponse.class);
        } catch (IOException e) {
            throw new SztpApiException("Failed to create group", e);
        }
    }
    
    /**
     * Get group information
     */
    public GroupDto.GetGroupResponse getGroup(String groupId) throws SztpApiException {
        logger.info("Getting group: {}", groupId);
        String endpoint = config.getApiEndpoint("/grpc/groups/" + groupId);
        try {
            String response = makeGetRequest(endpoint);
            return gson.fromJson(response, GroupDto.GetGroupResponse.class);
        } catch (IOException e) {
            throw new SztpApiException("Failed to get group", e);
        }
    }
    
    /**
     * Delete a group
     */
    public void deleteGroup(String groupId) throws SztpApiException {
        logger.info("Deleting group: {}", groupId);

        String endpoint = config.getApiEndpoint("/grpc/groups/" + groupId);
        try {
            makeDeleteRequest(endpoint);
        } catch (IOException e) {
            throw new SztpApiException("Failed to delete group", e);
        }
    }
    
    /**
     * Add user role to group
     */
    public void addUserRole(UserRoleDto.UserRoleRequest request) throws SztpApiException {
        logger.info("Adding user role: {} to group: {}", request.getUsername(), request.getGroupId());
        
        String endpoint = config.getApiEndpoint("/grpc/groups/" + request.getGroupId() + "/users");
        String jsonRequest = gson.toJson(request);
        
        try {
            makePostRequest(endpoint, jsonRequest);
        } catch (IOException e) {
            throw new SztpApiException("Failed to add user role", e);
        }
    }
    
    /**
     * Remove user role from group
     */
    public void removeUserRole(UserRoleDto.UserRoleRequest request) throws SztpApiException {
        logger.info("Removing user role: {} from group: {}", request.getUsername(), request.getGroupId());
        
        String endpoint = config.getApiEndpoint("/grpc/groups/" + request.getGroupId() + "/users/" + request.getUsername() + "?user_type=" + request.getUserType() + "&org_id=" + request.getOrgId());
        
        try {
            makeDeleteRequest(endpoint);
        } catch (IOException e) {
            throw new SztpApiException("Failed to remove user role", e);
        }
    }
    
    /**
     * Get user roles
     */
    public UserRoleDto.UserRoleResponse getUserRole(String username, String userType, String orgId) throws SztpApiException {
        logger.info("Getting user roles for: {}", username);
        
        String endpoint = config.getApiEndpoint("/grpc/users/" + username + "/roles?user_type=" + userType + "&org_id=" + orgId);
        
        try {
            String response = makeGetRequest(endpoint);
            return gson.fromJson(response, UserRoleDto.UserRoleResponse.class);
        } catch (IOException e) {
            throw new SztpApiException("Failed to get user roles", e);
        }
    }
    
    /**
     * Add serial number to group
     */
    public SerialDto.AddSerialResponse addSerial(SerialDto.AddSerialRequest request) throws SztpApiException {
        logger.info("Adding serial: {} to group: {}", 
            request.getComponent().getSerialNumber(), request.getGroupId());
        
        String endpoint = config.getApiEndpoint("/grpc/groups/" + request.getGroupId() + "/serials");
        String jsonRequest = gson.toJson(request);
        
        try {
            String response = makePostRequest(endpoint, jsonRequest);
            return gson.fromJson(response, SerialDto.AddSerialResponse.class);
        } catch (IOException e) {
            throw new SztpApiException("Failed to add serial", e);
        }
    }
    
    /**
     * Get serial information
     */
    public SerialDto.GetSerialResponse getSerial(String ien, String serialNumber) throws SztpApiException {
        logger.info("Getting serial: {} with IEN: {}", serialNumber, ien);
        
        String endpoint = config.getApiEndpoint("/grpc/serials/" + serialNumber + "?ien=" + ien);
        
        try {
            String response = makeGetRequest(endpoint);
            return gson.fromJson(response, SerialDto.GetSerialResponse.class);
        } catch (IOException e) {
            throw new SztpApiException("Failed to get serial", e);
        }
    }
    
    /**
     * Remove serial number from group
     */
    public void removeSerial(String groupId, ComponentDto component) throws SztpApiException {
        logger.info("Removing serial: {} from group: {}", component.getSerialNumber(), groupId);
        
        String endpoint = config.getApiEndpoint("/grpc/groups/" + groupId + "/serials/" + component.getSerialNumber() + "?ien=" + component.getIen());
        
        try {
            makeDeleteRequest(endpoint);
        } catch (IOException e) {
            throw new SztpApiException("Failed to remove serial", e);
        }
    }
    
    /**
     * Create domain certificate for group
     */
    public DomainCertDto.CreateDomainCertResponse createDomainCert(DomainCertDto.CreateDomainCertRequest request) throws SztpApiException {
        logger.info("Creating domain certificate for group: {}", request.getGroupId());
        
        String endpoint = config.getApiEndpoint("/grpc/groups/" + request.getGroupId() + "/certificates");
        String jsonRequest = gson.toJson(request);
        
        try {
            String response = makePostRequest(endpoint, jsonRequest);
            return gson.fromJson(response, DomainCertDto.CreateDomainCertResponse.class);
        } catch (IOException e) {
            throw new SztpApiException("Failed to create domain certificate", e);
        }
    }

    public OwnershipVoucherDto.GetOwnershipVoucherResponse getOwnershipVoucher(OwnershipVoucherDto.GetOwnershipVoucherRequest request) throws SztpApiException {
        logger.info("Getting ownership voucher for serial: {}", request.getComponent().getSerialNumber());

        String endpoint = config.getApiEndpoint("/grpc/vouchers");
        String jsonRequest = gson.toJson(request);
        try {
            String response = makeSecurePostRequest(endpoint, jsonRequest);
            return gson.fromJson(response, OwnershipVoucherDto.GetOwnershipVoucherResponse.class);
        } catch (Exception e) {
            throw new SztpApiException("Failed to get ownership voucher", e);
        }
    }
    
    /**
     * Add user auth token header to HTTP request
     * Simply passes the JWT token from current gRPC context
     */
    private void addUserContextHeaders(Request.Builder requestBuilder) {
        try {
            // Get auth token directly from gRPC context
            String authToken = AuthenticationInterceptor.getCurrentAuthToken();
            
            if (authToken == null || authToken.trim().isEmpty()) {
                logger.warn("No auth token available, skipping auth token header");
                return;
            }
            
            // Add auth token header for backend API validation
            requestBuilder.addHeader("X-gRPC-User-Token", authToken);
            logger.debug("Added auth token header to request");
            
        } catch (Exception e) {
            logger.warn("Failed to add auth token header", e);
            // Continue with request even if headers fail - don't break the flow
        }
    }
    
    // Private helper methods for HTTP requests
    private String makeGetRequest(String url) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
            .url(url)
            .get();
        
        // Add user context headers
        addUserContextHeaders(requestBuilder);
        
        Request request = requestBuilder.build();
        
        try (Response response = httpClientBuilder.build().newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            
            ResponseBody body = response.body();
            return body != null ? body.string() : "";
        }
    }
    
    private String makePostRequest(String url, String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request.Builder requestBuilder = new Request.Builder()
            .url(url)
            .post(body);
        
        // Add user context headers
        addUserContextHeaders(requestBuilder);
        
        Request request = requestBuilder.build();
        
        try (Response response = httpClientBuilder.build().newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            
            ResponseBody responseBody = response.body();
            return responseBody != null ? responseBody.string() : "";
        }
    }

    private String makeSecurePostRequest(String url, String jsonBody) throws Exception {
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request.Builder requestBuilder = new Request.Builder()
            .url(url)
            .post(body);
        
        // Add user context headers
        addUserContextHeaders(requestBuilder);
        
        Request request = requestBuilder.build();
        httpClientBuilder.sslSocketFactory(createSslSocketFactory(
            config.getCertificatePath(), config.getCertificatePassword()), (X509TrustManager)getTrustedManager())
            .hostnameVerifier((hostname, session) -> true);

        try (Response response = httpClientBuilder.build().newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            
            ResponseBody responseBody = response.body();
            return responseBody != null ? responseBody.string() : "";
        }
    }
    
    private String makeDeleteRequest(String url) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
            .url(url)
            .delete();
        
        // Add user context headers
        addUserContextHeaders(requestBuilder);
        
        Request request = requestBuilder.build();
        
        try (Response response = httpClientBuilder.build().newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            
            ResponseBody responseBody = response.body();
            return responseBody != null ? responseBody.string() : "";
        }
    }

    private SSLSocketFactory createSslSocketFactory(String p12Path, String p12Password) throws Exception {
        // ----- Load client key material (PKCS12) -----
        KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(p12Path)) {
            clientKeyStore.load(fis, p12Password != null ? p12Password.toCharArray() : null);
        }

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(clientKeyStore, p12Password != null ? p12Password.toCharArray() : null);
        KeyManager[] keyManagers = kmf.getKeyManagers();

        // ----- Create a TrustManager that trusts all certs -----
        TrustManager trustManager = getTrustedManager();
        // ----- Init SSLContext with keyManagers (client cert) and trustAllCerts -----
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagers, new TrustManager[] { trustManager }, new SecureRandom());
        return sslContext.getSocketFactory();
    }

    private TrustManager getTrustedManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) { /* trust all */ }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) { /* trust all */ }

            @Override
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        };
    }
}