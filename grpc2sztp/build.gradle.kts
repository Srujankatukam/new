import com.google.protobuf.gradle.*

plugins {
    id("java")
    id("application")
    id("com.google.protobuf") version "0.9.4"
}

group = "nokia.grpc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val grpcVersion = "1.64.0"
val protobufVersion = "3.25.3"

dependencies {
    implementation("io.grpc:grpc-netty-shaded:${grpcVersion}")
    implementation("io.grpc:grpc-protobuf:${grpcVersion}")
    implementation("io.grpc:grpc-stub:${grpcVersion}")
    implementation("com.google.protobuf:protobuf-java:${protobufVersion}")
    implementation("com.google.protobuf:protobuf-java-util:${protobufVersion}")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    
    // HTTP Client dependencies
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    // JSON processing
    implementation("com.google.code.gson:gson:2.10.1")
    
    // Configuration
    implementation("org.apache.commons:commons-configuration2:2.9.0")
    implementation("commons-beanutils:commons-beanutils:1.9.4")
    
    // Logging
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")

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

// Create fat JAR with all dependencies
tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.nokia.grpc2sztp.Application"
        )
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// Create separate task for running client
// tasks.register<JavaExec>("runClient") {
//     group = "application"
//     description = "Run the gRPC test client"
//     classpath = sourceSets.main.get().runtimeClasspath
//     mainClass.set("com.nokia.grpc2sztp.TestClient")
// }
