package com.example;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Component demonstrating reflection patterns that break with JPMS in JDK 11+.
 * 
 * ⚠️ MIGRATION NOTE: Java 9+ introduced the Java Platform Module System (JPMS).
 * Reflection on non-exported internal APIs will fail without --add-opens or --add-exports.
 * 
 * ACTION FOR JDK 11:
 * - Avoid accessing private fields of other classes via reflection
 * - Use proper dependency injection instead of reflection hacks
 * - Replace reflection-based frameworks with modern alternatives
 * - Only use reflection on explicitly exported APIs
 * - Consider MethodHandles as alternative to Reflection API
 */
public class ReflectionUtility {
    
    /**
     * Accessing private field of another class via reflection.
     * This breaks with JPMS encapsulation in JDK 11+.
     */
    public static Object getPrivateFieldValue(Object obj, String fieldName) 
            throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);  // ⚠️ Breaks in JDK 11+ without --add-opens
        return field.get(obj);
    }
    
    /**
     * Setting private field via reflection.
     * Problematic for JPMS modules.
     */
    public static void setPrivateFieldValue(Object obj, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);  // ⚠️ Fails on JDK 11+ without --add-opens
        field.set(obj, value);
    }
    
    /**
     * Invoking private method via reflection.
     * This pattern is deeply anti-JPMS and should be avoided.
     */
    public static Object invokePrivateMethod(Object obj, String methodName, 
                                             Class<?>[] paramTypes, Object[] args)
            throws Exception {
        Method method = obj.getClass().getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);  // ⚠️ Breaks with JPMS
        return method.invoke(obj, args);
    }
    
    /**
     * Accessing classes by name dynamically.
     * Internal JDK classes are not accessible from JDK 9+.
     */
    public static Class<?> loadClassDynamically(String className) 
            throws ClassNotFoundException {
        // In JDK 8: ClassLoader.getSystemClassLoader().loadClass(className) works
        // In JDK 11: fails for internal.* and sun.* classes without --add-opens
        return Class.forName(className);
    }
    
    /**
     * Accessing internal JDK classes (BAD PATTERN).
     * Do NOT do this in JDK 9+ - will fail.
     */
    @SuppressWarnings("unused")
    public static void accessInternalJDKClass() {
        try {
            // This pattern MUST be eliminated for JDK 11 migration
            // Example: sun.reflect.Reflection, sun.misc.Unsafe, etc.
            // These will not be accessible without --add-opens flags
            Class<?> reflectionClass = Class.forName("sun.reflect.Reflection");
            Method method = reflectionClass.getMethod("registerMethodsToFilter", 
                                                       Class.class, String[].class);
            // ⚠️ These APIs are GONE in JDK 17+
        } catch (Exception e) {
            System.err.println("Internal API access failed on JDK 11+: " + e.getMessage());
        }
    }
    
    /**
     * Modern alternative for JDK 11+: Use MethodHandles.
     * Preferred for performance and JPMS compatibility.
     */
    public static java.lang.invoke.MethodHandle getMethodHandle(Class<?> clazz, 
                                                                  String methodName,
                                                                  java.lang.invoke.MethodType type) 
            throws NoSuchMethodException, IllegalAccessException {
        java.lang.invoke.MethodHandles.Lookup lookup = 
            java.lang.invoke.MethodHandles.lookup();
        return lookup.findVirtual(clazz, methodName, type);
    }
}
