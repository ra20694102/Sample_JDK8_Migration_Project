package com.example;

public class UserRepository {

    public User findByName(String name) {
        String normalized = name == null ? "guest" : name.trim().toLowerCase();
        String email = normalized + "@example.com";
        return new User(normalized, email);
    }
}
