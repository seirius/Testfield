package com.testfield.templates.submitter;

/**
 *
 * @author Andriy
 */
public class SubmitterException extends Exception {
    
    public SubmitterException(String msg) {
        super(msg);
    }
    
    public SubmitterException(Exception e) {
        super(e);
    }
    
    public SubmitterException(String msg, Exception e) {
        super(msg, e);
    }
    
}
