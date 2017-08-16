package com.testfield.util;

import com.testfield.util.exceptions.DAOException;


/**
 *
 * @author Andriy
 */
public class DAOValidator {
    
    public static void errorOnInsert(String name, Exception e) throws DAOException {
        ErrorMsgs.sysLogThis(e);
        throw new DAOException("Error while creating new " + name + ".", e);
    }
    
    public static void errorOnUpdate(String name, Exception e) throws DAOException {
        ErrorMsgs.sysLogThis(e);
        throw new DAOException("Error while updating " + name + ".", e);
    }
    
    public static void errorOnSelect(String name, Exception e) throws DAOException {
        ErrorMsgs.sysLogThis(e);
        throw new DAOException("Error while getting " + name + ".", e);
    }
    
    public static void errorOnDelete(String name, Exception e) throws DAOException {
        ErrorMsgs.sysLogThis(e);
        throw new DAOException("Error while deleting " + name + ".", e);
    }
    
    public static void errorOnCheck(String condition, Exception e) throws DAOException {
        ErrorMsgs.sysLogThis(e);
        throw new DAOException("Error " + condition + ".", e);
    }
    
    
    
}
