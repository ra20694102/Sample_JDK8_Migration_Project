package com.example;

import java.io.*;

/**
 * Component demonstrating legacy Java serialization patterns.
 * 
 * ⚠️ MIGRATION NOTE: ObjectInputStream/ObjectOutputStream require special care in Java 9+.
 * With stronger module boundaries, serialization can break unexpectedly.
 * 
 * ACTION FOR JDK 11:
 * - Consider JSON serialization (Jackson, Gson) instead
 * - If staying with Java serialization, ensure serialVersionUID is explicit
 * - Use FilterInputStream for deserialization security
 * - Test thoroughly as JPMS affects internal class loading
 */
public class LegacySerializableComponent implements Serializable {
    
    // Missing serialVersionUID - will cause issues in JDK 11 with module system
    private static final long serialVersionUID = 1L;
    
    private String data;
    private int count;
    private transient byte[] buffer;
    
    public LegacySerializableComponent(String data, int count) {
        this.data = data;
        this.count = count;
        this.buffer = new byte[1024];
    }
    
    /**
     * Manual serialization using deprecated readObject/writeObject.
     * These should be replaced with modern serialization frameworks.
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        // Custom serialization logic - error-prone pattern
        oos.writeInt(buffer.length);
        oos.write(buffer);
    }
    
    /**
     * Manual deserialization - security risk and incompatible with JPMS.
     */
    private void readObject(ObjectInputStream ois) 
            throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // Custom deserialization - can fail with classloader issues in JDK 11+
        int len = ois.readInt();
        buffer = new byte[len];
        ois.readFully(buffer);
    }
    
    /**
     * Clone using broken serialization pattern.
     * This should use copy constructors or builders instead.
     */
    public LegacySerializableComponent deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (LegacySerializableComponent) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("Serialization clone failed", e);
        }
    }
    
    public String getData() {
        return data;
    }
    
    public int getCount() {
        return count;
    }
}
