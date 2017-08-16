package com.testfield.util.exceptions;

/**
 * @author Andriy Yednarovych
 */
public class ServiceException extends MyException {

    public ServiceException (String msg) {
        super(msg);
    }

    public ServiceException (String msg, Exception e) {
        super(msg, e);
    }
    
}
