package com.testfield.util;

import javax.servlet.http.HttpSession;

/**
 * @author Andriy Yednarovych
 */
public class HttpSessionUtil {
    
    public boolean requestConnection(HttpSession session) {
        boolean ok = false;
        
        String user = (String) session.getAttribute("user");
        if (user != null) {
            ok = true;
        }
        
        return ok;
    }

}
