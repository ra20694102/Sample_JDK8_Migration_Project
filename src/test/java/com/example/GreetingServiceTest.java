package com.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreetingServiceTest {

    @Test
    public void shouldCreateGreetingWithCapitalizedName() {
        GreetingService service = new GreetingService();
        User user = new User("alice", "alice@example.com");

        String greeting = service.createGreeting(user);

        assertEquals(
                "Hello, Alice! Welcome to Spring 4 and Apache Commons Lang. Your email is alice@example.com.",
                greeting
        );
    }
}
