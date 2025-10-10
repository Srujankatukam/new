package com.nokia.grpc2sztp.exception;

/**
 * Custom exception for SZTP API related errors
 */
public class SztpApiException extends Exception {
    private final int statusCode;
    private final String responseBody;
    
    public SztpApiException(String message) {
        super(message);
        this.statusCode = -1;
        this.responseBody = null;
    }
    
    public SztpApiException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = -1;
        this.responseBody = null;
    }
    
    public SztpApiException(String message, int statusCode, String responseBody) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public String getResponseBody() {
        return responseBody;
    }
}