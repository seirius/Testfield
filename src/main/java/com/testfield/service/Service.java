package com.testfield.service;

import com.testfield.util.ServiceManager;
import javax.servlet.http.HttpServletRequest;
import com.testfield.templates.validation.FormValidationException;
import com.testfield.templates.validation.server_validator.SVException;
import com.testfield.util.ErrorMsgs;
import com.testfield.util.exceptions.BeanException;
import com.testfield.util.exceptions.ServiceException;

/**
 * @author Andriy Yednarovych
 */
public class Service {
    
    public static boolean DEBUG = true;
    
    protected final ServiceManager MANAGER;
    protected HttpServletRequest request;
    
    public Service() {
        this.MANAGER = new ServiceManager();
    }
    
    protected Exception treatException(Exception e) {
        return treatException(ErrorMsgs.DEFAULT_MSG, e);
    }

    protected Exception treatException(String msg, Exception e) {
        Exception exception;
        
        if (DEBUG) {
            e.printStackTrace();
        }
        if (e.getCause() instanceof BeanException ||
                e.getCause() instanceof ServiceException || 
                e.getCause() instanceof FormValidationException ||
                e.getCause() instanceof SVException) {
            exception = e;
        } else {
            System.err.println("Exception: " + e.getMessage());
            exception = new ServiceException(msg, e);
        }
        
        return exception;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    

}
