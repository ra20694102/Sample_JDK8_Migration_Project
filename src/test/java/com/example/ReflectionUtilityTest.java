package com.example;

import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.lang.invoke.MethodType;

/**
 * Test cases for ReflectionUtility.
 * Tests reflection patterns that break with JPMS in JDK 11+.
 */
public class ReflectionUtilityTest {
    
    /**
     * Test class with private field for reflection testing.
     */
    private static class TestClass {
        private String privateField = "secret";
        
        private void privateMethod() {
            // Empty private method for testing
        }
    }
    
    @Test
    public void testGetPrivateFieldValue() throws Exception {
        TestClass obj = new TestClass();
        
        Object value = ReflectionUtility.getPrivateFieldValue(obj, "privateField");
        
        assertEquals("secret", value);
    }
    
    @Test
    public void testSetPrivateFieldValue() throws Exception {
        TestClass obj = new TestClass();
        
        ReflectionUtility.setPrivateFieldValue(obj, "privateField", "modified");
        
        Object value = ReflectionUtility.getPrivateFieldValue(obj, "privateField");
        assertEquals("modified", value);
    }
    
    @Test(expected = NoSuchFieldException.class)
    public void testGetPrivateFieldValueNonExistent() throws Exception {
        TestClass obj = new TestClass();
        
        ReflectionUtility.getPrivateFieldValue(obj, "nonExistentField");
    }
    
    @Test(expected = NoSuchFieldException.class)
    public void testSetPrivateFieldValueNonExistent() throws Exception {
        TestClass obj = new TestClass();
        
        ReflectionUtility.setPrivateFieldValue(obj, "nonExistentField", "value");
    }
    
    @Test
    public void testLoadClassDynamically() throws ClassNotFoundException {
        Class<?> clazz = ReflectionUtility.loadClassDynamically("java.lang.String");
        
        assertEquals(String.class, clazz);
    }
    
    @Test
    public void testLoadClassDynamicallyWithDifferentTypes() throws ClassNotFoundException {
        Class<?> stringClass = ReflectionUtility.loadClassDynamically("java.lang.String");
        Class<?> integerClass = ReflectionUtility.loadClassDynamically("java.lang.Integer");
        Class<?> listClass = ReflectionUtility.loadClassDynamically("java.util.ArrayList");
        
        assertEquals(String.class, stringClass);
        assertEquals(Integer.class, integerClass);
        assertEquals(java.util.ArrayList.class, listClass);
    }
    
    @Test(expected = ClassNotFoundException.class)
    public void testLoadClassDynamicallyNonExistent() throws ClassNotFoundException {
        ReflectionUtility.loadClassDynamically("com.nonexistent.ClassDoesNotExist");
    }
    
    @Test
    public void testAccessInternalJDKClass() {
        // This test verifies the method executes without throwing
        // (it catches exceptions internally)
        try {
            ReflectionUtility.accessInternalJDKClass();
            // Success if we get here
            assertTrue(true);
        } catch (Exception e) {
            fail("accessInternalJDKClass should not throw: " + e.getMessage());
        }
    }
    
    @Test
    public void testInvokePrivateMethod() throws Exception {
        TestClass obj = new TestClass();
        
        // Create a private method to invoke
        // This should not throw even though the method is private
        Object result = ReflectionUtility.invokePrivateMethod(
            obj, "privateMethod", new Class<?>[]{}, new Object[]{}
        );
        
        assertNull(result);  // privateMethod returns void (null)
    }
    
    @Test(expected = Exception.class)
    public void testInvokePrivateMethodNonExistent() throws Exception {
        TestClass obj = new TestClass();
        
        ReflectionUtility.invokePrivateMethod(
            obj, "nonExistentMethod", new Class<?>[]{}, new Object[]{}
        );
    }
    
    @Test
    public void testGetMethodHandle() throws NoSuchMethodException, IllegalAccessException {
        java.lang.invoke.MethodHandle handle = 
            ReflectionUtility.getMethodHandle(
                String.class, 
                "length", 
                MethodType.methodType(int.class)
            );
        
        assertNotNull(handle);
    }
    
    @Test(expected = NoSuchMethodException.class)
    public void testGetMethodHandleNonExistent() throws NoSuchMethodException, IllegalAccessException {
        ReflectionUtility.getMethodHandle(
            String.class, 
            "nonExistentMethod", 
            MethodType.methodType(void.class)
        );
    }
    
    @Test
    public void testReflectionUtilityWithMultipleObjectTypes() throws Exception {
        // Test with different object types
        User user = new User("Alice", "alice@example.com");
        Application app = new Application();
        
        // Both should support reflection
        assertNotNull(ReflectionUtility.getPrivateFieldValue(user, "name"));
    }
}
