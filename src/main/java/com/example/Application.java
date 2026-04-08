package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            UserRepository repository = context.getBean(UserRepository.class);
            GreetingService greetingService = context.getBean(GreetingService.class);

            User user = repository.findByName("alice");
            String greeting = greetingService.createGreeting(user);

            System.out.println(greeting);
        }
    }
}
