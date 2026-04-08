package com.example;

import org.apache.commons.lang3.StringUtils;

public class GreetingService {

    public String createGreeting(User user) {
        String name = StringUtils.capitalize(user.getName());
        return String.format(
                "Hello, %s! Welcome to Spring 4 and Apache Commons Lang. Your email is %s.",
                name,
                user.getEmail()
        );
    }
}
