package com.example;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;

/**
 * Test cases for LegacySerializableComponent.
 * Tests the deprecated serialization patterns that need migration on JDK 11+.
 */
public class LegacySerializableComponentTest {
    
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        LegacySerializableComponent original = new LegacySerializableComponent("TestData", 42);
        
        // Serialize object
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(original);
        oos.close();
        
        // Deserialize object
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        LegacySerializableComponent deserialized = 
            (LegacySerializableComponent) ois.readObject();
        ois.close();
        
        // Verify data
        assertEquals("TestData", deserialized.getData());
        assertEquals(42, deserialized.getCount());
    }
    
    @Test
    public void testDeepClone() {
        LegacySerializableComponent original = new LegacySerializableComponent("OriginalData", 100);
        
        // Create deep clone using serialization
        LegacySerializableComponent cloned = original.deepClone();
        
        // Verify clone has same data
        assertEquals(original.getData(), cloned.getData());
        assertEquals(original.getCount(), cloned.getCount());
        
        // Verify they are different objects
        assertNotSame(original, cloned);
    }
    
    @Test
    public void testMultipleSerializationCycles() throws IOException, ClassNotFoundException {
        LegacySerializableComponent original = new LegacySerializableComponent("CycleData", 5);
        
        // Cycle 1
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        ObjectOutputStream oos1 = new ObjectOutputStream(baos1);
        oos1.writeObject(original);
        oos1.close();
        
        ByteArrayInputStream bais1 = new ByteArrayInputStream(baos1.toByteArray());
        ObjectInputStream ois1 = new ObjectInputStream(bais1);
        LegacySerializableComponent cycle1 = (LegacySerializableComponent) ois1.readObject();
        ois1.close();
        
        // Cycle 2
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        ObjectOutputStream oos2 = new ObjectOutputStream(baos2);
        oos2.writeObject(cycle1);
        oos2.close();
        
        ByteArrayInputStream bais2 = new ByteArrayInputStream(baos2.toByteArray());
        ObjectInputStream ois2 = new ObjectInputStream(bais2);
        LegacySerializableComponent cycle2 = (LegacySerializableComponent) ois2.readObject();
        ois2.close();
        
        // Verify data survives multiple cycles
        assertEquals("CycleData", cycle2.getData());
        assertEquals(5, cycle2.getCount());
    }
    
    @Test
    public void testConstructorInitialization() {
        String testData = "TestConstructor";
        int testCount = 25;
        
        LegacySerializableComponent component = 
            new LegacySerializableComponent(testData, testCount);
        
        assertEquals(testData, component.getData());
        assertEquals(testCount, component.getCount());
    }
    
    @Test
    public void testSerializationWithDifferentDataTypes() throws IOException, ClassNotFoundException {
        // Test with empty string
        LegacySerializableComponent empty = new LegacySerializableComponent("", 0);
        LegacySerializableComponent emptyCloned = empty.deepClone();
        assertEquals("", emptyCloned.getData());
        assertEquals(0, emptyCloned.getCount());
        
        // Test with large count
        LegacySerializableComponent large = 
            new LegacySerializableComponent("LargeCount", Integer.MAX_VALUE);
        LegacySerializableComponent largeCloned = large.deepClone();
        assertEquals("LargeCount", largeCloned.getData());
        assertEquals(Integer.MAX_VALUE, largeCloned.getCount());
    }
}
