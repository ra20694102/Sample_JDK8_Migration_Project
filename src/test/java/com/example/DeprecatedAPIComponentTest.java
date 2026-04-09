package com.example;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Hashtable;
import java.nio.charset.StandardCharsets;

/**
 * Test cases for DeprecatedAPIComponent.
 * Tests deprecated API usage patterns that require modernization on JDK 11+.
 */
public class DeprecatedAPIComponentTest {
    
    @Test
    public void testDecodeStringOld() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        byte[] bytes = new byte[]{65, 66, 67, 68};  // "ABCD"
        String result = component.decodeStringOld(bytes);
        
        assertEquals("ABCD", result);
    }
    
    @Test
    public void testDecodeStringNew() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        byte[] bytes = new byte[]{65, 66, 67, 68};  // "ABCD"
        String result = component.decodeStringNew(bytes);
        
        assertEquals("ABCD", result);
    }
    
    @Test
    public void testDecodeStringWithUTF8Characters() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        String expectedText = "Hello";
        byte[] bytes = expectedText.getBytes(StandardCharsets.UTF_8);
        
        String resultOld = component.decodeStringOld(bytes);
        String resultNew = component.decodeStringNew(bytes);
        
        assertEquals(expectedText, resultOld);
        assertEquals(expectedText, resultNew);
    }
    
    @Test
    public void testGetVectorOld() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        Vector<String> vector = component.getVectorOld();
        
        assertNotNull(vector);
        assertEquals(2, vector.size());
        assertTrue(vector.contains("item1"));
        assertTrue(vector.contains("item2"));
    }
    
    @Test
    public void testGetListNew() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        List<String> list = component.getListNew();
        
        assertNotNull(list);
        assertTrue(list instanceof List);
    }
    
    @Test
    public void testGetHashtableOld() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        Hashtable<String, String> hashtable = component.getHashtableOld();
        
        assertNotNull(hashtable);
        assertEquals("value1", hashtable.get("key1"));
    }
    
    @Test
    public void testGetMapNew() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        Map<String, String> map = component.getMapNew();
        
        assertNotNull(map);
        assertEquals("value1", map.get("key1"));
    }
    
    @Test
    public void testGetRawTypeListOld() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        @SuppressWarnings("unchecked")
        List<Object> list = component.getRawTypeListOld();
        
        assertNotNull(list);
        assertEquals(2, list.size());
        assertTrue(list.contains("string"));
        assertTrue(list.contains(123));
    }
    
    @Test
    public void testGetTypedListNew() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        List<String> list = component.getTypedListNew();
        
        assertNotNull(list);
        assertEquals(2, list.size());
        assertTrue(list.contains("item1"));
        assertTrue(list.contains("item2"));
    }
    
    @Test
    public void testGetDateOld() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        java.util.Date date = component.getDateOld(2026, 4, 9);
        
        assertNotNull(date);
    }
    
    @Test
    public void testGetDateNew() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        java.time.LocalDate date = component.getDateNew(2026, 4, 9);
        
        assertNotNull(date);
        assertEquals(2026, date.getYear());
        assertEquals(4, date.getMonthValue());
        assertEquals(9, date.getDayOfMonth());
    }
    
    @Test
    public void testStopThreadOld() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        Thread testThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Expected
            }
        });
        testThread.start();
        
        // Should not throw
        component.stopThreadOld(testThread);
        
        assertTrue(testThread.isInterrupted() || !testThread.isAlive());
    }
    
    @Test
    public void testBuildStringOld() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        String result = component.buildStringOld();
        
        assertNotNull(result);
        assertTrue(result.contains("item"));
        assertTrue(result.length() > 0);
    }
    
    @Test
    public void testBuildStringNew() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        String result = component.buildStringNew();
        
        assertNotNull(result);
        assertTrue(result.contains("item"));
        assertTrue(result.length() > 0);
    }
    
    @Test
    public void testDemonstrateMigrationPath() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        // Should execute without throwing
        component.demonstrateMigrationPath();
        
        assertTrue(true);
    }
    
    @Test
    public void testDecodeStringOldWithEmptyArray() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        byte[] emptyBytes = new byte[]{};
        String result = component.decodeStringOld(emptyBytes);
        
        assertEquals("", result);
    }
    
    @Test
    public void testDecodeStringNewWithEmptyArray() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        byte[] emptyBytes = new byte[]{};
        String result = component.decodeStringNew(emptyBytes);
        
        assertEquals("", result);
    }
    
    @Test
    public void testGetDateNewWithDifferentDates() {
        DeprecatedAPIComponent component = new DeprecatedAPIComponent();
        
        java.time.LocalDate date1 = component.getDateNew(2020, 1, 1);
        java.time.LocalDate date2 = component.getDateNew(2025, 12, 31);
        java.time.LocalDate date3 = component.getDateNew(2026, 6, 15);
        
        assertEquals(2020, date1.getYear());
        assertEquals(2025, date2.getYear());
        assertEquals(2026, date3.getYear());
    }
}
