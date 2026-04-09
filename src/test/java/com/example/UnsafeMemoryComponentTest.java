package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test cases for UnsafeMemoryComponent.
 * Tests the internal API usage patterns that will need migration on JDK 11+.
 */
public class UnsafeMemoryComponentTest {
    
    @Test
    public void testUpdateValueAndGetValue() {
        UnsafeMemoryComponent component = new UnsafeMemoryComponent();
        
        // Initial value should be 0
        assertEquals(0L, component.getValue());
        
        // Update value using CAS operation
        component.updateValue(42L);
        
        // Value should be updated
        assertEquals(42L, component.getValue());
    }
    
    @Test
    public void testMultipleValueUpdates() {
        UnsafeMemoryComponent component = new UnsafeMemoryComponent();
        
        // Test multiple updates
        component.updateValue(100L);
        assertEquals(100L, component.getValue());
        
        component.updateValue(200L);
        assertEquals(200L, component.getValue());
        
        component.updateValue(300L);
        assertEquals(300L, component.getValue());
    }
    
    @Test
    public void testAllocateOffHeapMemory() {
        UnsafeMemoryComponent component = new UnsafeMemoryComponent();
        
        // This tests the off-heap memory allocation pattern
        // Note: This is a terrible pattern and will need fixing for JDK 11+
        try {
            component.allocateOffHeapMemory(1024);
            // If we get here, allocation succeeded
            assertTrue(true);
        } catch (Exception e) {
            fail("Off-heap memory allocation should not throw: " + e.getMessage());
        }
    }
    
    @Test
    public void testOffHeapMemoryWithVariousSizes() {
        UnsafeMemoryComponent component = new UnsafeMemoryComponent();
        
        // Test various memory sizes
        long[] sizes = {256, 512, 1024, 4096, 65536};
        for (long size : sizes) {
            try {
                component.allocateOffHeapMemory(size);
                assertTrue(true);
            } catch (Exception e) {
                fail("Failed to allocate " + size + " bytes: " + e.getMessage());
            }
        }
    }
}
