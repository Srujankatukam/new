package com.nokia.grpc2sztp.service;

import com.nokia.grpc2sztp.dto.AuthDto;
import com.nokia.grpc2sztp.exception.SztpApiException;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import login.v1.AuthRequest;
import login.v1.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Mock
    private SZTPWrapperService mockSztpService;

    @Mock
    private StreamObserver<AuthResponse> mockResponseObserver;

    private LoginServiceImpl loginService;
    private MockedStatic<SZTPWrapperService> sztpServiceMock;

    @BeforeEach
    void setUp() {
        sztpServiceMock = Mockito.mockStatic(SZTPWrapperService.class);
        sztpServiceMock.when(SZTPWrapperService::getInstance).thenReturn(mockSztpService);
        loginService = new LoginServiceImpl();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        sztpServiceMock.close();
    }

    @Test
    void login_Success() throws SztpApiException {
        // Arrange
        AuthRequest request = AuthRequest.newBuilder()
                .setUsername("testuser")
                .setPassword("testpass")
                .setOrgId("testorg")
                .build();

        AuthDto.AuthResponse sztpResponse = new AuthDto.AuthResponse();
        sztpResponse.setToken("test-jwt-token");
        sztpResponse.setRole("admin");

        when(mockSztpService.authenticateUser(any(AuthDto.AuthRequest.class)))
                .thenReturn(sztpResponse);

        // Act
        loginService.login(request, mockResponseObserver);

        // Assert
        verify(mockSztpService).authenticateUser(any(AuthDto.AuthRequest.class));
        verify(mockResponseObserver).onNext(any(AuthResponse.class));
        verify(mockResponseObserver).onCompleted();
        verify(mockResponseObserver, never()).onError(any());

        // Verify the AuthRequest sent to SZTP service
        var argumentCaptor = org.mockito.ArgumentCaptor.forClass(AuthDto.AuthRequest.class);
        verify(mockSztpService).authenticateUser(argumentCaptor.capture());
        AuthDto.AuthRequest capturedRequest = argumentCaptor.getValue();
        assertEquals("testuser", capturedRequest.getUsername());
        assertEquals("testpass", capturedRequest.getPassword());
        assertEquals("testorg", capturedRequest.getOrgId());
    }

    @Test
    void login_EmptyUsername() {
        // Arrange
        AuthRequest request = AuthRequest.newBuilder()
                .setUsername("")
                .setPassword("testpass")
                .setOrgId("testorg")
                .build();

        // Act
        loginService.login(request, mockResponseObserver);

        // Assert
        verify(mockResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockResponseObserver, never()).onNext(any());
        verify(mockResponseObserver, never()).onCompleted();
        verifyNoInteractions(mockSztpService);
    }

    @Test
    void login_EmptyPassword() {
        // Arrange
        AuthRequest request = AuthRequest.newBuilder()
                .setUsername("testuser")
                .setPassword("")
                .setOrgId("testorg")
                .build();

        // Act
        loginService.login(request, mockResponseObserver);

        // Assert
        verify(mockResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockResponseObserver, never()).onNext(any());
        verify(mockResponseObserver, never()).onCompleted();
        verifyNoInteractions(mockSztpService);
    }

    @Test
    void login_EmptyOrgId() {
        // Arrange
        AuthRequest request = AuthRequest.newBuilder()
                .setUsername("testuser")
                .setPassword("testpass")
                .setOrgId("")
                .build();

        // Act
        loginService.login(request, mockResponseObserver);

        // Assert
        verify(mockResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockResponseObserver, never()).onNext(any());
        verify(mockResponseObserver, never()).onCompleted();
        verifyNoInteractions(mockSztpService);
    }

    @Test
    void login_InvalidCredentials() throws SztpApiException {
        // Arrange
        AuthRequest request = AuthRequest.newBuilder()
                .setUsername("testuser")
                .setPassword("wrongpass")
                .setOrgId("testorg")
                .build();

        when(mockSztpService.authenticateUser(any(AuthDto.AuthRequest.class)))
                .thenReturn(null); // SZTP API returns null for invalid credentials

        // Act
        loginService.login(request, mockResponseObserver);

        // Assert
        verify(mockSztpService).authenticateUser(any(AuthDto.AuthRequest.class));
        verify(mockResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockResponseObserver, never()).onNext(any());
        verify(mockResponseObserver, never()).onCompleted();
    }

    @Test
    void login_SztpApiException() throws SztpApiException {
        // Arrange
        AuthRequest request = AuthRequest.newBuilder()
                .setUsername("testuser")
                .setPassword("testpass")
                .setOrgId("testorg")
                .build();

        when(mockSztpService.authenticateUser(any(AuthDto.AuthRequest.class)))
                .thenThrow(new SztpApiException("API Error"));

        // Act
        loginService.login(request, mockResponseObserver);

        // Assert
        verify(mockSztpService).authenticateUser(any(AuthDto.AuthRequest.class));
        verify(mockResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockResponseObserver, never()).onNext(any());
        verify(mockResponseObserver, never()).onCompleted();
    }

    @Test
    void login_UnexpectedException() throws SztpApiException {
        // Arrange
        AuthRequest request = AuthRequest.newBuilder()
                .setUsername("testuser")
                .setPassword("testpass")
                .setOrgId("testorg")
                .build();

        when(mockSztpService.authenticateUser(any(AuthDto.AuthRequest.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Act
        loginService.login(request, mockResponseObserver);

        // Assert
        verify(mockSztpService).authenticateUser(any(AuthDto.AuthRequest.class));
        verify(mockResponseObserver).onError(any(StatusRuntimeException.class));
        verify(mockResponseObserver, never()).onNext(any());
        verify(mockResponseObserver, never()).onCompleted();
    }
}