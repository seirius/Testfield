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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;
import util.AjaxResponse;
import util.ServiceReturn;
import util.dummys.UserDummy;
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
    public @ResponseBody AjaxResponse loginRequest(@RequestBody UserDummy userDummy, HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        boolean loginOk = false;
        
        try {
            ServiceReturn result = new UserService().login(userDummy.userNick, userDummy.password);
            UserTestfield user = (UserTestfield) result.getItem("user");
            if (user != null) {
                session.setAttribute("user", user.getUserNick());
                loginOk = true;
            }
            
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        
        ajaxResponse.add("loginOk", loginOk);
        
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse logoutRequest(HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        boolean logoutOk = false;
        
        try {
            session.invalidate();
            logoutOk = true;
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        
        ajaxResponse.add("logoutOk", logoutOk);
        
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse createUser(@RequestBody UserDummy userDummy) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        
        try {
            UserService userService = new UserService();
            ServiceReturn serviceReturn = userService.createUser(userDummy);
            UserTestfield user = (UserTestfield) serviceReturn.getItem("user");
            ajaxResponse.add("user", user);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        
        return ajaxResponse;
    }
    
}
