package com.testfield.util.exceptions;

import com.testfield.util.ErrorMsgs;

/**
 * @author Andriy Yednarovych
 */
public class MyException extends Exception {
    
    public MyException() {}
 
    public MyException(String msg) {
        
        super(msg);
    }
    
    public MyException(String msg, Exception e) {
        super(msg, e);
        treatException(e);
    }
    
    public MyException(Exception e) {
        super(e);
        treatException(e);
    }
    
    private void treatException(Exception e) {
        ErrorMsgs.sysLogThis(e);
    }
    
}
