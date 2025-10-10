package com.nokia.grpc2sztp.service;

import com.nokia.grpc2sztp.config.ConfigurationManager;
import com.nokia.grpc2sztp.dto.AuthDto;
import com.nokia.grpc2sztp.dto.GroupDto;
import com.nokia.grpc2sztp.dto.SerialDto;
import com.nokia.grpc2sztp.dto.UserRoleDto;
import com.nokia.grpc2sztp.dto.ComponentDto;
import com.nokia.grpc2sztp.exception.SztpApiException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SZTPWrapperServiceTest {

    private MockWebServer mockWebServer;
    private SZTPWrapperService service;
    private ConfigurationManager mockConfig;
    private MockedStatic<ConfigurationManager> configManagerMock;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        
        mockConfig = mock(ConfigurationManager.class);
        configManagerMock = Mockito.mockStatic(ConfigurationManager.class);
        configManagerMock.when(ConfigurationManager::getInstance).thenReturn(mockConfig);
        
        when(mockConfig.getSztpApiBaseUrl()).thenReturn(mockWebServer.url("/").toString());
        when(mockConfig.getApiTimeoutSeconds()).thenReturn(30);
        when(mockConfig.getRetryAttempts()).thenReturn(3);
        when(mockConfig.getApiEndpoint(anyString())).thenAnswer(invocation -> 
            mockWebServer.url("/" + invocation.getArgument(0)).toString());
        
        service = SZTPWrapperService.getInstance();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
        configManagerMock.close();
        // Reset singleton instance
        try {
            var field = SZTPWrapperService.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null, null);
        } catch (Exception e) {
            // Ignore
        }
    }

    @Test
    void authenticateUser_Success() throws Exception {
        // Arrange
        String responseJson = "{\"token\":\"test-token\",\"userId\":\"user123\",\"role\":\"admin\",\"expiresAt\":1234567890}";
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(responseJson));

        AuthDto.AuthRequest request = new AuthDto.AuthRequest("testuser", "testpass", "testorg");

        // Act
        AuthDto.AuthResponse response = service.authenticateUser(request);

        // Assert
        assertNotNull(response);
        assertEquals("test-token", response.getToken());
        assertEquals("user123", response.getUserId());
        assertEquals("admin", response.getRole());
        assertEquals(1234567890L, response.getExpiresAt());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertTrue(recordedRequest.getPath().contains("auth/login"));
    }

    @Test
    void authenticateUser_ApiFailure() {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));
        AuthDto.AuthRequest request = new AuthDto.AuthRequest("testuser", "testpass", "testorg");

        // Act & Assert
        assertThrows(SztpApiException.class, () -> service.authenticateUser(request));
    }

    @Test
    void createGroup_Success() throws Exception {
        // Arrange
        String responseJson = "{\"groupId\":\"group123\",\"status\":\"success\",\"message\":\"Group created\"}";
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(responseJson));

        GroupDto.CreateGroupRequest request = new GroupDto.CreateGroupRequest("parent123", "Test Group");

        // Act
        GroupDto.CreateGroupResponse response = service.createGroup(request);

        // Assert
        assertNotNull(response);
        assertEquals("group123", response.getGroupId());
        assertEquals("success", response.getStatus());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertTrue(recordedRequest.getPath().contains("groups/"));
    }

    @Test
    void createGroup_ApiFailure() {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(400));
        GroupDto.CreateGroupRequest request = new GroupDto.CreateGroupRequest("parent123", "Test Group");

        // Act & Assert
        assertThrows(SztpApiException.class, () -> service.createGroup(request));
    }

    @Test
    void getGroup_Success() throws Exception {
        // Arrange
        String responseJson = "{\"groupId\":\"group123\",\"description\":\"Test Group\",\"childGroupIds\":[],\"users\":[],\"components\":[],\"certIds\":[]}";
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(responseJson));

        // Act
        GroupDto.GetGroupResponse response = service.getGroup("group123");

        // Assert
        assertNotNull(response);
        assertEquals("group123", response.getGroupId());
        assertEquals("Test Group", response.getDescription());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertTrue(recordedRequest.getPath().contains("groups/group123"));
    }

    @Test
    void deleteGroup_Success() throws Exception {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        // Act
        assertDoesNotThrow(() -> service.deleteGroup("group123"));

        // Assert
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("DELETE", recordedRequest.getMethod());
        assertTrue(recordedRequest.getPath().contains("groups/group123"));
    }

    @Test
    void addUserRole_Success() throws Exception {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));
        UserRoleDto.UserRoleRequest request = new UserRoleDto.UserRoleRequest();
        request.setGroupId("group123");
        request.setUsername("testuser");
        request.setUserType("USER");
        request.setOrgId("testorg");
        request.setUserRole("ADMIN");

        // Act
        assertDoesNotThrow(() -> service.addUserRole(request));

        // Assert
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertTrue(recordedRequest.getPath().contains("groups/group123/users/testuser/roles"));
    }

    @Test
    void removeUserRole_Success() throws Exception {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));
        UserRoleDto.UserRoleRequest request = new UserRoleDto.UserRoleRequest();
        request.setGroupId("group123");
        request.setUsername("testuser");

        // Act
        assertDoesNotThrow(() -> service.removeUserRole(request));

        // Assert
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("DELETE", recordedRequest.getMethod());
        assertTrue(recordedRequest.getPath().contains("groups/group123/users/testuser/roles"));
    }

    @Test
    void getUserRole_Success() throws Exception {
        // Arrange
        String responseJson = "{\"status\":\"success\",\"groups\":{\"group123\":\"ADMIN\"}}";
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(responseJson));

        // Act
        UserRoleDto.UserRoleResponse response = service.getUserRole("testuser", "USER", "testorg");

        // Assert
        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertNotNull(response.getGroups());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertTrue(recordedRequest.getPath().contains("users/testuser/roles"));
    }

    @Test
    void addSerial_Success() throws Exception {
        // Arrange
        String responseJson = "{\"status\":\"success\",\"message\":\"Serial added\"}";
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(responseJson));

        ComponentDto component = new ComponentDto("30065", "SN123456");
        SerialDto.AddSerialRequest request = new SerialDto.AddSerialRequest("group123", component);

        // Act
        SerialDto.AddSerialResponse response = service.addSerial(request);

        // Assert
        assertNotNull(response);
        assertEquals("success", response.getStatus());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertTrue(recordedRequest.getPath().contains("groups/group123/serials"));
    }

    @Test
    void getSerial_Success() throws Exception {
        // Arrange
        String responseJson = "{\"groupIds\":[\"group123\"],\"model\":\"TestModel\",\"macAddr\":\"00:11:22:33:44:55\",\"status\":\"success\"}";
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(responseJson));

        // Act
        SerialDto.GetSerialResponse response = service.getSerial("30065", "SN123456");

        // Assert
        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertNotNull(response.getGroupIds());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertTrue(recordedRequest.getPath().contains("serials/SN123456"));
    }

    @Test
    void removeSerial_Success() throws Exception {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));
        ComponentDto component = new ComponentDto("30065", "SN123456");

        // Act
        assertDoesNotThrow(() -> service.removeSerial("group123", component));

        // Assert
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("DELETE", recordedRequest.getMethod());
        assertTrue(recordedRequest.getPath().contains("groups/group123/serials/SN123456"));
    }
}