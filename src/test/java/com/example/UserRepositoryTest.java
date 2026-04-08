package com.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserRepositoryTest {

    @Test
    public void shouldNormalizeNameAndCreateEmail() {
        UserRepository repository = new UserRepository();

        User user = repository.findByName(" Alice ");

        assertEquals("alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
    }
}
