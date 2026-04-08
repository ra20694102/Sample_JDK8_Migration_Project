package com.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void shouldReturnNameEmailAndToString() {
        User user = new User("alice", "alice@example.com");

        assertEquals("alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("alice <alice@example.com>", user.toString());
    }
}
