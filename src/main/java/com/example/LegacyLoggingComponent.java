package com.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Component demonstrating use of commons-logging (via Spring transitive dependency).
 * 
 * ⚠️ MIGRATION NOTE: commons-logging is a legacy logging facade replaced by SLF4J.
 * Spring 5.3+ recommends SLF4J for better performance and JPMS compatibility.
 * 
 * ACTION FOR JDK 11:
 * - Exclude commons-logging from Spring dependencies
 * - Add SLF4J and a logging implementation (Logback or Log4j2)
 * - Replace Log/LogFactory with org.slf4j equivalents
 * - Update log statements to use modern logging patterns
 * - Consider adding structured logging (JSON logs) for JDK 11+ environments
 */
public class LegacyLoggingComponent {
    
    // Legacy commons-logging - should be replaced with SLF4J
    private static final Log log = LogFactory.getLog(LegacyLoggingComponent.class);
    
    private String componentId;
    
    public LegacyLoggingComponent(String id) {
        this.componentId = id;
        log.info("LegacyLoggingComponent initialized with ID: " + componentId);
    }
    
    /**
     * Process data with legacy logging patterns.
     */
    public void processData(String data) {
        try {
            log.debug("Processing data: " + data);
            
            if (data == null || data.isEmpty()) {
                log.warn("Data is null or empty");
                return;
            }
            
            // Simulate processing
            int result = data.length() * 2;
            log.info("Processed successfully with result: " + result);
            
        } catch (Exception e) {
            log.error("Error during processing: " + e.getMessage(), e);
        }
    }
    
    /**
     * Inefficient error handling with logging.
     * Multiple try-catch blocks with commons-logging calls.
     */
    public void handleMultipleErrors() {
        try {
            try {
                int[] arr = new int[5];
                int value = arr[10];  // ArrayIndexOutOfBoundsException
            } catch (ArrayIndexOutOfBoundsException e) {
                log.error("Array index error", e);
                throw new RuntimeException("Wrapping error", e);
            }
        } catch (RuntimeException e) {
            log.fatal("Fatal error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Modern SLF4J alternative for JDK 11+.
     * Replace Log/LogFactory with org.slf4j equivalents.
     * 
     * NOTE: Actual implementation shown below uses commented SLF4J
     * because SLF4J is not yet in dependencies for JDK 8 version.
     * 
     * For JDK 11+, add these dependencies:
     * - org.slf4j:slf4j-api:1.7.36
     * - ch.qos.logback:logback-classic:1.2.13
     * - org.slf4j:jcl-over-slf4j:1.7.36 (to bridge commons-logging)
     */
    public static class ModernLoggingComponent {
        // private static final org.slf4j.Logger logger = 
        //     org.slf4j.LoggerFactory.getLogger(ModernLoggingComponent.class);
        
        // For now, use standard System.out in JDK 8
        private static final java.io.PrintStream logger = System.out;
        
        private String componentId;
        
        public ModernLoggingComponent(String id) {
            this.componentId = id;
            logger.println("ModernLoggingComponent initialized with ID: " + componentId);
        }
        
        /**
         * After migration to JDK 11+, SLF4J supports parameterized messages.
         * More efficient than string concatenation.
         * 
         * JDK 11+ code:
         * public void processData(String data) {
         *     logger.debug("Processing data: {}", data);
         *     if (data == null || data.isEmpty()) {
         *         logger.warn("Data is null or empty");
         *         return;
         *     }
         *     int result = data.length() * 2;
         *     logger.info("Processed successfully with result: {}", result);
         * }
         */
        public void processData(String data) {
            logger.println("Processing data: " + data);
            
            if (data == null || data.isEmpty()) {
                logger.println("Data is null or empty");
                return;
            }
            
            int result = data.length() * 2;
            logger.println("Processed successfully with result: " + result);
        }
        
        /**
         * SLF4J with structured logging (optional) for JDK 11+.
         * Better for centralized logging in modern environments.
         * 
         * JDK 11+ implements:
         * public void logStructured(String action, Object details) {
         *     logger.info("action={} details={}", action, details);
         * }
         */
        public void logStructured(String action, Object details) {
            logger.println("Action: " + action + " Details: " + details);
        }
    }
}
