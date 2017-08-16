package com.testfield.templates.validation.server_validator;

/**
 *
 * @author Andriy
 */
public class SVException extends Exception {
    
    public SVException(String msg) {
        super(msg);
    }
    
    public SVException(Exception e) {
        super(e);
    }
    
    public SVException(String msg, Exception e) {
        super(msg, e);
    }
    
}
