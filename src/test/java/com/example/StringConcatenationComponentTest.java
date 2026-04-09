package com.example;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

/**
 * Test cases for StringConcatenationComponent.
 * Tests inefficient string concatenation patterns that need modernization on JDK 11+.
 */
public class StringConcatenationComponentTest {
    
    @Test
    public void testConstructorInitialization() {
        StringConcatenationComponent component = 
            new StringConcatenationComponent("John Doe", "john@example.com", 30);
        
        assertNotNull(component);
    }
    
    @Test
    public void testGetUserSummaryOld() {
        StringConcatenationComponent component = 
            new StringConcatenationComponent("Alice", "alice@test.com", 25);
        
        String summary = component.getUserSummaryOld();
        
        assertTrue(summary.contains("Alice"));
        assertTrue(summary.contains("alice@test.com"));
        assertTrue(summary.contains("25"));
    }
    
    @Test
    public void testGetUserSummaryNew() {
        StringConcatenationComponent component = 
            new StringConcatenationComponent("Bob", "bob@test.com", 35);
        
        String summary = component.getUserSummaryNew();
        
        assertTrue(summary.contains("Bob"));
        assertTrue(summary.contains("bob@test.com"));
        assertTrue(summary.contains("35"));
    }
    
    @Test
    public void testBuildUserListOld() {
        StringConcatenationComponent component = 
            new StringConcatenationComponent("Test", "test@test.com", 20);
        
        List<String> users = Arrays.asList("User1", "User2", "User3");
        String result = component.buildUserListOld(users);
        
        assertTrue(result.contains("User1"));
        assertTrue(result.contains("User2"));
        assertTrue(result.contains("User3"));
    }
    
    @Test
    public void testBuildUserListNew() {
        StringConcatenationComponent component = 
            new StringConcatenationComponent("Test", "test@test.com", 20);
        
        List<String> users = Arrays.asList("User1", "User2", "User3");
        String result = component.buildUserListNew(users);
        
        assertTrue(result.contains("User1"));
        assertTrue(result.contains("User2"));
        assertTrue(result.contains("User3"));
    }
    
    @Test
    public void testBuildUserListOldEmpty() {
        StringConcatenationComponent component = 
            new StringConcatenationComponent("Test", "test@test.com", 20);
        
        List<String> emptyList = Arrays.asList();
        String result = component.buildUserListOld(emptyList);
        
        // Empty list should result in empty or minimal string
        assertNotNull(result);
    }
    
    @Test
    public void testBuildUserListNewEmpty() {
        StringConcatenationComponent component = 
            new StringConcatenationComponent("Test", "test@test.com", 20);
        
        List<String> emptyList = Arrays.asList();
        String result = component.buildUserListNew(emptyList);
        
        // Empty list should result in empty string
        assertNotNull(result);
        assertEquals("", result);
    }
    
    @Test
    public void testGetFullDetailsOld() {
        StringConcatenationComponent component = 
            new StringConcatenationComponent("Charlie", "charlie@test.com", 40);
        
        String details = component.getFullDetailsOld();
        
        assertTrue(details.contains("Charlie"));
        assertTrue(details.contains("charlie@test.com"));
        assertTrue(details.contains("40"));
        assertTrue(details.contains("\n"));
    }
    
    @Test
    public void testGetFullDetailsNew() {
        StringConcatenationComponent component = 
            new StringConcatenationComponent("Diana", "diana@test.com", 28);
        
        String details = component.getFullDetailsNew();
        
        assertTrue(details.contains("Diana"));
        assertTrue(details.contains("diana@test.com"));
        assertTrue(details.contains("28"));
    }
    
    @Test
    public void testGetFormattedOutput() {
        StringConcatenationComponent component = 
            new StringConcatenationComponent("Eve", "eve@test.com", 32);
        
        String output = component.getFormattedOutput();
        
        assertTrue(output.contains("Eve"));
        assertTrue(output.contains("eve@test.com"));
        assertTrue(output.contains("32"));
    }
    
    @Test
    public void testBuildUserListWithSingleUser() {
        StringConcatenationComponent component = 
            new StringConcatenationComponent("Test", "test@test.com", 20);
        
        List<String> singleUser = Arrays.asList("OnlyUser");
        
        String resultOld = component.buildUserListOld(singleUser);
        String resultNew = component.buildUserListNew(singleUser);
        
        assertTrue(resultOld.contains("OnlyUser"));
        assertTrue(resultNew.contains("OnlyUser"));
    }
    
    @Test
    public void testBuildUserListWithManyUsers() {
        StringConcatenationComponent component = 
            new StringConcatenationComponent("Test", "test@test.com", 20);
        
        List<String> manyUsers = Arrays.asList(
            "User1", "User2", "User3", "User4", "User5", 
            "User6", "User7", "User8", "User9", "User10"
        );
        
        String resultOld = component.buildUserListOld(manyUsers);
        String resultNew = component.buildUserListNew(manyUsers);
        
        // Both methods should produce valid results
        assertTrue(resultOld.contains("User1"));
        assertTrue(resultNew.contains("User1"));
        assertTrue(resultNew.contains("User10"));
    }
}
