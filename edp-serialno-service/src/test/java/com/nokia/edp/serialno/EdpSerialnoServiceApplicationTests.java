package com.nokia.edp.serialno;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Basic test to verify application context loads
 */
@SpringBootTest
@TestPropertySource(properties = {
    "job.run-on-start=false",
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
})
class EdpSerialnoServiceApplicationTests {

    @Test
    void contextLoads() {
        // Test that Spring context loads successfully
    }
}
