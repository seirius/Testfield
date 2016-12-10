/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

/**
 * @author Andriy Yednarovych
 */
public class ErrorMsgs {
    
    public final static String NO_SESSION = "You must be logged in.";
    public final static String DEFAULT_MSG = "An error has ocurred, try again later.";
    public final static String ERR_PARSING = "Error parsing an Item Result.";
    public final static String NO_PERMISSION = "Not enough permissions.";
    public final static String ACC_DEN = "Access denied.";
    
    public static void sysLogThis(Exception e) {
        try {
            sysLogThis(e.getMessage());
        } catch(Exception ex) {
            System.err.println("This can't be happening: " + ex.getMessage());
        }
    }
    
    public static void sysLogThis(String msg) {
        System.err.println(msg);
    }
    
    public static void sysLogInfo(String msg) {
        sysLogThis(" -- INFO: " + msg);
    }
 
}
