package com.testfield.util.exceptions;

/**
 * @author Andriy Yednarovych
 */
public class DAOException extends MyException {

    public DAOException (String msg) {
        super(msg);
    }
    
    public DAOException (String msg, Exception e) {
        super(msg, e);
    }
    
    public DAOException(Exception e) {
        super(e);
    }
    
}
