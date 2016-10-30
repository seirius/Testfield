/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ajax;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import model.bean.UserTestfield;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ConnectionsService;
import service.UserService;
import util.AjaxResponse;
import util.ServiceReturn;
import util.exceptions.ServiceException;

/**
 * @author Andriy Yednarovych
 */
@Controller
public class UserControl extends MyController {

    @Autowired
    private ServletContext context;
    
//    private int maxConnections;
    
    @PostConstruct
    @Override
    public void init() {
        super.init();
        
//        maxConnections = 0;
//        try {
//            maxConnections = Integer.parseInt(context.getInitParameter("maxConnections"));
//        } catch(NumberFormatException e) {
//            System.err.println("Context.maxConnections is not an Integer.");
//        }
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse loginRequest(@RequestParam String userNick, @RequestParam String password, HttpSession session) {
        boolean loginOk = false;
        
        try {
            UserService userService = new UserService();

            ServiceReturn result = userService.login(userNick, password);
            UserTestfield user = (UserTestfield) result.getItem("user");
            if (user != null) {
                session.setAttribute("user", user.getUserNick());
                loginOk = true;
            }
            
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg();
        }
        
        ajaxResponse.add("loginOk", loginOk);
        
        return ajaxResponse;
    }
//    
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public @ResponseBody AjaxResponse loginRequest(@RequestParam String userNick, @RequestParam String password, HttpSession session) {
//        boolean loginOk = false;
//        
//        try {
//            UserService userService = new UserService();
//
//            HashMap<String, Object> result = userService.login(userNick, password, maxConnections);
//            UserTestfield user = (UserTestfield) result.get("user");
//            Connections connection = (Connections) result.get("connection");
//            if (user != null) {
//                session.setAttribute("user", user.getUserNick());
//                session.setAttribute("connectionToken", connection.getId().getToken());
//                loginOk = true;
//            }
//        } catch(ServiceException e) {
//            ajaxResponse.setError(e);
//        } catch(Exception e) {
//            ajaxResponse.setErrorMsg();
//        }
//        
//        ajaxResponse.add("loginOk", loginOk);
//        
//        return ajaxResponse;
//    }
    
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public @ResponseBody AjaxResponse loginRequest(@RequestBody UserTestfield user, HttpSession session) {
//        boolean loginOk = false;
//        ajaxResponse.add("user", user);
//        System.out.println(user);
////        try {
////            if (session.getAttribute("user") != null) {
////                ajaxResponse.setErrorMsg("You are already logged in.");
////            } else {
////                UserService userService = new UserService();
////
////                HashMap<String, Object> result = userService.login(userNick, password, maxConnections);
////                UserTestfield user = (UserTestfield) result.get("user");
////                Connections connection = (Connections) result.get("connection");
////                if (user != null) {
////                    session.setAttribute("user", user.getUserNick());
////                    session.setAttribute("connectionToken", connection.getId().getToken());
////                    loginOk = true;
////                }
////            }
////        } catch(ServiceException e) {
////            ajaxResponse.setError(e);
////        } catch(Exception e) {
////            ajaxResponse.setErrorMsg();
////        }
////        
////        ajaxResponse.add("loginOk", loginOk);
//        
//        return ajaxResponse;
//    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse logoutRequest(HttpSession session) {
        boolean logoutOk = false;
        
        try {
            String userNick = (String) session.getAttribute("user");
            String token = (String) session.getAttribute("connectionToken");
            if (userNick != null && token != null) {
                ConnectionsService connectionService = new ConnectionsService();
                connectionService.delete(userNick, token);
                session.invalidate();
                logoutOk = true;
            }
            
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg();
        }
        
        ajaxResponse.add("logoutOk", logoutOk);
        
        return ajaxResponse;
    }
    
}
