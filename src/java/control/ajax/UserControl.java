package control.ajax;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import model.bean.user.UserTestfield;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;
import util.AjaxResponse;
import util.ServiceReturn;
import util.exceptions.ServiceException;

/**
 * @author Andriy Yednarovych
 */
@Controller
public class UserControl extends MyController {
    
    @PostConstruct
    @Override
    public void init() {
        super.init();
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse loginRequest(@RequestBody UserTestfield user, HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        boolean loginOk = false;
        
        try {
            ServiceReturn result = new UserService().login(user);
            user = (UserTestfield) result.getItem("user");
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
    public @ResponseBody AjaxResponse createUser(@RequestParam String userNick, @RequestParam String password, @RequestParam String email) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        
        try {
            UserService userService = new UserService();
            userService.createUser(userNick, password, email);
            ajaxResponse.add("userCreated", true);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        
        return ajaxResponse;
    }
    
}
