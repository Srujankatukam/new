package com.nokia.grpc2sztp.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration manager for SZTP API settings
 * Supports environment variable substitution
 */
public class ConfigurationManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);
    private static ConfigurationManager instance;
    private Configuration config;
    
    private ConfigurationManager() {
        try {
            Configurations configs = new Configurations();
            config = configs.properties("application.properties");
            logger.info("Configuration loaded successfully");
        } catch (ConfigurationException e) {
            logger.error("Failed to load configuration", e);
            throw new RuntimeException("Configuration initialization failed", e);
        }
    }
    
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }
    
    /**
     * Get string value with environment variable support
     */
    private String getStringWithEnvSupport(String key, String defaultValue) {
        String value = config.getString(key, defaultValue);
        return resolveEnvironmentVariables(value);
    }
    
    /**
     * Get integer value with environment variable support
     */
    private int getIntWithEnvSupport(String key, int defaultValue) {
        String value = config.getString(key, String.valueOf(defaultValue));
        String resolved = resolveEnvironmentVariables(value);
        try {
            return Integer.parseInt(resolved);
        } catch (NumberFormatException e) {
            logger.warn("Could not parse '{}' as integer, using default: {}", resolved, defaultValue);
            return defaultValue;
        }
    }
    
    /**
     * Resolve environment variables in format ${VAR_NAME:default_value}
     */
    private String resolveEnvironmentVariables(String value) {
        if (value == null || !value.contains("${")) {
            return value;
        }
        
        // Simple regex to match ${VAR_NAME:default_value}
        while (value.contains("${")) {
            int start = value.indexOf("${");
            int end = value.indexOf("}", start);
            
            if (end == -1) break;
            
            String placeholder = value.substring(start + 2, end);
            String envVar;
            String defaultVal = "";
            
            if (placeholder.contains(":")) {
                String[] parts = placeholder.split(":", 2);
                envVar = parts[0];
                defaultVal = parts[1];
            } else {
                envVar = placeholder;
            }
            
            String envValue = System.getenv(envVar);
            String replacement = envValue != null ? envValue : defaultVal;
            
            value = value.substring(0, start) + replacement + value.substring(end + 1);
        }
        
        return value;
    }
    
    
    public String getSztpApiBaseUrl() {
        String baseUrl = getStringWithEnvSupport("sztp.api.base.url", "https://localhost:8443");
        logger.debug("SZTP API Base URL: {}", baseUrl);
        return baseUrl;
    }
    
    public int getApiTimeoutSeconds() {
        return getIntWithEnvSupport("sztp.api.timeout.seconds", 30);
    }
    
    public int getRetryAttempts() {
        return getIntWithEnvSupport("sztp.api.retry.attempts", 3);
    }
    
    public String getApiEndpoint(String methodName) {
        String endpoint = getSztpApiBaseUrl() + "/api" + methodName;
        logger.debug("API endpoint for {}: {}", methodName, endpoint);
        return endpoint;
    }
    
    public int getGrpcPort() {
        return getIntWithEnvSupport("grpc.server.port", 9090);
    }

    public String getCertificatePath() {
        return getStringWithEnvSupport("sztp.api.auth.certificate-path", "");
    }

    public String getCertificatePassword() {
        return getStringWithEnvSupport("sztp.api.auth.certificate-password", "");
    }
}