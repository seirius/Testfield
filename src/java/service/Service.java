/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import javax.servlet.http.HttpServletRequest;
import templates.validation.FormValidationException;
import util.exceptions.BeanException;
import util.ErrorMsgs;
import util.exceptions.ServiceException;
import util.ServiceManager;

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
        
        if (e instanceof BeanException ||
                e instanceof ServiceException || 
                e instanceof FormValidationException) {
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
