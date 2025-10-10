package ovgs.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: ovgs.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class OwnershipVoucherServiceGrpc {

  private OwnershipVoucherServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "ovgs.v1.OwnershipVoucherService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ovgs.v1.CreateGroupRequest,
      ovgs.v1.CreateGroupResponse> getCreateGroupMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateGroup",
      requestType = ovgs.v1.CreateGroupRequest.class,
      responseType = ovgs.v1.CreateGroupResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.CreateGroupRequest,
      ovgs.v1.CreateGroupResponse> getCreateGroupMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.CreateGroupRequest, ovgs.v1.CreateGroupResponse> getCreateGroupMethod;
    if ((getCreateGroupMethod = OwnershipVoucherServiceGrpc.getCreateGroupMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getCreateGroupMethod = OwnershipVoucherServiceGrpc.getCreateGroupMethod) == null) {
          OwnershipVoucherServiceGrpc.getCreateGroupMethod = getCreateGroupMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.CreateGroupRequest, ovgs.v1.CreateGroupResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateGroup"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.CreateGroupRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.CreateGroupResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("CreateGroup"))
              .build();
        }
      }
    }
    return getCreateGroupMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.DeleteGroupRequest,
      ovgs.v1.DeleteGroupResponse> getDeleteGroupMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteGroup",
      requestType = ovgs.v1.DeleteGroupRequest.class,
      responseType = ovgs.v1.DeleteGroupResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.DeleteGroupRequest,
      ovgs.v1.DeleteGroupResponse> getDeleteGroupMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.DeleteGroupRequest, ovgs.v1.DeleteGroupResponse> getDeleteGroupMethod;
    if ((getDeleteGroupMethod = OwnershipVoucherServiceGrpc.getDeleteGroupMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getDeleteGroupMethod = OwnershipVoucherServiceGrpc.getDeleteGroupMethod) == null) {
          OwnershipVoucherServiceGrpc.getDeleteGroupMethod = getDeleteGroupMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.DeleteGroupRequest, ovgs.v1.DeleteGroupResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteGroup"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.DeleteGroupRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.DeleteGroupResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("DeleteGroup"))
              .build();
        }
      }
    }
    return getDeleteGroupMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.GetGroupRequest,
      ovgs.v1.GetGroupResponse> getGetGroupMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetGroup",
      requestType = ovgs.v1.GetGroupRequest.class,
      responseType = ovgs.v1.GetGroupResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.GetGroupRequest,
      ovgs.v1.GetGroupResponse> getGetGroupMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.GetGroupRequest, ovgs.v1.GetGroupResponse> getGetGroupMethod;
    if ((getGetGroupMethod = OwnershipVoucherServiceGrpc.getGetGroupMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getGetGroupMethod = OwnershipVoucherServiceGrpc.getGetGroupMethod) == null) {
          OwnershipVoucherServiceGrpc.getGetGroupMethod = getGetGroupMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.GetGroupRequest, ovgs.v1.GetGroupResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetGroup"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.GetGroupRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.GetGroupResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("GetGroup"))
              .build();
        }
      }
    }
    return getGetGroupMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.AddUserRoleRequest,
      ovgs.v1.AddUserRoleResponse> getAddUserRoleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddUserRole",
      requestType = ovgs.v1.AddUserRoleRequest.class,
      responseType = ovgs.v1.AddUserRoleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.AddUserRoleRequest,
      ovgs.v1.AddUserRoleResponse> getAddUserRoleMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.AddUserRoleRequest, ovgs.v1.AddUserRoleResponse> getAddUserRoleMethod;
    if ((getAddUserRoleMethod = OwnershipVoucherServiceGrpc.getAddUserRoleMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getAddUserRoleMethod = OwnershipVoucherServiceGrpc.getAddUserRoleMethod) == null) {
          OwnershipVoucherServiceGrpc.getAddUserRoleMethod = getAddUserRoleMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.AddUserRoleRequest, ovgs.v1.AddUserRoleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddUserRole"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.AddUserRoleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.AddUserRoleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("AddUserRole"))
              .build();
        }
      }
    }
    return getAddUserRoleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.RemoveUserRoleRequest,
      ovgs.v1.RemoveUserRoleResponse> getRemoveUserRoleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RemoveUserRole",
      requestType = ovgs.v1.RemoveUserRoleRequest.class,
      responseType = ovgs.v1.RemoveUserRoleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.RemoveUserRoleRequest,
      ovgs.v1.RemoveUserRoleResponse> getRemoveUserRoleMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.RemoveUserRoleRequest, ovgs.v1.RemoveUserRoleResponse> getRemoveUserRoleMethod;
    if ((getRemoveUserRoleMethod = OwnershipVoucherServiceGrpc.getRemoveUserRoleMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getRemoveUserRoleMethod = OwnershipVoucherServiceGrpc.getRemoveUserRoleMethod) == null) {
          OwnershipVoucherServiceGrpc.getRemoveUserRoleMethod = getRemoveUserRoleMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.RemoveUserRoleRequest, ovgs.v1.RemoveUserRoleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RemoveUserRole"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.RemoveUserRoleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.RemoveUserRoleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("RemoveUserRole"))
              .build();
        }
      }
    }
    return getRemoveUserRoleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.GetUserRoleRequest,
      ovgs.v1.GetUserRoleResponse> getGetUserRoleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUserRole",
      requestType = ovgs.v1.GetUserRoleRequest.class,
      responseType = ovgs.v1.GetUserRoleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.GetUserRoleRequest,
      ovgs.v1.GetUserRoleResponse> getGetUserRoleMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.GetUserRoleRequest, ovgs.v1.GetUserRoleResponse> getGetUserRoleMethod;
    if ((getGetUserRoleMethod = OwnershipVoucherServiceGrpc.getGetUserRoleMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getGetUserRoleMethod = OwnershipVoucherServiceGrpc.getGetUserRoleMethod) == null) {
          OwnershipVoucherServiceGrpc.getGetUserRoleMethod = getGetUserRoleMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.GetUserRoleRequest, ovgs.v1.GetUserRoleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUserRole"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.GetUserRoleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.GetUserRoleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("GetUserRole"))
              .build();
        }
      }
    }
    return getGetUserRoleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.AddSerialRequest,
      ovgs.v1.AddSerialResponse> getAddSerialMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddSerial",
      requestType = ovgs.v1.AddSerialRequest.class,
      responseType = ovgs.v1.AddSerialResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.AddSerialRequest,
      ovgs.v1.AddSerialResponse> getAddSerialMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.AddSerialRequest, ovgs.v1.AddSerialResponse> getAddSerialMethod;
    if ((getAddSerialMethod = OwnershipVoucherServiceGrpc.getAddSerialMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getAddSerialMethod = OwnershipVoucherServiceGrpc.getAddSerialMethod) == null) {
          OwnershipVoucherServiceGrpc.getAddSerialMethod = getAddSerialMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.AddSerialRequest, ovgs.v1.AddSerialResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddSerial"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.AddSerialRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.AddSerialResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("AddSerial"))
              .build();
        }
      }
    }
    return getAddSerialMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.RemoveSerialRequest,
      ovgs.v1.RemoveSerialResponse> getRemoveSerialMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RemoveSerial",
      requestType = ovgs.v1.RemoveSerialRequest.class,
      responseType = ovgs.v1.RemoveSerialResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.RemoveSerialRequest,
      ovgs.v1.RemoveSerialResponse> getRemoveSerialMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.RemoveSerialRequest, ovgs.v1.RemoveSerialResponse> getRemoveSerialMethod;
    if ((getRemoveSerialMethod = OwnershipVoucherServiceGrpc.getRemoveSerialMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getRemoveSerialMethod = OwnershipVoucherServiceGrpc.getRemoveSerialMethod) == null) {
          OwnershipVoucherServiceGrpc.getRemoveSerialMethod = getRemoveSerialMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.RemoveSerialRequest, ovgs.v1.RemoveSerialResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RemoveSerial"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.RemoveSerialRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.RemoveSerialResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("RemoveSerial"))
              .build();
        }
      }
    }
    return getRemoveSerialMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.GetSerialRequest,
      ovgs.v1.GetSerialResponse> getGetSerialMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSerial",
      requestType = ovgs.v1.GetSerialRequest.class,
      responseType = ovgs.v1.GetSerialResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.GetSerialRequest,
      ovgs.v1.GetSerialResponse> getGetSerialMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.GetSerialRequest, ovgs.v1.GetSerialResponse> getGetSerialMethod;
    if ((getGetSerialMethod = OwnershipVoucherServiceGrpc.getGetSerialMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getGetSerialMethod = OwnershipVoucherServiceGrpc.getGetSerialMethod) == null) {
          OwnershipVoucherServiceGrpc.getGetSerialMethod = getGetSerialMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.GetSerialRequest, ovgs.v1.GetSerialResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSerial"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.GetSerialRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.GetSerialResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("GetSerial"))
              .build();
        }
      }
    }
    return getGetSerialMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.CreateDomainCertRequest,
      ovgs.v1.CreateDomainCertResponse> getCreateDomainCertMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateDomainCert",
      requestType = ovgs.v1.CreateDomainCertRequest.class,
      responseType = ovgs.v1.CreateDomainCertResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.CreateDomainCertRequest,
      ovgs.v1.CreateDomainCertResponse> getCreateDomainCertMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.CreateDomainCertRequest, ovgs.v1.CreateDomainCertResponse> getCreateDomainCertMethod;
    if ((getCreateDomainCertMethod = OwnershipVoucherServiceGrpc.getCreateDomainCertMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getCreateDomainCertMethod = OwnershipVoucherServiceGrpc.getCreateDomainCertMethod) == null) {
          OwnershipVoucherServiceGrpc.getCreateDomainCertMethod = getCreateDomainCertMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.CreateDomainCertRequest, ovgs.v1.CreateDomainCertResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateDomainCert"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.CreateDomainCertRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.CreateDomainCertResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("CreateDomainCert"))
              .build();
        }
      }
    }
    return getCreateDomainCertMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.DeleteDomainCertRequest,
      ovgs.v1.DeleteDomainCertResponse> getDeleteDomainCertMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteDomainCert",
      requestType = ovgs.v1.DeleteDomainCertRequest.class,
      responseType = ovgs.v1.DeleteDomainCertResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.DeleteDomainCertRequest,
      ovgs.v1.DeleteDomainCertResponse> getDeleteDomainCertMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.DeleteDomainCertRequest, ovgs.v1.DeleteDomainCertResponse> getDeleteDomainCertMethod;
    if ((getDeleteDomainCertMethod = OwnershipVoucherServiceGrpc.getDeleteDomainCertMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getDeleteDomainCertMethod = OwnershipVoucherServiceGrpc.getDeleteDomainCertMethod) == null) {
          OwnershipVoucherServiceGrpc.getDeleteDomainCertMethod = getDeleteDomainCertMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.DeleteDomainCertRequest, ovgs.v1.DeleteDomainCertResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteDomainCert"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.DeleteDomainCertRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.DeleteDomainCertResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("DeleteDomainCert"))
              .build();
        }
      }
    }
    return getDeleteDomainCertMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.GetDomainCertRequest,
      ovgs.v1.GetDomainCertResponse> getGetDomainCertMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDomainCert",
      requestType = ovgs.v1.GetDomainCertRequest.class,
      responseType = ovgs.v1.GetDomainCertResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.GetDomainCertRequest,
      ovgs.v1.GetDomainCertResponse> getGetDomainCertMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.GetDomainCertRequest, ovgs.v1.GetDomainCertResponse> getGetDomainCertMethod;
    if ((getGetDomainCertMethod = OwnershipVoucherServiceGrpc.getGetDomainCertMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getGetDomainCertMethod = OwnershipVoucherServiceGrpc.getGetDomainCertMethod) == null) {
          OwnershipVoucherServiceGrpc.getGetDomainCertMethod = getGetDomainCertMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.GetDomainCertRequest, ovgs.v1.GetDomainCertResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDomainCert"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.GetDomainCertRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.GetDomainCertResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("GetDomainCert"))
              .build();
        }
      }
    }
    return getGetDomainCertMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.GetOwnershipVoucherRequest,
      ovgs.v1.GetOwnershipVoucherResponse> getGetOwnershipVoucherMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOwnershipVoucher",
      requestType = ovgs.v1.GetOwnershipVoucherRequest.class,
      responseType = ovgs.v1.GetOwnershipVoucherResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.GetOwnershipVoucherRequest,
      ovgs.v1.GetOwnershipVoucherResponse> getGetOwnershipVoucherMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.GetOwnershipVoucherRequest, ovgs.v1.GetOwnershipVoucherResponse> getGetOwnershipVoucherMethod;
    if ((getGetOwnershipVoucherMethod = OwnershipVoucherServiceGrpc.getGetOwnershipVoucherMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getGetOwnershipVoucherMethod = OwnershipVoucherServiceGrpc.getGetOwnershipVoucherMethod) == null) {
          OwnershipVoucherServiceGrpc.getGetOwnershipVoucherMethod = getGetOwnershipVoucherMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.GetOwnershipVoucherRequest, ovgs.v1.GetOwnershipVoucherResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOwnershipVoucher"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.GetOwnershipVoucherRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.GetOwnershipVoucherResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("GetOwnershipVoucher"))
              .build();
        }
      }
    }
    return getGetOwnershipVoucherMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.ListSuperAdminsRequest,
      ovgs.v1.ListSuperAdminsResponse> getListSuperAdminsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListSuperAdmins",
      requestType = ovgs.v1.ListSuperAdminsRequest.class,
      responseType = ovgs.v1.ListSuperAdminsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.ListSuperAdminsRequest,
      ovgs.v1.ListSuperAdminsResponse> getListSuperAdminsMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.ListSuperAdminsRequest, ovgs.v1.ListSuperAdminsResponse> getListSuperAdminsMethod;
    if ((getListSuperAdminsMethod = OwnershipVoucherServiceGrpc.getListSuperAdminsMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getListSuperAdminsMethod = OwnershipVoucherServiceGrpc.getListSuperAdminsMethod) == null) {
          OwnershipVoucherServiceGrpc.getListSuperAdminsMethod = getListSuperAdminsMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.ListSuperAdminsRequest, ovgs.v1.ListSuperAdminsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListSuperAdmins"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.ListSuperAdminsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.ListSuperAdminsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("ListSuperAdmins"))
              .build();
        }
      }
    }
    return getListSuperAdminsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.UpsertThirdPartyDelegationRequest,
      ovgs.v1.UpsertThirdPartyDelegationResponse> getUpsertThirdPartyDelegationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpsertThirdPartyDelegation",
      requestType = ovgs.v1.UpsertThirdPartyDelegationRequest.class,
      responseType = ovgs.v1.UpsertThirdPartyDelegationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.UpsertThirdPartyDelegationRequest,
      ovgs.v1.UpsertThirdPartyDelegationResponse> getUpsertThirdPartyDelegationMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.UpsertThirdPartyDelegationRequest, ovgs.v1.UpsertThirdPartyDelegationResponse> getUpsertThirdPartyDelegationMethod;
    if ((getUpsertThirdPartyDelegationMethod = OwnershipVoucherServiceGrpc.getUpsertThirdPartyDelegationMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getUpsertThirdPartyDelegationMethod = OwnershipVoucherServiceGrpc.getUpsertThirdPartyDelegationMethod) == null) {
          OwnershipVoucherServiceGrpc.getUpsertThirdPartyDelegationMethod = getUpsertThirdPartyDelegationMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.UpsertThirdPartyDelegationRequest, ovgs.v1.UpsertThirdPartyDelegationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpsertThirdPartyDelegation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.UpsertThirdPartyDelegationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.UpsertThirdPartyDelegationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("UpsertThirdPartyDelegation"))
              .build();
        }
      }
    }
    return getUpsertThirdPartyDelegationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.DeleteThirdPartyDelegationRequest,
      ovgs.v1.DeleteThirdPartyDelegationResponse> getDeleteThirdPartyDelegationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteThirdPartyDelegation",
      requestType = ovgs.v1.DeleteThirdPartyDelegationRequest.class,
      responseType = ovgs.v1.DeleteThirdPartyDelegationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.DeleteThirdPartyDelegationRequest,
      ovgs.v1.DeleteThirdPartyDelegationResponse> getDeleteThirdPartyDelegationMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.DeleteThirdPartyDelegationRequest, ovgs.v1.DeleteThirdPartyDelegationResponse> getDeleteThirdPartyDelegationMethod;
    if ((getDeleteThirdPartyDelegationMethod = OwnershipVoucherServiceGrpc.getDeleteThirdPartyDelegationMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getDeleteThirdPartyDelegationMethod = OwnershipVoucherServiceGrpc.getDeleteThirdPartyDelegationMethod) == null) {
          OwnershipVoucherServiceGrpc.getDeleteThirdPartyDelegationMethod = getDeleteThirdPartyDelegationMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.DeleteThirdPartyDelegationRequest, ovgs.v1.DeleteThirdPartyDelegationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteThirdPartyDelegation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.DeleteThirdPartyDelegationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.DeleteThirdPartyDelegationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("DeleteThirdPartyDelegation"))
              .build();
        }
      }
    }
    return getDeleteThirdPartyDelegationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.GetThirdPartyDelegationRequest,
      ovgs.v1.GetThirdPartyDelegationResponse> getGetThirdPartyDelegationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetThirdPartyDelegation",
      requestType = ovgs.v1.GetThirdPartyDelegationRequest.class,
      responseType = ovgs.v1.GetThirdPartyDelegationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.GetThirdPartyDelegationRequest,
      ovgs.v1.GetThirdPartyDelegationResponse> getGetThirdPartyDelegationMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.GetThirdPartyDelegationRequest, ovgs.v1.GetThirdPartyDelegationResponse> getGetThirdPartyDelegationMethod;
    if ((getGetThirdPartyDelegationMethod = OwnershipVoucherServiceGrpc.getGetThirdPartyDelegationMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getGetThirdPartyDelegationMethod = OwnershipVoucherServiceGrpc.getGetThirdPartyDelegationMethod) == null) {
          OwnershipVoucherServiceGrpc.getGetThirdPartyDelegationMethod = getGetThirdPartyDelegationMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.GetThirdPartyDelegationRequest, ovgs.v1.GetThirdPartyDelegationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetThirdPartyDelegation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.GetThirdPartyDelegationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.GetThirdPartyDelegationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("GetThirdPartyDelegation"))
              .build();
        }
      }
    }
    return getGetThirdPartyDelegationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ovgs.v1.ListThirdPartyDelegationsRequest,
      ovgs.v1.ListThirdPartyDelegationsResponse> getListThirdPartyDelegationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListThirdPartyDelegations",
      requestType = ovgs.v1.ListThirdPartyDelegationsRequest.class,
      responseType = ovgs.v1.ListThirdPartyDelegationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ovgs.v1.ListThirdPartyDelegationsRequest,
      ovgs.v1.ListThirdPartyDelegationsResponse> getListThirdPartyDelegationsMethod() {
    io.grpc.MethodDescriptor<ovgs.v1.ListThirdPartyDelegationsRequest, ovgs.v1.ListThirdPartyDelegationsResponse> getListThirdPartyDelegationsMethod;
    if ((getListThirdPartyDelegationsMethod = OwnershipVoucherServiceGrpc.getListThirdPartyDelegationsMethod) == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        if ((getListThirdPartyDelegationsMethod = OwnershipVoucherServiceGrpc.getListThirdPartyDelegationsMethod) == null) {
          OwnershipVoucherServiceGrpc.getListThirdPartyDelegationsMethod = getListThirdPartyDelegationsMethod =
              io.grpc.MethodDescriptor.<ovgs.v1.ListThirdPartyDelegationsRequest, ovgs.v1.ListThirdPartyDelegationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListThirdPartyDelegations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.ListThirdPartyDelegationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ovgs.v1.ListThirdPartyDelegationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipVoucherServiceMethodDescriptorSupplier("ListThirdPartyDelegations"))
              .build();
        }
      }
    }
    return getListThirdPartyDelegationsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OwnershipVoucherServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OwnershipVoucherServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OwnershipVoucherServiceStub>() {
        @java.lang.Override
        public OwnershipVoucherServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OwnershipVoucherServiceStub(channel, callOptions);
        }
      };
    return OwnershipVoucherServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OwnershipVoucherServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OwnershipVoucherServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OwnershipVoucherServiceBlockingStub>() {
        @java.lang.Override
        public OwnershipVoucherServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OwnershipVoucherServiceBlockingStub(channel, callOptions);
        }
      };
    return OwnershipVoucherServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OwnershipVoucherServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OwnershipVoucherServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OwnershipVoucherServiceFutureStub>() {
        @java.lang.Override
        public OwnershipVoucherServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OwnershipVoucherServiceFutureStub(channel, callOptions);
        }
      };
    return OwnershipVoucherServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * CreateGroup creates a group as a child of an existing group.
     * Errors will be returned:
     * INVALID_ARGUMENT if either parent or description is empty
     * NOT_FOUND if the parent group doesn't exist, as specified in request
     * ALREADY_EXISTS if a group already exists with the same parent group
     * and description.
     * PERMISSION_DENIED if the user doesn't have access to the parent group
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    default void createGroup(ovgs.v1.CreateGroupRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.CreateGroupResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateGroupMethod(), responseObserver);
    }

    /**
     * <pre>
     * DeleteGroup deletes a named group. All associated cert_ids and child
     * groups must have been deleted and all associated components must have
     * been removed before the group can be deleted.
     * Errors will be returned:
     * INVALID_ARGUMENT if group_id = root group (the precreated root group
     * cannot be deleted) or if cert_ids, users, or child_group_ids is
     * non-empty.
     * NOT_FOUND if the group doesn't exist
     * PERMISSION_DENIED if user doesn't have access to parent group
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    default void deleteGroup(ovgs.v1.DeleteGroupRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.DeleteGroupResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteGroupMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetGroup returns the domain-certs (keyed by id), components,
     * user/role mappings for that group, and the child_group_ids.
     * Errors will be returned:
     * NOT_FOUND if the group doesn't exist
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    default void getGroup(ovgs.v1.GetGroupRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.GetGroupResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetGroupMethod(), responseObserver);
    }

    /**
     * <pre>
     * AddUserRole will assign a role to a user in a named group.
     * Username is unique to an username, org_id, user_type tuple.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field is empty
     * NOT_FOUND if the group doesn't exist, as specified in the request
     * FAILED_PRECONDITION if any of user tuple (username, org_id, user_type)
     * or group_id do not exist.
     * ALREADY_EXISTS if user already exists in the group
     * PERMISSION_DENIED if the user doesn't have access to the group.
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    default void addUserRole(ovgs.v1.AddUserRoleRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.AddUserRoleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddUserRoleMethod(), responseObserver);
    }

    /**
     * <pre>
     * RemoveUserRole removes a role from a user in a named group.
     * Username is unique to an username, org_id, user_type tuple.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field is empty
     * NOT_FOUND if the group doesn't exist or if the user tuple is not a
     * member of the group.
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    default void removeUserRole(ovgs.v1.RemoveUserRoleRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.RemoveUserRoleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveUserRoleMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetUserRole returns the roles the user is assigned in the group.
     * Username is unique to an username, org_id, user_type tuple.
     * A user can only view roles of another user in the groups that
     * it has a role assigned to.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field is empty
     * NOT_FOUND if the group doesn't exist or the user tuple is not a member.
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    default void getUserRole(ovgs.v1.GetUserRoleRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.GetUserRoleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUserRoleMethod(), responseObserver);
    }

    /**
     * <pre>
     * AddSerial assigns the component to the group.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of component or group_id is empty or the
     * IEN isn't applicable for the voucher issuer.
     * NOT_FOUND if the component or group_id doesn't exist
     * ALREADY_EXISTS if component is already a member of the group.
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    default void addSerial(ovgs.v1.AddSerialRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.AddSerialResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddSerialMethod(), responseObserver);
    }

    /**
     * <pre>
     * RemoveSerial removes the component from the group.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of component or group_id is empty or the
     * IEN isn't applicable for the voucher issuer.
     * NOT_FOUND if the component or group_id doesn't exist
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    default void removeSerial(ovgs.v1.RemoveSerialRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.RemoveSerialResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveSerialMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetSerial returns component, groups the component belongs to.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of component or group_id is empty or the
     * IEN isn't applicable for the voucher issuer.
     * NOT_FOUND if the component doesn't exist.
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    default void getSerial(ovgs.v1.GetSerialRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.GetSerialResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSerialMethod(), responseObserver);
    }

    /**
     * <pre>
     * CreateDomainCert creates the certificate in the group.
     * Errors will be returned:
     * INVALID_ARGUMENT if expiry_time is empty or in the past, the
     * supplied cert is invalid (such expired or malformed), or any of the
     * fields are empty.
     * NOT_FOUND if the group_id doesn't exist.
     * ALREADY_EXISTS if the certificate_der,revocation_checks,expiry_time
     * tuple already exists in the group.
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    default void createDomainCert(ovgs.v1.CreateDomainCertRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.CreateDomainCertResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateDomainCertMethod(), responseObserver);
    }

    /**
     * <pre>
     * DeleteDomainCert deletes the cert_id.
     * Errors will be returned:
     * NOT_FOUND if the cert_id doesn't exist
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    default void deleteDomainCert(ovgs.v1.DeleteDomainCertRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.DeleteDomainCertResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteDomainCertMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetDomainCert returns the details of the cert_id.
     * NOT_FOUND if the cert_id doesn't exist.
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    default void getDomainCert(ovgs.v1.GetDomainCertRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.GetDomainCertResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDomainCertMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetOwnershipVoucher issues an ownership voucher for the component (if it
     * exists/if applicable)
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of the request is empty, lifetime is in
     * the past, or the IEN supplied isn't applicable for the voucher issuer
     * FAILED_PRECONDITION if the component or cert_id do not exist.
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    default void getOwnershipVoucher(ovgs.v1.GetOwnershipVoucherRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.GetOwnershipVoucherResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOwnershipVoucherMethod(), responseObserver);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * </pre>
     */
    default void listSuperAdmins(ovgs.v1.ListSuperAdminsRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.ListSuperAdminsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListSuperAdminsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * </pre>
     */
    default void upsertThirdPartyDelegation(ovgs.v1.UpsertThirdPartyDelegationRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.UpsertThirdPartyDelegationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpsertThirdPartyDelegationMethod(), responseObserver);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * Deletes a third-party delegation by delegation_id.
     * </pre>
     */
    default void deleteThirdPartyDelegation(ovgs.v1.DeleteThirdPartyDelegationRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.DeleteThirdPartyDelegationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteThirdPartyDelegationMethod(), responseObserver);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * Retrieves a third-party delegation by delegation_id.
     * </pre>
     */
    default void getThirdPartyDelegation(ovgs.v1.GetThirdPartyDelegationRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.GetThirdPartyDelegationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetThirdPartyDelegationMethod(), responseObserver);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * Lists third-party delegations for a vendor organization and group.
     * </pre>
     */
    default void listThirdPartyDelegations(ovgs.v1.ListThirdPartyDelegationsRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.ListThirdPartyDelegationsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListThirdPartyDelegationsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service OwnershipVoucherService.
   */
  public static abstract class OwnershipVoucherServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return OwnershipVoucherServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service OwnershipVoucherService.
   */
  public static final class OwnershipVoucherServiceStub
      extends io.grpc.stub.AbstractAsyncStub<OwnershipVoucherServiceStub> {
    private OwnershipVoucherServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OwnershipVoucherServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OwnershipVoucherServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * CreateGroup creates a group as a child of an existing group.
     * Errors will be returned:
     * INVALID_ARGUMENT if either parent or description is empty
     * NOT_FOUND if the parent group doesn't exist, as specified in request
     * ALREADY_EXISTS if a group already exists with the same parent group
     * and description.
     * PERMISSION_DENIED if the user doesn't have access to the parent group
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    public void createGroup(ovgs.v1.CreateGroupRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.CreateGroupResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateGroupMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * DeleteGroup deletes a named group. All associated cert_ids and child
     * groups must have been deleted and all associated components must have
     * been removed before the group can be deleted.
     * Errors will be returned:
     * INVALID_ARGUMENT if group_id = root group (the precreated root group
     * cannot be deleted) or if cert_ids, users, or child_group_ids is
     * non-empty.
     * NOT_FOUND if the group doesn't exist
     * PERMISSION_DENIED if user doesn't have access to parent group
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    public void deleteGroup(ovgs.v1.DeleteGroupRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.DeleteGroupResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteGroupMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * GetGroup returns the domain-certs (keyed by id), components,
     * user/role mappings for that group, and the child_group_ids.
     * Errors will be returned:
     * NOT_FOUND if the group doesn't exist
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public void getGroup(ovgs.v1.GetGroupRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.GetGroupResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetGroupMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * AddUserRole will assign a role to a user in a named group.
     * Username is unique to an username, org_id, user_type tuple.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field is empty
     * NOT_FOUND if the group doesn't exist, as specified in the request
     * FAILED_PRECONDITION if any of user tuple (username, org_id, user_type)
     * or group_id do not exist.
     * ALREADY_EXISTS if user already exists in the group
     * PERMISSION_DENIED if the user doesn't have access to the group.
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    public void addUserRole(ovgs.v1.AddUserRoleRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.AddUserRoleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddUserRoleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * RemoveUserRole removes a role from a user in a named group.
     * Username is unique to an username, org_id, user_type tuple.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field is empty
     * NOT_FOUND if the group doesn't exist or if the user tuple is not a
     * member of the group.
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    public void removeUserRole(ovgs.v1.RemoveUserRoleRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.RemoveUserRoleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveUserRoleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * GetUserRole returns the roles the user is assigned in the group.
     * Username is unique to an username, org_id, user_type tuple.
     * A user can only view roles of another user in the groups that
     * it has a role assigned to.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field is empty
     * NOT_FOUND if the group doesn't exist or the user tuple is not a member.
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public void getUserRole(ovgs.v1.GetUserRoleRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.GetUserRoleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUserRoleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * AddSerial assigns the component to the group.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of component or group_id is empty or the
     * IEN isn't applicable for the voucher issuer.
     * NOT_FOUND if the component or group_id doesn't exist
     * ALREADY_EXISTS if component is already a member of the group.
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    public void addSerial(ovgs.v1.AddSerialRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.AddSerialResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddSerialMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * RemoveSerial removes the component from the group.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of component or group_id is empty or the
     * IEN isn't applicable for the voucher issuer.
     * NOT_FOUND if the component or group_id doesn't exist
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    public void removeSerial(ovgs.v1.RemoveSerialRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.RemoveSerialResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveSerialMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * GetSerial returns component, groups the component belongs to.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of component or group_id is empty or the
     * IEN isn't applicable for the voucher issuer.
     * NOT_FOUND if the component doesn't exist.
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public void getSerial(ovgs.v1.GetSerialRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.GetSerialResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSerialMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * CreateDomainCert creates the certificate in the group.
     * Errors will be returned:
     * INVALID_ARGUMENT if expiry_time is empty or in the past, the
     * supplied cert is invalid (such expired or malformed), or any of the
     * fields are empty.
     * NOT_FOUND if the group_id doesn't exist.
     * ALREADY_EXISTS if the certificate_der,revocation_checks,expiry_time
     * tuple already exists in the group.
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    public void createDomainCert(ovgs.v1.CreateDomainCertRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.CreateDomainCertResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateDomainCertMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * DeleteDomainCert deletes the cert_id.
     * Errors will be returned:
     * NOT_FOUND if the cert_id doesn't exist
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    public void deleteDomainCert(ovgs.v1.DeleteDomainCertRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.DeleteDomainCertResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteDomainCertMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * GetDomainCert returns the details of the cert_id.
     * NOT_FOUND if the cert_id doesn't exist.
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public void getDomainCert(ovgs.v1.GetDomainCertRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.GetDomainCertResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDomainCertMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * GetOwnershipVoucher issues an ownership voucher for the component (if it
     * exists/if applicable)
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of the request is empty, lifetime is in
     * the past, or the IEN supplied isn't applicable for the voucher issuer
     * FAILED_PRECONDITION if the component or cert_id do not exist.
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public void getOwnershipVoucher(ovgs.v1.GetOwnershipVoucherRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.GetOwnershipVoucherResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOwnershipVoucherMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * </pre>
     */
    public void listSuperAdmins(ovgs.v1.ListSuperAdminsRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.ListSuperAdminsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListSuperAdminsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * </pre>
     */
    public void upsertThirdPartyDelegation(ovgs.v1.UpsertThirdPartyDelegationRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.UpsertThirdPartyDelegationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpsertThirdPartyDelegationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * Deletes a third-party delegation by delegation_id.
     * </pre>
     */
    public void deleteThirdPartyDelegation(ovgs.v1.DeleteThirdPartyDelegationRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.DeleteThirdPartyDelegationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteThirdPartyDelegationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * Retrieves a third-party delegation by delegation_id.
     * </pre>
     */
    public void getThirdPartyDelegation(ovgs.v1.GetThirdPartyDelegationRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.GetThirdPartyDelegationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetThirdPartyDelegationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * Lists third-party delegations for a vendor organization and group.
     * </pre>
     */
    public void listThirdPartyDelegations(ovgs.v1.ListThirdPartyDelegationsRequest request,
        io.grpc.stub.StreamObserver<ovgs.v1.ListThirdPartyDelegationsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListThirdPartyDelegationsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service OwnershipVoucherService.
   */
  public static final class OwnershipVoucherServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<OwnershipVoucherServiceBlockingStub> {
    private OwnershipVoucherServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OwnershipVoucherServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OwnershipVoucherServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * CreateGroup creates a group as a child of an existing group.
     * Errors will be returned:
     * INVALID_ARGUMENT if either parent or description is empty
     * NOT_FOUND if the parent group doesn't exist, as specified in request
     * ALREADY_EXISTS if a group already exists with the same parent group
     * and description.
     * PERMISSION_DENIED if the user doesn't have access to the parent group
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    public ovgs.v1.CreateGroupResponse createGroup(ovgs.v1.CreateGroupRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateGroupMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * DeleteGroup deletes a named group. All associated cert_ids and child
     * groups must have been deleted and all associated components must have
     * been removed before the group can be deleted.
     * Errors will be returned:
     * INVALID_ARGUMENT if group_id = root group (the precreated root group
     * cannot be deleted) or if cert_ids, users, or child_group_ids is
     * non-empty.
     * NOT_FOUND if the group doesn't exist
     * PERMISSION_DENIED if user doesn't have access to parent group
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    public ovgs.v1.DeleteGroupResponse deleteGroup(ovgs.v1.DeleteGroupRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteGroupMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetGroup returns the domain-certs (keyed by id), components,
     * user/role mappings for that group, and the child_group_ids.
     * Errors will be returned:
     * NOT_FOUND if the group doesn't exist
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public ovgs.v1.GetGroupResponse getGroup(ovgs.v1.GetGroupRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetGroupMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * AddUserRole will assign a role to a user in a named group.
     * Username is unique to an username, org_id, user_type tuple.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field is empty
     * NOT_FOUND if the group doesn't exist, as specified in the request
     * FAILED_PRECONDITION if any of user tuple (username, org_id, user_type)
     * or group_id do not exist.
     * ALREADY_EXISTS if user already exists in the group
     * PERMISSION_DENIED if the user doesn't have access to the group.
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    public ovgs.v1.AddUserRoleResponse addUserRole(ovgs.v1.AddUserRoleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddUserRoleMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * RemoveUserRole removes a role from a user in a named group.
     * Username is unique to an username, org_id, user_type tuple.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field is empty
     * NOT_FOUND if the group doesn't exist or if the user tuple is not a
     * member of the group.
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    public ovgs.v1.RemoveUserRoleResponse removeUserRole(ovgs.v1.RemoveUserRoleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveUserRoleMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetUserRole returns the roles the user is assigned in the group.
     * Username is unique to an username, org_id, user_type tuple.
     * A user can only view roles of another user in the groups that
     * it has a role assigned to.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field is empty
     * NOT_FOUND if the group doesn't exist or the user tuple is not a member.
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public ovgs.v1.GetUserRoleResponse getUserRole(ovgs.v1.GetUserRoleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUserRoleMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * AddSerial assigns the component to the group.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of component or group_id is empty or the
     * IEN isn't applicable for the voucher issuer.
     * NOT_FOUND if the component or group_id doesn't exist
     * ALREADY_EXISTS if component is already a member of the group.
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    public ovgs.v1.AddSerialResponse addSerial(ovgs.v1.AddSerialRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddSerialMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * RemoveSerial removes the component from the group.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of component or group_id is empty or the
     * IEN isn't applicable for the voucher issuer.
     * NOT_FOUND if the component or group_id doesn't exist
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    public ovgs.v1.RemoveSerialResponse removeSerial(ovgs.v1.RemoveSerialRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveSerialMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetSerial returns component, groups the component belongs to.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of component or group_id is empty or the
     * IEN isn't applicable for the voucher issuer.
     * NOT_FOUND if the component doesn't exist.
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public ovgs.v1.GetSerialResponse getSerial(ovgs.v1.GetSerialRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSerialMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * CreateDomainCert creates the certificate in the group.
     * Errors will be returned:
     * INVALID_ARGUMENT if expiry_time is empty or in the past, the
     * supplied cert is invalid (such expired or malformed), or any of the
     * fields are empty.
     * NOT_FOUND if the group_id doesn't exist.
     * ALREADY_EXISTS if the certificate_der,revocation_checks,expiry_time
     * tuple already exists in the group.
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    public ovgs.v1.CreateDomainCertResponse createDomainCert(ovgs.v1.CreateDomainCertRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateDomainCertMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * DeleteDomainCert deletes the cert_id.
     * Errors will be returned:
     * NOT_FOUND if the cert_id doesn't exist
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    public ovgs.v1.DeleteDomainCertResponse deleteDomainCert(ovgs.v1.DeleteDomainCertRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteDomainCertMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetDomainCert returns the details of the cert_id.
     * NOT_FOUND if the cert_id doesn't exist.
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public ovgs.v1.GetDomainCertResponse getDomainCert(ovgs.v1.GetDomainCertRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDomainCertMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetOwnershipVoucher issues an ownership voucher for the component (if it
     * exists/if applicable)
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of the request is empty, lifetime is in
     * the past, or the IEN supplied isn't applicable for the voucher issuer
     * FAILED_PRECONDITION if the component or cert_id do not exist.
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public ovgs.v1.GetOwnershipVoucherResponse getOwnershipVoucher(ovgs.v1.GetOwnershipVoucherRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOwnershipVoucherMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * </pre>
     */
    public ovgs.v1.ListSuperAdminsResponse listSuperAdmins(ovgs.v1.ListSuperAdminsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListSuperAdminsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * </pre>
     */
    public ovgs.v1.UpsertThirdPartyDelegationResponse upsertThirdPartyDelegation(ovgs.v1.UpsertThirdPartyDelegationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpsertThirdPartyDelegationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * Deletes a third-party delegation by delegation_id.
     * </pre>
     */
    public ovgs.v1.DeleteThirdPartyDelegationResponse deleteThirdPartyDelegation(ovgs.v1.DeleteThirdPartyDelegationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteThirdPartyDelegationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * Retrieves a third-party delegation by delegation_id.
     * </pre>
     */
    public ovgs.v1.GetThirdPartyDelegationResponse getThirdPartyDelegation(ovgs.v1.GetThirdPartyDelegationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetThirdPartyDelegationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * Lists third-party delegations for a vendor organization and group.
     * </pre>
     */
    public ovgs.v1.ListThirdPartyDelegationsResponse listThirdPartyDelegations(ovgs.v1.ListThirdPartyDelegationsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListThirdPartyDelegationsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service OwnershipVoucherService.
   */
  public static final class OwnershipVoucherServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<OwnershipVoucherServiceFutureStub> {
    private OwnershipVoucherServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OwnershipVoucherServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OwnershipVoucherServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * CreateGroup creates a group as a child of an existing group.
     * Errors will be returned:
     * INVALID_ARGUMENT if either parent or description is empty
     * NOT_FOUND if the parent group doesn't exist, as specified in request
     * ALREADY_EXISTS if a group already exists with the same parent group
     * and description.
     * PERMISSION_DENIED if the user doesn't have access to the parent group
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.CreateGroupResponse> createGroup(
        ovgs.v1.CreateGroupRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateGroupMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * DeleteGroup deletes a named group. All associated cert_ids and child
     * groups must have been deleted and all associated components must have
     * been removed before the group can be deleted.
     * Errors will be returned:
     * INVALID_ARGUMENT if group_id = root group (the precreated root group
     * cannot be deleted) or if cert_ids, users, or child_group_ids is
     * non-empty.
     * NOT_FOUND if the group doesn't exist
     * PERMISSION_DENIED if user doesn't have access to parent group
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.DeleteGroupResponse> deleteGroup(
        ovgs.v1.DeleteGroupRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteGroupMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * GetGroup returns the domain-certs (keyed by id), components,
     * user/role mappings for that group, and the child_group_ids.
     * Errors will be returned:
     * NOT_FOUND if the group doesn't exist
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.GetGroupResponse> getGroup(
        ovgs.v1.GetGroupRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetGroupMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * AddUserRole will assign a role to a user in a named group.
     * Username is unique to an username, org_id, user_type tuple.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field is empty
     * NOT_FOUND if the group doesn't exist, as specified in the request
     * FAILED_PRECONDITION if any of user tuple (username, org_id, user_type)
     * or group_id do not exist.
     * ALREADY_EXISTS if user already exists in the group
     * PERMISSION_DENIED if the user doesn't have access to the group.
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.AddUserRoleResponse> addUserRole(
        ovgs.v1.AddUserRoleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddUserRoleMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * RemoveUserRole removes a role from a user in a named group.
     * Username is unique to an username, org_id, user_type tuple.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field is empty
     * NOT_FOUND if the group doesn't exist or if the user tuple is not a
     * member of the group.
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.RemoveUserRoleResponse> removeUserRole(
        ovgs.v1.RemoveUserRoleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveUserRoleMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * GetUserRole returns the roles the user is assigned in the group.
     * Username is unique to an username, org_id, user_type tuple.
     * A user can only view roles of another user in the groups that
     * it has a role assigned to.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field is empty
     * NOT_FOUND if the group doesn't exist or the user tuple is not a member.
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.GetUserRoleResponse> getUserRole(
        ovgs.v1.GetUserRoleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUserRoleMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * AddSerial assigns the component to the group.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of component or group_id is empty or the
     * IEN isn't applicable for the voucher issuer.
     * NOT_FOUND if the component or group_id doesn't exist
     * ALREADY_EXISTS if component is already a member of the group.
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.AddSerialResponse> addSerial(
        ovgs.v1.AddSerialRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddSerialMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * RemoveSerial removes the component from the group.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of component or group_id is empty or the
     * IEN isn't applicable for the voucher issuer.
     * NOT_FOUND if the component or group_id doesn't exist
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.RemoveSerialResponse> removeSerial(
        ovgs.v1.RemoveSerialRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveSerialMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * GetSerial returns component, groups the component belongs to.
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of component or group_id is empty or the
     * IEN isn't applicable for the voucher issuer.
     * NOT_FOUND if the component doesn't exist.
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.GetSerialResponse> getSerial(
        ovgs.v1.GetSerialRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSerialMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * CreateDomainCert creates the certificate in the group.
     * Errors will be returned:
     * INVALID_ARGUMENT if expiry_time is empty or in the past, the
     * supplied cert is invalid (such expired or malformed), or any of the
     * fields are empty.
     * NOT_FOUND if the group_id doesn't exist.
     * ALREADY_EXISTS if the certificate_der,revocation_checks,expiry_time
     * tuple already exists in the group.
     * PERMISSION_DENIED if the user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.CreateDomainCertResponse> createDomainCert(
        ovgs.v1.CreateDomainCertRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateDomainCertMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * DeleteDomainCert deletes the cert_id.
     * Errors will be returned:
     * NOT_FOUND if the cert_id doesn't exist
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.DeleteDomainCertResponse> deleteDomainCert(
        ovgs.v1.DeleteDomainCertRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteDomainCertMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * GetDomainCert returns the details of the cert_id.
     * NOT_FOUND if the cert_id doesn't exist.
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.GetDomainCertResponse> getDomainCert(
        ovgs.v1.GetDomainCertRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDomainCertMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * GetOwnershipVoucher issues an ownership voucher for the component (if it
     * exists/if applicable)
     * Errors will be returned:
     * INVALID_ARGUMENT if any field of the request is empty, lifetime is in
     * the past, or the IEN supplied isn't applicable for the voucher issuer
     * FAILED_PRECONDITION if the component or cert_id do not exist.
     * PERMISSION_DENIED if user doesn't have access to the group
     * Roles with permission to invoke this = ADMIN, ASSIGNER, REQUESTOR
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.GetOwnershipVoucherResponse> getOwnershipVoucher(
        ovgs.v1.GetOwnershipVoucherRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOwnershipVoucherMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.ListSuperAdminsResponse> listSuperAdmins(
        ovgs.v1.ListSuperAdminsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListSuperAdminsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.UpsertThirdPartyDelegationResponse> upsertThirdPartyDelegation(
        ovgs.v1.UpsertThirdPartyDelegationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpsertThirdPartyDelegationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * Deletes a third-party delegation by delegation_id.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.DeleteThirdPartyDelegationResponse> deleteThirdPartyDelegation(
        ovgs.v1.DeleteThirdPartyDelegationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteThirdPartyDelegationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * Retrieves a third-party delegation by delegation_id.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.GetThirdPartyDelegationResponse> getThirdPartyDelegation(
        ovgs.v1.GetThirdPartyDelegationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetThirdPartyDelegationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Roles with permission to invoke this = SUPER_ADMIN
     * Lists third-party delegations for a vendor organization and group.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ovgs.v1.ListThirdPartyDelegationsResponse> listThirdPartyDelegations(
        ovgs.v1.ListThirdPartyDelegationsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListThirdPartyDelegationsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_GROUP = 0;
  private static final int METHODID_DELETE_GROUP = 1;
  private static final int METHODID_GET_GROUP = 2;
  private static final int METHODID_ADD_USER_ROLE = 3;
  private static final int METHODID_REMOVE_USER_ROLE = 4;
  private static final int METHODID_GET_USER_ROLE = 5;
  private static final int METHODID_ADD_SERIAL = 6;
  private static final int METHODID_REMOVE_SERIAL = 7;
  private static final int METHODID_GET_SERIAL = 8;
  private static final int METHODID_CREATE_DOMAIN_CERT = 9;
  private static final int METHODID_DELETE_DOMAIN_CERT = 10;
  private static final int METHODID_GET_DOMAIN_CERT = 11;
  private static final int METHODID_GET_OWNERSHIP_VOUCHER = 12;
  private static final int METHODID_LIST_SUPER_ADMINS = 13;
  private static final int METHODID_UPSERT_THIRD_PARTY_DELEGATION = 14;
  private static final int METHODID_DELETE_THIRD_PARTY_DELEGATION = 15;
  private static final int METHODID_GET_THIRD_PARTY_DELEGATION = 16;
  private static final int METHODID_LIST_THIRD_PARTY_DELEGATIONS = 17;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_GROUP:
          serviceImpl.createGroup((ovgs.v1.CreateGroupRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.CreateGroupResponse>) responseObserver);
          break;
        case METHODID_DELETE_GROUP:
          serviceImpl.deleteGroup((ovgs.v1.DeleteGroupRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.DeleteGroupResponse>) responseObserver);
          break;
        case METHODID_GET_GROUP:
          serviceImpl.getGroup((ovgs.v1.GetGroupRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.GetGroupResponse>) responseObserver);
          break;
        case METHODID_ADD_USER_ROLE:
          serviceImpl.addUserRole((ovgs.v1.AddUserRoleRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.AddUserRoleResponse>) responseObserver);
          break;
        case METHODID_REMOVE_USER_ROLE:
          serviceImpl.removeUserRole((ovgs.v1.RemoveUserRoleRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.RemoveUserRoleResponse>) responseObserver);
          break;
        case METHODID_GET_USER_ROLE:
          serviceImpl.getUserRole((ovgs.v1.GetUserRoleRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.GetUserRoleResponse>) responseObserver);
          break;
        case METHODID_ADD_SERIAL:
          serviceImpl.addSerial((ovgs.v1.AddSerialRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.AddSerialResponse>) responseObserver);
          break;
        case METHODID_REMOVE_SERIAL:
          serviceImpl.removeSerial((ovgs.v1.RemoveSerialRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.RemoveSerialResponse>) responseObserver);
          break;
        case METHODID_GET_SERIAL:
          serviceImpl.getSerial((ovgs.v1.GetSerialRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.GetSerialResponse>) responseObserver);
          break;
        case METHODID_CREATE_DOMAIN_CERT:
          serviceImpl.createDomainCert((ovgs.v1.CreateDomainCertRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.CreateDomainCertResponse>) responseObserver);
          break;
        case METHODID_DELETE_DOMAIN_CERT:
          serviceImpl.deleteDomainCert((ovgs.v1.DeleteDomainCertRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.DeleteDomainCertResponse>) responseObserver);
          break;
        case METHODID_GET_DOMAIN_CERT:
          serviceImpl.getDomainCert((ovgs.v1.GetDomainCertRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.GetDomainCertResponse>) responseObserver);
          break;
        case METHODID_GET_OWNERSHIP_VOUCHER:
          serviceImpl.getOwnershipVoucher((ovgs.v1.GetOwnershipVoucherRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.GetOwnershipVoucherResponse>) responseObserver);
          break;
        case METHODID_LIST_SUPER_ADMINS:
          serviceImpl.listSuperAdmins((ovgs.v1.ListSuperAdminsRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.ListSuperAdminsResponse>) responseObserver);
          break;
        case METHODID_UPSERT_THIRD_PARTY_DELEGATION:
          serviceImpl.upsertThirdPartyDelegation((ovgs.v1.UpsertThirdPartyDelegationRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.UpsertThirdPartyDelegationResponse>) responseObserver);
          break;
        case METHODID_DELETE_THIRD_PARTY_DELEGATION:
          serviceImpl.deleteThirdPartyDelegation((ovgs.v1.DeleteThirdPartyDelegationRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.DeleteThirdPartyDelegationResponse>) responseObserver);
          break;
        case METHODID_GET_THIRD_PARTY_DELEGATION:
          serviceImpl.getThirdPartyDelegation((ovgs.v1.GetThirdPartyDelegationRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.GetThirdPartyDelegationResponse>) responseObserver);
          break;
        case METHODID_LIST_THIRD_PARTY_DELEGATIONS:
          serviceImpl.listThirdPartyDelegations((ovgs.v1.ListThirdPartyDelegationsRequest) request,
              (io.grpc.stub.StreamObserver<ovgs.v1.ListThirdPartyDelegationsResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreateGroupMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.CreateGroupRequest,
              ovgs.v1.CreateGroupResponse>(
                service, METHODID_CREATE_GROUP)))
        .addMethod(
          getDeleteGroupMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.DeleteGroupRequest,
              ovgs.v1.DeleteGroupResponse>(
                service, METHODID_DELETE_GROUP)))
        .addMethod(
          getGetGroupMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.GetGroupRequest,
              ovgs.v1.GetGroupResponse>(
                service, METHODID_GET_GROUP)))
        .addMethod(
          getAddUserRoleMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.AddUserRoleRequest,
              ovgs.v1.AddUserRoleResponse>(
                service, METHODID_ADD_USER_ROLE)))
        .addMethod(
          getRemoveUserRoleMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.RemoveUserRoleRequest,
              ovgs.v1.RemoveUserRoleResponse>(
                service, METHODID_REMOVE_USER_ROLE)))
        .addMethod(
          getGetUserRoleMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.GetUserRoleRequest,
              ovgs.v1.GetUserRoleResponse>(
                service, METHODID_GET_USER_ROLE)))
        .addMethod(
          getAddSerialMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.AddSerialRequest,
              ovgs.v1.AddSerialResponse>(
                service, METHODID_ADD_SERIAL)))
        .addMethod(
          getRemoveSerialMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.RemoveSerialRequest,
              ovgs.v1.RemoveSerialResponse>(
                service, METHODID_REMOVE_SERIAL)))
        .addMethod(
          getGetSerialMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.GetSerialRequest,
              ovgs.v1.GetSerialResponse>(
                service, METHODID_GET_SERIAL)))
        .addMethod(
          getCreateDomainCertMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.CreateDomainCertRequest,
              ovgs.v1.CreateDomainCertResponse>(
                service, METHODID_CREATE_DOMAIN_CERT)))
        .addMethod(
          getDeleteDomainCertMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.DeleteDomainCertRequest,
              ovgs.v1.DeleteDomainCertResponse>(
                service, METHODID_DELETE_DOMAIN_CERT)))
        .addMethod(
          getGetDomainCertMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.GetDomainCertRequest,
              ovgs.v1.GetDomainCertResponse>(
                service, METHODID_GET_DOMAIN_CERT)))
        .addMethod(
          getGetOwnershipVoucherMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.GetOwnershipVoucherRequest,
              ovgs.v1.GetOwnershipVoucherResponse>(
                service, METHODID_GET_OWNERSHIP_VOUCHER)))
        .addMethod(
          getListSuperAdminsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.ListSuperAdminsRequest,
              ovgs.v1.ListSuperAdminsResponse>(
                service, METHODID_LIST_SUPER_ADMINS)))
        .addMethod(
          getUpsertThirdPartyDelegationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.UpsertThirdPartyDelegationRequest,
              ovgs.v1.UpsertThirdPartyDelegationResponse>(
                service, METHODID_UPSERT_THIRD_PARTY_DELEGATION)))
        .addMethod(
          getDeleteThirdPartyDelegationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.DeleteThirdPartyDelegationRequest,
              ovgs.v1.DeleteThirdPartyDelegationResponse>(
                service, METHODID_DELETE_THIRD_PARTY_DELEGATION)))
        .addMethod(
          getGetThirdPartyDelegationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.GetThirdPartyDelegationRequest,
              ovgs.v1.GetThirdPartyDelegationResponse>(
                service, METHODID_GET_THIRD_PARTY_DELEGATION)))
        .addMethod(
          getListThirdPartyDelegationsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ovgs.v1.ListThirdPartyDelegationsRequest,
              ovgs.v1.ListThirdPartyDelegationsResponse>(
                service, METHODID_LIST_THIRD_PARTY_DELEGATIONS)))
        .build();
  }

  private static abstract class OwnershipVoucherServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OwnershipVoucherServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ovgs.v1.Ovgs.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("OwnershipVoucherService");
    }
  }

  private static final class OwnershipVoucherServiceFileDescriptorSupplier
      extends OwnershipVoucherServiceBaseDescriptorSupplier {
    OwnershipVoucherServiceFileDescriptorSupplier() {}
  }

  private static final class OwnershipVoucherServiceMethodDescriptorSupplier
      extends OwnershipVoucherServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    OwnershipVoucherServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (OwnershipVoucherServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OwnershipVoucherServiceFileDescriptorSupplier())
              .addMethod(getCreateGroupMethod())
              .addMethod(getDeleteGroupMethod())
              .addMethod(getGetGroupMethod())
              .addMethod(getAddUserRoleMethod())
              .addMethod(getRemoveUserRoleMethod())
              .addMethod(getGetUserRoleMethod())
              .addMethod(getAddSerialMethod())
              .addMethod(getRemoveSerialMethod())
              .addMethod(getGetSerialMethod())
              .addMethod(getCreateDomainCertMethod())
              .addMethod(getDeleteDomainCertMethod())
              .addMethod(getGetDomainCertMethod())
              .addMethod(getGetOwnershipVoucherMethod())
              .addMethod(getListSuperAdminsMethod())
              .addMethod(getUpsertThirdPartyDelegationMethod())
              .addMethod(getDeleteThirdPartyDelegationMethod())
              .addMethod(getGetThirdPartyDelegationMethod())
              .addMethod(getListThirdPartyDelegationsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
