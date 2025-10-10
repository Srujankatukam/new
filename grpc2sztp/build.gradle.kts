import com.google.protobuf.gradle.*

plugins {
    id("java")
    id("application")
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.google.protobuf") version "0.9.4"
}

group = "nokia.grpc"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val grpcVersion = "1.64.0"
val protobufVersion = "3.25.3"

dependencies {
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    
    // gRPC dependencies
    implementation("io.grpc:grpc-netty-shaded:${grpcVersion}")
    implementation("io.grpc:grpc-protobuf:${grpcVersion}")
    implementation("io.grpc:grpc-stub:${grpcVersion}")
    implementation("com.google.protobuf:protobuf-java:${protobufVersion}")
    implementation("com.google.protobuf:protobuf-java-util:${protobufVersion}")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    
    // Database Drivers (from EDP service)
    implementation("com.oracle.database.jdbc:ojdbc11:23.3.0.23.09")
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.4.2.jre11")
    
    // HTTP Client dependencies
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    // JSON processing
    implementation("com.google.code.gson:gson:2.10.1")
    
    // Configuration
    implementation("org.apache.commons:commons-configuration2:2.9.0")
    implementation("commons-beanutils:commons-beanutils:1.9.4")
    
    // Lombok for reducing boilerplate
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    
    // Logging
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.5.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
    testImplementation("io.grpc:grpc-testing:${grpcVersion}")
    testImplementation("io.grpc:grpc-inprocess:${grpcVersion}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${protobufVersion}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc") {}
            }
        }
    }
}

sourceSets {
    main {
        proto {
            srcDir("protos")
        }
        java {
            srcDirs("src/main/java", "build/generated/source/proto/main/grpc", "build/generated/source/proto/main/java")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.nokia.grpc2sztp.Application")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    archiveFileName.set("grpc2sztp-integrated.jar")
    mainClass.set("com.nokia.grpc2sztp.Application")
}

tasks.named<Jar>("jar") {
    enabled = false
}
