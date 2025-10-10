package com.nokia.grpc2sztp;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import login.v1.*;
import ovgs.v1.*;

import java.util.concurrent.TimeUnit;

/**
 * Simple test client for gRPC services
 */
public class TestClient {
    
    private final ManagedChannel channel;
    private final LoginServiceGrpc.LoginServiceBlockingStub loginStub;
    private final OwnershipVoucherServiceGrpc.OwnershipVoucherServiceBlockingStub ovgsStub;
    
    public TestClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext()
            .build());
    }
    
    TestClient(ManagedChannel channel) {
        this.channel = channel;
        loginStub = LoginServiceGrpc.newBlockingStub(channel);
        ovgsStub = OwnershipVoucherServiceGrpc.newBlockingStub(channel);
    }
    
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    
    public void testLogin() {
        System.out.println("=== Testing Login Service ===");
        
        try {
            // Test successful login
            AuthRequest request = AuthRequest.newBuilder()
                .setUsername("admin")
                .setOrgId("nokia")
                .setPassword("password123")
                .build();
                
            AuthResponse response = loginStub.login(request);
            System.out.println("✅ Login successful! Token: " + response.getToken());
            
        } catch (StatusRuntimeException e) {
            System.err.println("❌ Login failed: " + e.getStatus());
        }
        
        try {
            // Test failed login
            AuthRequest request = AuthRequest.newBuilder()
                .setUsername("admin")
                .setOrgId("org-nokia")
                .setPassword("wrongpassword")
                .build();
                
            loginStub.login(request);
            System.out.println("❌ Should have failed!");
            
        } catch (StatusRuntimeException e) {
            System.out.println("✅ Login correctly failed: " + e.getStatus().getDescription());
        }
        
        System.out.println();
    }
    
    public void testOwnershipVoucherService() {
        System.out.println("=== Testing Ownership Voucher Service ===");
        
        try {
            // Test CreateGroup
            CreateGroupRequest createRequest = CreateGroupRequest.newBuilder()
                .setParent("org-nokia")
                .setDescription("Test Group from Client")
                .build();
                
            CreateGroupResponse createResponse = ovgsStub.createGroup(createRequest);
            System.out.println("✅ Group created: " + createResponse.getGroupId());
            
            String newGroupId = createResponse.getGroupId();
            
            // Test GetGroup
            GetGroupRequest getRequest = GetGroupRequest.newBuilder()
                .setGroupId(newGroupId)
                .build();
                
            GetGroupResponse getResponse = ovgsStub.getGroup(getRequest);
            System.out.println("✅ Group retrieved: " + getResponse.getDescription());
            System.out.println("   Child groups: " + getResponse.getChildGroupIdsList());
            System.out.println("   Users count: " + getResponse.getUsersList().size());
            
            // Test AddUserRole
            AddUserRoleRequest addUserRequest = AddUserRoleRequest.newBuilder()
                .setUsername("testuser")
                .setUserType(AccountType.ACCOUNT_TYPE_USER)
                .setOrgId("org-nokia")
                .setGroupId(newGroupId)
                .setUserRole(UserRole.USER_ROLE_ASSIGNER)
                .build();
                
            ovgsStub.addUserRole(addUserRequest);
            System.out.println("✅ User role added successfully");
            
            // Test GetUserRole
            GetUserRoleRequest getUserRequest = GetUserRoleRequest.newBuilder()
                .setUsername("testuser")
                .setUserType(AccountType.ACCOUNT_TYPE_USER)
                .setOrgId("org-nokia")
                .build();
                
            GetUserRoleResponse getUserResponse = ovgsStub.getUserRole(getUserRequest);
            System.out.println("✅ User roles: " + getUserResponse.getGroupsMap());
            
            // Test AddSerial
            Component component = Component.newBuilder()
                .setIen("30065")
                .setSerialNumber("TEST-12345")
                .build();
                
            AddSerialRequest addSerialRequest = AddSerialRequest.newBuilder()
                .setComponent(component)
                .setGroupId(newGroupId)
                .build();
                
            ovgsStub.addSerial(addSerialRequest);
            System.out.println("✅ Serial added to group");
            
            // Test GetSerial
            GetSerialRequest getSerialRequest = GetSerialRequest.newBuilder()
                .setComponent(component)
                .build();
                
            GetSerialResponse getSerialResponse = ovgsStub.getSerial(getSerialRequest);
            System.out.println("✅ Serial info retrieved:");
            System.out.println("   Groups: " + getSerialResponse.getGroupIdsList());
            System.out.println("   Model: " + getSerialResponse.getModel());
            
            // Test DeleteGroup
            DeleteGroupRequest deleteRequest = DeleteGroupRequest.newBuilder()
                .setGroupId(newGroupId)
                .build();
                
            ovgsStub.deleteGroup(deleteRequest);
            System.out.println("✅ Group deleted successfully");
            
        } catch (StatusRuntimeException e) {
            System.err.println("❌ OVGS operation failed: " + e.getStatus());
        }
        
        System.out.println();
    }
    
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 8080;
        
        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        
        TestClient client = new TestClient(host, port);
        
        try {
            System.out.println("Connecting to gRPC server at " + host + ":" + port);
            System.out.println();
            
            client.testLogin();
            client.testOwnershipVoucherService();
            
            System.out.println("All tests completed!");
            
        } finally {
            client.shutdown();
        }
    }
}
