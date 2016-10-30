/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.exceptions;

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
