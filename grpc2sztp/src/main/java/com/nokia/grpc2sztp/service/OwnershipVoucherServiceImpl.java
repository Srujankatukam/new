package com.nokia.grpc2sztp.service;

import com.nokia.grpc2sztp.dto.ComponentDto;
import com.nokia.grpc2sztp.dto.GroupDto;
import com.nokia.grpc2sztp.dto.SerialDto;
import com.nokia.grpc2sztp.dto.DomainCertDto;
import com.nokia.grpc2sztp.dto.OwnershipVoucherDto;
import com.nokia.grpc2sztp.dto.UserRoleDto;
import com.nokia.grpc2sztp.exception.SztpApiException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ovgs.v1.*;

/**
 * Implementation of the OwnershipVoucherService gRPC service
 * Integrates with SZTP APIs for OVGS operations
 */
public class OwnershipVoucherServiceImpl extends OwnershipVoucherServiceGrpc.OwnershipVoucherServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(OwnershipVoucherServiceImpl.class);
    
    private final SZTPWrapperService sztpService;
    
    public OwnershipVoucherServiceImpl() {
        this.sztpService = SZTPWrapperService.getInstance();
    }
    
    @Override
    public void createGroup(CreateGroupRequest request, StreamObserver<CreateGroupResponse> responseObserver) {
        logger.info("Creating group with parent: {}, description: {}", request.getParent(), request.getDescription());
        
        try {
            // Validate request
            if (request.getParent().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Parent group ID is required")
                    .asRuntimeException());
                return;
            }
            
            if (request.getDescription().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Group description is required")
                    .asRuntimeException());
                return;
            }
            
            // SZTP backend will handle all validation and authorization
            // Call SZTP API
            GroupDto.CreateGroupRequest sztpRequest = new GroupDto.CreateGroupRequest(
                request.getParent(), request.getDescription());
            
            GroupDto.CreateGroupResponse sztpResponse = sztpService.createGroup(sztpRequest);
            
            // Convert response
            CreateGroupResponse response = CreateGroupResponse.newBuilder()
                .setGroupId(sztpResponse.getGroupId())
                .build();
            
            logger.info("Successfully created group: {}", sztpResponse.getGroupId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (SztpApiException e) {
            logger.error("Failed to create group", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to create group: " + e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("Unexpected error during group creation", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Internal server error")
                .asRuntimeException());
        }
    }
    
    @Override
    public void deleteGroup(DeleteGroupRequest request, StreamObserver<DeleteGroupResponse> responseObserver) {
        logger.info("Deleting group: {}", request.getGroupId());
        
        try {
            // Validate request
            if (request.getGroupId().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Group ID is required")
                    .asRuntimeException());
                return;
            }
            
            // SZTP backend will handle all validation and authorization
            // Call SZTP API
            sztpService.deleteGroup(request.getGroupId());
            
            // Create response
            DeleteGroupResponse response = DeleteGroupResponse.newBuilder().build();

            logger.info("Successfully deleted group: {}", request.getGroupId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (SztpApiException e) {
            logger.error("Failed to delete group: {}", request.getGroupId(), e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to delete group: " + e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("Unexpected error during group deletion", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Internal server error")
                .asRuntimeException());
        }
    }
    
    @Override
    public void getGroup(GetGroupRequest request, StreamObserver<GetGroupResponse> responseObserver) {
        logger.info("Getting group: {}", request.getGroupId());
        
        try {
            // Validate request
            if (request.getGroupId().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Group ID is required")
                    .asRuntimeException());
                return;
            }
            
            // SZTP backend will handle all validation and authorization
            // Call SZTP API
            GroupDto.GetGroupResponse sztpResponse = sztpService.getGroup(request.getGroupId());
            
            // Convert response
            GetGroupResponse.Builder responseBuilder = GetGroupResponse.newBuilder()
                .setGroupId(sztpResponse.getGroupId())
                .setDescription(sztpResponse.getDescription());
            
            // Add child groups
            if (sztpResponse.getChildGroupIds() != null) {
                responseBuilder.addAllChildGroupIds(sztpResponse.getChildGroupIds());
            }
            
            // Add users
            if (sztpResponse.getUsers() != null) {
                for (com.nokia.grpc2sztp.dto.UserDto userDto : sztpResponse.getUsers()) {
                    User user = User.newBuilder()
                        .setUsername(userDto.getUsername())
                        .setUserType(convertAccountType(userDto.getUserType()))
                        .setOrgId(userDto.getOrgId())
                        .setUserRole(convertUserRole(userDto.getUserRole()))
                        .build();
                    responseBuilder.addUsers(user);
                }
            }
            
            // Add components
            if (sztpResponse.getComponents() != null) {
                for (ComponentDto componentDto : sztpResponse.getComponents()) {
                    Component component = Component.newBuilder()
                        .setIen(componentDto.getIen())
                        .setSerialNumber(componentDto.getSerialNumber())
                        .build();
                    responseBuilder.addComponents(component);
                }
            }
            
            // Add cert IDs
            if (sztpResponse.getCertIds() != null) {
                responseBuilder.addAllCertIds(sztpResponse.getCertIds());
            }
            
            GetGroupResponse response = responseBuilder.build();
            
            logger.info("Successfully retrieved group: {}", 
                request.getGroupId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (SztpApiException e) {
            logger.error("Failed to get group: {}", request.getGroupId(), e);
            responseObserver.onError(Status.NOT_FOUND
                .withDescription("Group not found: " + e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("Unexpected error during group retrieval", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Internal server error")
                .asRuntimeException());
        }
    }
    
    @Override
    public void addUserRole(AddUserRoleRequest request, StreamObserver<AddUserRoleResponse> responseObserver) {
        logger.info("Adding user role: {} to group: {} for user: {}", 
            request.getUserRole(), request.getGroupId(), request.getUsername());
        
        try {
            // Validate request
            if (request.getGroupId().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Group ID is required")
                    .asRuntimeException());
                return;
            }
            
            if (request.getUsername().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Username is required")
                    .asRuntimeException());
                return;
            }
            
            // SZTP backend will handle all validation and authorization
            // Create SZTP request
            UserRoleDto.UserRoleRequest sztpRequest = new UserRoleDto.UserRoleRequest();
            sztpRequest.setGroupId(request.getGroupId());
            sztpRequest.setUsername(request.getUsername());
            sztpRequest.setUserType(convertAccountTypeToString(request.getUserType()));
            sztpRequest.setOrgId(request.getOrgId());
            sztpRequest.setUserRole(convertUserRoleToString(request.getUserRole()));
            
            // Call SZTP API
            sztpService.addUserRole(sztpRequest);
            
            // Create response
            AddUserRoleResponse response = AddUserRoleResponse.newBuilder().build();
            
            logger.info("Successfully added user role: {} for user: {} to group: {}", 
                request.getUserRole(), request.getUsername(), request.getGroupId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (SztpApiException e) {
            logger.error("Failed to add user role for user: {}", request.getUsername(), e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to add user role: " + e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("Unexpected error during user role addition", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Internal server error")
                .asRuntimeException());
        }
    }
    
    @Override
    public void removeUserRole(RemoveUserRoleRequest request, StreamObserver<RemoveUserRoleResponse> responseObserver) {
        logger.info("Removing user role: {} from group: {}", request.getUsername(), request.getGroupId());
        
        try {
            // Validate request
            if (request.getGroupId().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Group ID is required")
                    .asRuntimeException());
                return;
            }
            
            if (request.getUsername().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Username is required")
                    .asRuntimeException());
                return;
            }
            
            // Create SZTP request
            UserRoleDto.UserRoleRequest sztpRequest = new UserRoleDto.UserRoleRequest();
            sztpRequest.setGroupId(request.getGroupId());
            sztpRequest.setUsername(request.getUsername());
            sztpRequest.setUserType(convertAccountTypeToString(request.getUserType()));
            sztpRequest.setOrgId(request.getOrgId());
            
            // Call SZTP API
            sztpService.removeUserRole(sztpRequest);
            
            // Create response
            RemoveUserRoleResponse response = RemoveUserRoleResponse.newBuilder().build();
            
            logger.info("Successfully removed user role");
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (SztpApiException e) {
            logger.error("Failed to remove user role", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to remove user role: " + e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("Unexpected error during user role removal", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Internal server error")
                .asRuntimeException());
        }
    }
    
    @Override
    public void getUserRole(GetUserRoleRequest request, StreamObserver<GetUserRoleResponse> responseObserver) {
        logger.info("Getting user roles for: {}", request.getUsername());
        
        try {
            // Validate request
            if (request.getUsername().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Username is required")
                    .asRuntimeException());
                return;
            }
            
            // Call SZTP API
            UserRoleDto.UserRoleResponse sztpResponse = sztpService.getUserRole(
                request.getUsername(),
                convertAccountTypeToString(request.getUserType()),
                request.getOrgId()
            );
            
            // Convert response
            GetUserRoleResponse.Builder responseBuilder = GetUserRoleResponse.newBuilder();
            
            if (sztpResponse.getGroups() != null) {
                for (String groupId : sztpResponse.getGroups().keySet()) {
                    String role = sztpResponse.getGroups().get(groupId);
                    responseBuilder.putGroups(groupId, convertUserRole(role));
                }
            }
            
            GetUserRoleResponse response = responseBuilder.build();
            
            logger.info("Successfully retrieved user roles");
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (SztpApiException e) {
            logger.error("Failed to get user roles", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to get user roles: " + e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("Unexpected error during user role retrieval", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Internal server error")
                .asRuntimeException());
        }
    }
    
    @Override
    public void addSerial(AddSerialRequest request, StreamObserver<AddSerialResponse> responseObserver) {
        logger.info("Adding serial: {} to group: {}", 
            request.getComponent().getSerialNumber(), request.getGroupId());
        
        try {
            // Validate request
            if (request.getGroupId().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Group ID is required")
                    .asRuntimeException());
                return;
            }

            if (request.getComponent() == null) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Component is required")
                    .asRuntimeException());
                return;
            }

            // Create SZTP request
            ComponentDto componentDto = new ComponentDto(
                request.getComponent().getIen(),
                request.getComponent().getSerialNumber()
            );
            
            SerialDto.AddSerialRequest sztpRequest = new SerialDto.AddSerialRequest(
                request.getGroupId(), componentDto);
            
            // Call SZTP API
            sztpService.addSerial(sztpRequest);
            
            // Create response
            AddSerialResponse response = AddSerialResponse.newBuilder().build();
            
            logger.info("Successfully added serial: {} to group: {}", 
                request.getComponent().getSerialNumber(), request.getGroupId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (SztpApiException e) {
            logger.error("Failed to add serial", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to add serial: " + e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("Unexpected error during serial addition", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Internal server error")
                .asRuntimeException());
        }
    }
    
    @Override
    public void removeSerial(RemoveSerialRequest request, StreamObserver<RemoveSerialResponse> responseObserver) {
        logger.info("Removing serial: {} from group: {}", 
            request.getComponent().getSerialNumber(), request.getGroupId());
        
        try {
            // Validate request
            if (request.getGroupId().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Group ID is required")
                    .asRuntimeException());
                return;
            }

            // Create SZTP request
            ComponentDto componentDto = new ComponentDto(
                request.getComponent().getIen(),
                request.getComponent().getSerialNumber()
            );
            
            // Call SZTP API
            sztpService.removeSerial(request.getGroupId(), componentDto);
            
            // Create response
            RemoveSerialResponse response = RemoveSerialResponse.newBuilder().build();
            
            logger.info("Successfully removed serial: {}", request.getComponent().getSerialNumber());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (SztpApiException e) {
            logger.error("Failed to remove serial", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to remove serial: " + e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("Unexpected error during serial removal", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Internal server error")
                .asRuntimeException());
        }
    }
    
    @Override
    public void getSerial(GetSerialRequest request, StreamObserver<GetSerialResponse> responseObserver) {
        logger.info("Getting serial: {} with IEN: {}", 
            request.getComponent().getSerialNumber(), request.getComponent().getIen());
        
        try {
            // Validate request
            if (request.getComponent() == null) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Component is required")
                    .asRuntimeException());
                return;
            }
            
            // Call SZTP API
            SerialDto.GetSerialResponse sztpResponse = sztpService.getSerial(
                request.getComponent().getIen(),
                request.getComponent().getSerialNumber()
            );
            
            // Convert response
            GetSerialResponse.Builder responseBuilder = GetSerialResponse.newBuilder();
            
            if (sztpResponse.getGroupIds() != null) {
                for (String groupId : sztpResponse.getGroupIds()) {
                    responseBuilder.addGroupIds(groupId);
                }
            }
            
            GetSerialResponse response = responseBuilder
                    .setMacAddr(sztpResponse.getMacAddr())
                    .setModel(sztpResponse.getModel())
                    .build();
            
            logger.info("Successfully retrieved serial information");
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (SztpApiException e) {
            logger.error("Failed to get serial", e);
            responseObserver.onError(Status.NOT_FOUND
                .withDescription("Serial not found: " + e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("Unexpected error during serial retrieval", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Internal server error")
                .asRuntimeException());
        }
    }
    
    // Additional methods would be implemented here for domain certs, ownership vouchers, etc.
    // For brevity, implementing the most essential ones first
    
    @Override
    public void createDomainCert(CreateDomainCertRequest request, StreamObserver<CreateDomainCertResponse> responseObserver) {
        logger.info("Creating domain cert for group: {}", request.getGroupId());
        
        try {
            // Validate request
            if (request.getGroupId().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Group ID is required")
                    .asRuntimeException());
                return;
            }
            
            if (request.getCertificateDer().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Certificate DER is required")
                    .asRuntimeException());
                return;
            }
            
            // Convert Protobuf Timestamp to ISO string
            String expiryTimeString = null;
            if (request.hasExpiryTime()) {
                // Convert protobuf timestamp to ISO string
                long seconds = request.getExpiryTime().getSeconds();
                int nanos = request.getExpiryTime().getNanos();
                java.time.Instant instant = java.time.Instant.ofEpochSecond(seconds, nanos);
                expiryTimeString = instant.toString();
            }
            
            // SZTP backend will handle all validation and authorization
            // Convert certificate DER bytes to Base64 string for API
            String certificateDerBase64 = java.util.Base64.getEncoder().encodeToString(
                request.getCertificateDer().toByteArray());
            
            // Create SZTP request
            DomainCertDto.CreateDomainCertRequest sztpRequest = new DomainCertDto.CreateDomainCertRequest(
                request.getGroupId(),
                certificateDerBase64,
                request.getRevocationChecks(),
                expiryTimeString
            );
            
            // Call SZTP API
            DomainCertDto.CreateDomainCertResponse sztpResponse = sztpService.createDomainCert(sztpRequest);
            
            // Create response
            CreateDomainCertResponse response = CreateDomainCertResponse.newBuilder()
                .setCertId(sztpResponse.getCertId())
                .build();
            
            logger.info("Successfully created domain certificate: {} for group: {}", 
                sztpResponse.getCertId(), request.getGroupId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (SztpApiException e) {
            logger.error("Failed to create domain certificate for group: {}", request.getGroupId(), e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to create domain certificate: " + e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("Unexpected error during domain certificate creation", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Internal server error")
                .asRuntimeException());
        }
    }
    
    @Override
    public void getOwnershipVoucher(GetOwnershipVoucherRequest request, StreamObserver<GetOwnershipVoucherResponse> responseObserver) {
        logger.info("Getting ownership voucher for serial: {} with cert: {}", 
            request.getComponent().getSerialNumber(), request.getCertId());
        
        try {
            // Validate request - similar to Go service validation
            if (request.getCertId() == null || request.getCertId().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("CertId is required")
                    .asRuntimeException());
                return;
            }
            
            if (request.getComponent() == null || request.getComponent().getSerialNumber().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Component SerialNumber is required")
                    .asRuntimeException());
                return;
            }
            
            if (request.getLifetime() == null) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Lifetime is required")
                    .asRuntimeException());
                return;
            }
            

            // Convert gRPC request to DTO for SZTP API
            ComponentDto componentDto = new ComponentDto(
                request.getComponent().getIen(),
                request.getComponent().getSerialNumber()
            );
            
            // Convert protobuf Duration to string (ISO duration format)
            String lifetimeString = null;
            if (request.getLifetime() != null) {
                long seconds = request.getLifetime().getSeconds();
                int nanos = request.getLifetime().getNanos();
                java.time.Instant instant = java.time.Instant.ofEpochSecond(seconds, nanos);
                lifetimeString = instant.toString();
            }
            
            OwnershipVoucherDto.GetOwnershipVoucherRequest sztpRequest = new OwnershipVoucherDto.GetOwnershipVoucherRequest(
                request.getCertId(),
                componentDto,
                lifetimeString
            );
            
            // Call SZTP API
            OwnershipVoucherDto.GetOwnershipVoucherResponse sztpResponse = sztpService.getOwnershipVoucher(sztpRequest);
            
            // Convert DTO response to gRPC response
            GetOwnershipVoucherResponse.Builder responseBuilder = GetOwnershipVoucherResponse.newBuilder();
            
            if (sztpResponse.getVoucherCms() != null) {
                // Convert Base64 string back to bytes for gRPC
                byte[] voucherBytes = java.util.Base64.getDecoder().decode(sztpResponse.getVoucherCms());
                responseBuilder.setVoucherCms(com.google.protobuf.ByteString.copyFrom(voucherBytes));
            }
            
            GetOwnershipVoucherResponse response = responseBuilder.build();

            logger.info("Successfully generated ownership voucher for serial: {}", 
                request.getComponent().getSerialNumber());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            logger.error("Error generating ownership voucher for serial: {}", 
                request.getComponent().getSerialNumber(), e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to generate ownership voucher: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    // Helper methods for type conversion
    private AccountType convertAccountType(String userType) {
        if (userType == null) return AccountType.ACCOUNT_TYPE_UNSPECIFIED;

        return switch (userType.toUpperCase()) {
            case "ACCOUNT_TYPE_USER" -> AccountType.ACCOUNT_TYPE_USER;
            case "ACCOUNT_TYPE_SERVICE_ACCOUNT" -> AccountType.ACCOUNT_TYPE_SERVICE_ACCOUNT;
            default -> AccountType.ACCOUNT_TYPE_UNSPECIFIED;
        };
    }
    
    private String convertAccountTypeToString(AccountType accountType) {
        if (accountType == null) return "USER";

        return switch (accountType) {
            case ACCOUNT_TYPE_USER -> "ACCOUNT_TYPE_USER";
            case ACCOUNT_TYPE_SERVICE_ACCOUNT -> "ACCOUNT_TYPE_SERVICE_ACCOUNT";
            default -> "UNSPECIFIED";
        };
    }
    
    private UserRole convertUserRole(String role) {
        if (role == null) return UserRole.USER_ROLE_UNSPECIFIED;

        return switch (role.toUpperCase()) {
            case "USER_ROLE_ADMIN" -> UserRole.USER_ROLE_ADMIN;
            case "USER_ROLE_ASSIGNER" -> UserRole.USER_ROLE_ASSIGNER;
            case "USER_ROLE_REQUESTOR" -> UserRole.USER_ROLE_REQUESTOR;
            case "USER_ROLE_SUPPORT" -> UserRole.USER_ROLE_SUPPORT;
            case "USER_ROLE_SUPER_ADMIN" -> UserRole.USER_ROLE_SUPER_ADMIN;
            default -> UserRole.USER_ROLE_UNSPECIFIED;
        };
    }
    
    private String convertUserRoleToString(UserRole userRole) {
        if (userRole == null) return "REQUESTOR";

        return switch (userRole) {
            case USER_ROLE_ADMIN -> "USER_ROLE_ADMIN";
            case USER_ROLE_ASSIGNER -> "USER_ROLE_ASSIGNER";
            case USER_ROLE_SUPPORT -> "USER_ROLE_SUPPORT";
            case USER_ROLE_SUPER_ADMIN -> "USER_ROLE_SUPER_ADMIN";
            default -> "USER_ROLE_REQUESTOR";
        };
    }
}