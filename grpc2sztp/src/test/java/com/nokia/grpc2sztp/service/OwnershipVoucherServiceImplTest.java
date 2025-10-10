package com.nokia.grpc2sztp.service;

import com.nokia.grpc2sztp.dto.ComponentDto;
import com.nokia.grpc2sztp.dto.GroupDto;
import com.nokia.grpc2sztp.dto.SerialDto;
import com.nokia.grpc2sztp.exception.SztpApiException;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ovgs.v1.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class OwnershipVoucherServiceImplTest {

    @Mock
    private SZTPWrapperService mockSztpService;

    @Mock
    private StreamObserver<CreateGroupResponse> mockCreateGroupResponseObserver;

    @Mock
    private StreamObserver<DeleteGroupResponse> mockDeleteGroupResponseObserver;

    @Mock
    private StreamObserver<AddSerialResponse> mockAddSerialResponseObserver;

    @Mock
    private StreamObserver<GetSerialResponse> mockGetSerialResponseObserver;

    @Mock
    private StreamObserver<RemoveSerialResponse> mockRemoveSerialResponseObserver;

    private OwnershipVoucherServiceImpl ownershipVoucherService;
    private MockedStatic<SZTPWrapperService> sztpServiceMock;

    @BeforeEach
    void setUp() {
        sztpServiceMock = Mockito.mockStatic(SZTPWrapperService.class);
        sztpServiceMock.when(SZTPWrapperService::getInstance).thenReturn(mockSztpService);
        ownershipVoucherService = new OwnershipVoucherServiceImpl();
    }

    @AfterEach
    void tearDown() {
        sztpServiceMock.close();
    }

    // ===== CREATE GROUP TESTS =====

    @Test
    void createGroup_Success() throws SztpApiException {
        // Arrange
        CreateGroupRequest request = CreateGroupRequest.newBuilder()
                .setParent("parent-group")
                .setDescription("Test group description")
                .build();

        GroupDto.CreateGroupResponse sztpResponse = new GroupDto.CreateGroupResponse();
        sztpResponse.setGroupId("new-group-id");

        when(mockSztpService.createGroup(any(GroupDto.CreateGroupRequest.class))).thenReturn(sztpResponse);

        // Act
        ownershipVoucherService.createGroup(request, mockCreateGroupResponseObserver);

        // Assert
        verify(mockSztpService).createGroup(any(GroupDto.CreateGroupRequest.class));
        verify(mockCreateGroupResponseObserver).onNext(any(CreateGroupResponse.class));
        verify(mockCreateGroupResponseObserver).onCompleted();
        verify(mockCreateGroupResponseObserver, never()).onError(any());
    }

    @Test
    void createGroup_EmptyParent() {
        // Arrange
        CreateGroupRequest request = CreateGroupRequest.newBuilder()
                .setParent("")
                .setDescription("Test description")
                .build();

        // Act
        ownershipVoucherService.createGroup(request, mockCreateGroupResponseObserver);

        // Assert
        verify(mockCreateGroupResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockCreateGroupResponseObserver, never()).onNext(any());
        verify(mockCreateGroupResponseObserver, never()).onCompleted();
        verifyNoInteractions(mockSztpService);
    }

    @Test
    void createGroup_EmptyDescription() {
        // Arrange
        CreateGroupRequest request = CreateGroupRequest.newBuilder()
                .setParent("parent-group")
                .setDescription("")
                .build();

        // Act
        ownershipVoucherService.createGroup(request, mockCreateGroupResponseObserver);

        // Assert
        verify(mockCreateGroupResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockCreateGroupResponseObserver, never()).onNext(any());
        verify(mockCreateGroupResponseObserver, never()).onCompleted();
        verifyNoInteractions(mockSztpService);
    }

    @Test
    void createGroup_SztpApiException() throws SztpApiException {
        // Arrange
        CreateGroupRequest request = CreateGroupRequest.newBuilder()
                .setParent("parent-group")
                .setDescription("Test description")
                .build();

        when(mockSztpService.createGroup(any(GroupDto.CreateGroupRequest.class)))
                .thenThrow(new SztpApiException("API Error"));

        // Act
        ownershipVoucherService.createGroup(request, mockCreateGroupResponseObserver);

        // Assert
        verify(mockSztpService).createGroup(any(GroupDto.CreateGroupRequest.class));
        verify(mockCreateGroupResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockCreateGroupResponseObserver, never()).onNext(any());
        verify(mockCreateGroupResponseObserver, never()).onCompleted();
    }

    // ===== DELETE GROUP TESTS =====

    @Test
    void deleteGroup_Success() throws SztpApiException {
        // Arrange
        DeleteGroupRequest request = DeleteGroupRequest.newBuilder()
                .setGroupId("test-group")
                .build();

        doNothing().when(mockSztpService).deleteGroup(anyString());

        // Act
        ownershipVoucherService.deleteGroup(request, mockDeleteGroupResponseObserver);

        // Assert
        verify(mockSztpService).deleteGroup("test-group");
        verify(mockDeleteGroupResponseObserver).onNext(any(DeleteGroupResponse.class));
        verify(mockDeleteGroupResponseObserver).onCompleted();
        verify(mockDeleteGroupResponseObserver, never()).onError(any());
    }

    @Test
    void deleteGroup_EmptyGroupId() {
        // Arrange
        DeleteGroupRequest request = DeleteGroupRequest.newBuilder()
                .setGroupId("")
                .build();

        // Act
        ownershipVoucherService.deleteGroup(request, mockDeleteGroupResponseObserver);

        // Assert
        verify(mockDeleteGroupResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockDeleteGroupResponseObserver, never()).onNext(any());
        verify(mockDeleteGroupResponseObserver, never()).onCompleted();
        verifyNoInteractions(mockSztpService);
    }

    // ===== ADD SERIAL TESTS =====

    @Test
    void addSerial_Success() throws SztpApiException {
        // Arrange
        Component component = Component.newBuilder()
                .setIen("12345")
                .setSerialNumber("SN123456")
                .build();

        AddSerialRequest request = AddSerialRequest.newBuilder()
                .setComponent(component)
                .setGroupId("test-group")
                .build();

        SerialDto.AddSerialResponse sztpResponse = new SerialDto.AddSerialResponse();

        when(mockSztpService.addSerial(any(SerialDto.AddSerialRequest.class))).thenReturn(sztpResponse);

        // Act
        ownershipVoucherService.addSerial(request, mockAddSerialResponseObserver);

        // Assert
        verify(mockSztpService).addSerial(any(SerialDto.AddSerialRequest.class));
        verify(mockAddSerialResponseObserver).onNext(any(AddSerialResponse.class));
        verify(mockAddSerialResponseObserver).onCompleted();
        verify(mockAddSerialResponseObserver, never()).onError(any());
    }

    @Test
    void addSerial_EmptyGroupId() {
        // Arrange
        Component component = Component.newBuilder()
                .setIen("12345")
                .setSerialNumber("SN123456")
                .build();

        AddSerialRequest request = AddSerialRequest.newBuilder()
                .setComponent(component)
                .setGroupId("")
                .build();

        // Act
        ownershipVoucherService.addSerial(request, mockAddSerialResponseObserver);

        // Assert
        verify(mockAddSerialResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockAddSerialResponseObserver, never()).onNext(any());
        verify(mockAddSerialResponseObserver, never()).onCompleted();
        verifyNoInteractions(mockSztpService);
    }

    @Test
    void addSerial_NullComponent() {
        // Arrange
        AddSerialRequest request = AddSerialRequest.newBuilder()
                .setGroupId("test-group")
                .build(); // Component is null by default

        // Act
        ownershipVoucherService.addSerial(request, mockAddSerialResponseObserver);

        // Assert
        verify(mockAddSerialResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockAddSerialResponseObserver, never()).onNext(any());
        verify(mockAddSerialResponseObserver, never()).onCompleted();
        verifyNoInteractions(mockSztpService);
    }

    // ===== GET SERIAL TESTS =====

    @Test
    void getSerial_Success() throws SztpApiException {
        // Arrange
        Component component = Component.newBuilder()
                .setIen("12345")
                .setSerialNumber("SN123456")
                .build();

        GetSerialRequest request = GetSerialRequest.newBuilder()
                .setComponent(component)
                .build();

        SerialDto.GetSerialResponse sztpResponse = new SerialDto.GetSerialResponse();
        sztpResponse.setGroupIds(new ArrayList<>(Arrays.asList("group1", "group2")));
        sztpResponse.setMacAddr("00:11:22:33:44:55");

        when(mockSztpService.getSerial(anyString(), anyString())).thenReturn(sztpResponse);

        // Act
        ownershipVoucherService.getSerial(request, mockGetSerialResponseObserver);

        // Assert
        verify(mockSztpService).getSerial("12345", "SN123456");
        verify(mockGetSerialResponseObserver).onNext(any(GetSerialResponse.class));
        verify(mockGetSerialResponseObserver).onCompleted();
        verify(mockGetSerialResponseObserver, never()).onError(any());
    }

    @Test
    void getSerial_NullComponent() {
        // Arrange
        GetSerialRequest request = GetSerialRequest.newBuilder()
                .build(); // Component is null by default

        // Act
        ownershipVoucherService.getSerial(request, mockGetSerialResponseObserver);

        // Assert
        verify(mockGetSerialResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockGetSerialResponseObserver, never()).onNext(any());
        verify(mockGetSerialResponseObserver, never()).onCompleted();
        verifyNoInteractions(mockSztpService);
    }

    // ===== REMOVE SERIAL TESTS =====

    @Test
    void removeSerial_Success() throws SztpApiException {
        // Arrange
        Component component = Component.newBuilder()
                .setIen("12345")
                .setSerialNumber("SN123456")
                .build();

        RemoveSerialRequest request = RemoveSerialRequest.newBuilder()
                .setComponent(component)
                .setGroupId("test-group")
                .build();

        doNothing().when(mockSztpService).removeSerial(anyString(), any(ComponentDto.class));

        // Act
        ownershipVoucherService.removeSerial(request, mockRemoveSerialResponseObserver);

        // Assert
        verify(mockSztpService).removeSerial(eq("test-group"), any(ComponentDto.class));
        verify(mockRemoveSerialResponseObserver).onNext(any(RemoveSerialResponse.class));
        verify(mockRemoveSerialResponseObserver).onCompleted();
        verify(mockRemoveSerialResponseObserver, never()).onError(any());
    }

    @Test
    void removeSerial_EmptyGroupId() {
        // Arrange
        Component component = Component.newBuilder()
                .setIen("12345")
                .setSerialNumber("SN123456")
                .build();

        RemoveSerialRequest request = RemoveSerialRequest.newBuilder()
                .setComponent(component)
                .setGroupId("")
                .build();

        // Act
        ownershipVoucherService.removeSerial(request, mockRemoveSerialResponseObserver);

        // Assert
        verify(mockRemoveSerialResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockRemoveSerialResponseObserver, never()).onNext(any());
        verify(mockRemoveSerialResponseObserver, never()).onCompleted();
        verifyNoInteractions(mockSztpService);
    }

    @Test
    void removeSerial_SztpApiException() throws SztpApiException {
        // Arrange
        Component component = Component.newBuilder()
                .setIen("12345")
                .setSerialNumber("SN123456")
                .build();

        RemoveSerialRequest request = RemoveSerialRequest.newBuilder()
                .setComponent(component)
                .setGroupId("test-group")
                .build();

        doThrow(new SztpApiException("API Error"))
                .when(mockSztpService).removeSerial(anyString(), any(ComponentDto.class));

        // Act
        ownershipVoucherService.removeSerial(request, mockRemoveSerialResponseObserver);

        // Assert
        verify(mockSztpService).removeSerial(eq("test-group"), any(ComponentDto.class));
        verify(mockRemoveSerialResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockRemoveSerialResponseObserver, never()).onNext(any());
        verify(mockRemoveSerialResponseObserver, never()).onCompleted();
    }
}