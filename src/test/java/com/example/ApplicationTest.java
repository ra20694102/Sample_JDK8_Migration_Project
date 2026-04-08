package com.example;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class ApplicationTest {

    @Test
    public void mainShouldRunAndPrintGreeting() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            Application.main(new String[0]);
            String output = outputStream.toString();
            assertTrue(output.contains("Hello, Alice! Welcome to Spring 4 and Apache Commons Lang. Your email is alice@example.com."));
        } finally {
            System.setOut(originalOut);
        }
    }
}
