package com.example;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AppConfigTest {

    @Test
    public void shouldCreateSpringBeans() {
        AppConfig config = new AppConfig();

        assertNotNull(config.userRepository());
        assertNotNull(config.greetingService());
    }
}
