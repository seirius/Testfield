/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.exceptions;

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
//        System.err.println(e.getMessage());
    }
    
}
