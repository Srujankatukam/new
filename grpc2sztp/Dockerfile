# Multi-stage build để tối ưu kích thước image

# Stage 1: Build application
FROM eclipse-temurin:17-jdk-jammy AS builder

# Install required tools
RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and properties
COPY gradlew gradle.properties* ./
COPY gradle/ gradle/

# Make gradlew executable
RUN chmod +x gradlew

# Copy build files
COPY build.gradle.kts settings.gradle.kts ./

# Copy source code
COPY src/ src/
COPY protos/ protos/

# Build application
RUN ./gradlew build -x test --no-daemon

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre-jammy

# Create non-root user for security
RUN groupadd -r grpcuser && useradd -r -g grpcuser grpcuser

# Set working directory
WORKDIR /app

# Copy built application from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Create directory for configuration and logs
RUN mkdir -p /app/config /app/logs && \
    chown -R grpcuser:grpcuser /app

# Switch to non-root user
USER grpcuser

# Expose gRPC port (default 9090)
EXPOSE 9090

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:9090/health || exit 1

# Set JVM options for containerized environment
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Run application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]