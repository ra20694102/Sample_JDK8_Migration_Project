package com.example;

/**
 * Component demonstrating inefficient string concatenation patterns.
 * 
 * ⚠️ MIGRATION NOTE: Java 8 string concatenation uses StringBuilder behind the scenes,
 * but the pattern is inefficient and lacks clarity. Java 11+ recommends:
 * - Text blocks (Java 13+)
 * - String::formatted method (Java 15+)
 * - String.join() for collections
 * - StringBuilder explicitly for complex cases
 * 
 * ACTION FOR JDK 11:
 * - Replace repeated + with StringBuilder
 * - Use String.format() or String::formatted for templating
 * - Consider text blocks for multi-line strings (Java 13+)
 */
public class StringConcatenationComponent {
    
    private String name;
    private String email;
    private int age;
    
    public StringConcatenationComponent(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
    
    /**
     * Inefficient string concatenation in loop.
     * This creates a new String object on each iteration.
     * 
     * JDK 11+: Use StringBuilder or String.join()
     */
    public String buildUserListOld(java.util.List<String> users) {
        String result = "";
        for (String user : users) {
            result = result + user + ", ";  // ⚠️ Creates new String each iteration
        }
        return result;
    }
    
    /**
     * Multiple concatenations in single expression.
     * While Java 8 optimizes this, it's unclear and becomes worse with variables.
     */
    @Deprecated
    public String getUserSummaryOld() {
        return "User: " + name + " | Email: " + email + " | Age: " + age;
    }
    
    /**
     * Many concatenations that should use String.format().
     * In JDK 11, replace with formatted() method.
     */
    @Deprecated
    public String getFullDetailsOld() {
        String line1 = "Name: " + name;
        String line2 = "Email: " + email;
        String line3 = "Age: " + age;
        return line1 + "\n" + line2 + "\n" + line3;
    }
    
    /**
     * JDK 11+ alternative: Use StringBuilder explicitly.
     */
    public String buildUserListNew(java.util.List<String> users) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < users.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(users.get(i));
        }
        return sb.toString();
    }
    
    /**
     * JDK 11+ alternative: Use String.format().
     * JDK 15+: Use formatted() method instead.
     */
    public String getUserSummaryNew() {
        return String.format("User: %s | Email: %s | Age: %d", name, email, age);
    }
    
    /**
     * JDK 15+ alternative: String.formatted() method.
     * For JDK 11: Use String.format() above.
     */
    public String getFullDetailsNew() {
        String template = "Name: %s\nEmail: %s\nAge: %d";
        // In JDK 15+: template.formatted(name, email, age);
        // For JDK 11: use String.format()
        return String.format(template, name, email, age);
    }
    
    /**
     * JDK 15+ Text blocks - replaces multi-line string concatenation.
     * NOTE: Text blocks are available in Java 13+
     */
    public String getFormattedOutput() {
        // Java 15+ syntax - enable with --enable-preview in JDK 13-14
        // String output = """
        //     User Profile
        //     Name: %s
        //     Email: %s
        //     Age: %d
        //     """.formatted(name, email, age);
        // For JDK 11: use StringBuilder or String.format()
        return "User Profile\nName: " + name + "\nEmail: " + email + "\nAge: " + age;
    }
}
