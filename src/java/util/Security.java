/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.servlet.http.HttpSession;
import model.bean.manual.Manual;
import util.exceptions.ServiceException;

/**
 *
 * @author Andriy
 */
public class Security {
    
    public static boolean isSessionSecured(HttpSession session, String userNick) throws ServiceException {
        boolean isSecure = false;
        try {
            String sessionUser = Security.isSessionOpened(session);
            
            if (sessionUser.equals(userNick)) {
                throw new ServiceException(ErrorMsgs.NO_PERMISSION);
            }
            
            isSecure = true;
        } catch(ServiceException e) {
            throw e;
        } catch(Exception e) {
            isSecure = false;
            System.err.println(e.getMessage());
        }
        return isSecure;
    }
    
    public static String isSessionOpened(HttpSession session) throws ServiceException {
        String sessionUser = null;
        try {
            sessionUser = (String) session.getAttribute("user");
            if (sessionUser == null) {
                throw new ServiceException(ErrorMsgs.NO_SESSION);
            }
        } catch(ServiceException e) {
            throw e;
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        
        return sessionUser;
    } 
    
    /**
     * Got the permission to modife this Manual? 
     * 
     * @param manual
     * @param user
     * @return 
     */
    public static boolean permissionModManual(Manual manual, String user) {
        boolean gotPermission = false;
        
        try {
            String manualUser = manual.getUserNick();
            gotPermission = user.equals(manualUser);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        
        return gotPermission;
    }
    
}
