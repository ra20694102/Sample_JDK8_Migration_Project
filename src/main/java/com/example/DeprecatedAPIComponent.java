package com.example;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Component demonstrating deprecated APIs that require changes for JDK 11+.
 * 
 * ⚠️ MIGRATION NOTE: Many APIs deprecated in Java 8 are removed in Java 11+.
 * Using deprecated APIs will generate compiler warnings and may cause runtime failures.
 * 
 * ACTION FOR JDK 11:
 * - Replace deprecated constructors with factory methods
 * - Use modern collection utilities instead of raw types
 * - Update URL/URI handling to use java.net.URI directly
 * - Replace Vector/Hashtable with modern Collections
 * - Use StandardCharsets instead of hardcoded charset strings
 */
public class DeprecatedAPIComponent {
    
    /**
     * Using deprecated String constructor with charset.
     * @deprecated As of Java 6, use {@link String#String(byte[], Charset)} instead.
     */
    @Deprecated
    @SuppressWarnings("deprecated")
    public String decodeStringOld(byte[] bytes) {
        try {
            // This constructor is deprecated since Java 6
            // Removed implications in Java 11+
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Modern alternative for JDK 11+.
     */
    public String decodeStringNew(byte[] bytes) {
        return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
    }
    
    /**
     * Using Vector instead of ArrayList.
     * Vector is synchronized but deprecated for multi-threaded use.
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public Vector<String> getVectorOld() {
        Vector<String> vec = new Vector();  // Raw type - deprecated pattern
        vec.add("item1");
        vec.add("item2");
        return vec;
    }
    
    /**
     * Modern alternative: ArrayList with explicit synchronization if needed.
     */
    public List<String> getListNew() {
        List<String> list = new ArrayList<>();
        if (Thread.activeCount() > 1) {
            // Use Collections.synchronizedList() only if truly needed
            return Collections.synchronizedList(list);
        }
        return list;
    }
    
    /**
     * Using Hashtable instead of HashMap.
     * Hashtable is deprecated for single-threaded use.
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public Hashtable<String, String> getHashtableOld() {
        Hashtable<String, String> table = new Hashtable();  // Raw type
        table.put("key1", "value1");
        return table;
    }
    
    /**
     * Modern alternative: HashMap or ConcurrentHashMap.
     */
    public Map<String, String> getMapNew() {
        Map<String, String> map = new HashMap<>();
        // For concurrent access, use ConcurrentHashMap
        // Map<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        return map;
    }
    
    /**
     * Using raw types (pre-generics pattern).
     * Compiles but generates warnings.
     */
    @Deprecated
    @SuppressWarnings("rawtypes")
    public List getRawTypeListOld() {
        List list = new ArrayList();  // Raw type - deprecated
        list.add("string");
        list.add(123);  // Can add any type - type-unsafe
        return list;
    }
    
    /**
     * Model alternative: Use generics.
     */
    public List<String> getTypedListNew() {
        return new ArrayList<>(Arrays.asList("item1", "item2"));
    }
    
    /**
     * Using Date constructors deprecated since Java 1.1.
     * Calendar and java.time are preferred.
     */
    @Deprecated
    @SuppressWarnings("deprecated")
    public Date getDateOld(int year, int month, int day) {
        // These constructors are deprecated - use Calendar instead
        return new Date(year - 1900, month - 1, day);
    }
    
    /**
     * Modern alternative: Use java.time.LocalDate (Java 8+).
     */
    public java.time.LocalDate getDateNew(int year, int month, int day) {
        return java.time.LocalDate.of(year, month, day);
    }
    
    /**
     * Using Thread.stop() - removed in Java 11.
     * This method is dangerous and long deprecated.
     */
    @Deprecated
    @SuppressWarnings("deprecated")
    public void stopThreadOld(Thread thread) {
        // NEVER use Thread.stop() - it's removed in modern JDKs
        // thread.stop();  // Will not compile on JDK 11+
        
        // Alternative: Use interruption pattern
        thread.interrupt();
    }
    
    /**
     * Deprecated StringBuilder constructor.
     */
    public String buildStringOld() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append("item ");
            sb.append(i);
            sb.append(" ");
        }
        return sb.toString();
    }
    
    /**
     * Better pattern for JDK 11+: String.join() or String.format().
     */
    public String buildStringNew() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add("item " + i);
        }
        return String.join(" ", items);
    }
    
    /**
     * Demonstrates migration path for deprecated API usage.
     */
    public void demonstrateMigrationPath() {
        // OLD: decodeStringOld(new byte[]{65, 66, 67});
        // NEW: 
        decodeStringNew(new byte[]{65, 66, 67});
        
        // OLD: Vector vec = getVectorOld();
        // NEW:
        List<String> list = getListNew();
        
        // OLD: Hashtable table = getHashtableOld();
        // NEW:
        Map<String, String> map = getMapNew();
    }
}
