package com.example;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

/**
 * Component demonstrating use of sun.misc.Unsafe (internal JDK API).
 * 
 * ⚠️ MIGRATION NOTE: sun.misc.Unsafe is encapsulated in Java 9+ with JPMS.
 * On JDK 11+, this will fail unless --add-opens java.base/sun.misc=ALL-UNNAMED is used.
 * 
 * ACTION FOR JDK 11:
 * - Replace with java.lang.VarHandle or AtomicReference for thread-safe access
 * - Use java.util.concurrent utilities instead of manual memory manipulation
 */
public class UnsafeMemoryComponent {
    
    private static final Unsafe unsafe;
    private static final long valueOffset;
    
    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
            valueOffset = unsafe.objectFieldOffset(
                UnsafeMemoryComponent.class.getDeclaredField("value")
            );
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    private long value = 0;
    
    /**
     * Update value using Unsafe CAS operation.
     * This is an antipattern for JDK 11+ migration.
     */
    public void updateValue(long newValue) {
        unsafe.compareAndSwapLong(this, valueOffset, value, newValue);
    }
    
    /**
     * Get value using Unsafe volatile read.
     * Should be replaced with volatile field or VarHandle.
     */
    public long getValue() {
        return unsafe.getLongVolatile(this, valueOffset);
    }
    
    /**
     * Allocate off-heap memory (deeply problematic pattern).
     * Definitely needs replacement in JDK 11+.
     */
    public void allocateOffHeapMemory(long sizeBytes) {
        long address = unsafe.allocateMemory(sizeBytes);
        unsafe.putLong(address, 12345L);
        // Memory leak: never deallocated
        // This is a terrible pattern and must be fixed during migration
    }
}
