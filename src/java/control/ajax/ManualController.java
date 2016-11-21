/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.ajax;

import javax.servlet.http.HttpSession;
import model.bean.Manual;
import model.bean.ManualBlock;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ManualService;
import util.AjaxResponse;
import util.Security;
import util.ServiceReturn;
import util.exceptions.ServiceException;

/**
 *
 * @author Andriy
 */
@Controller
public class ManualController extends MyController {
    
    @RequestMapping(value = "/manual/createManual", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse createManual(HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().createManual(userNick);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/loadManual", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse loadManual(HttpSession session, @RequestBody Manual manual) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().loadManual(manual.getId());
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/loadManuals", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse loadManuals(HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().loadManuals(userNick);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/setTitle", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse setTitle(HttpSession session, @RequestBody Manual manual) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().setManualTitle(userNick, manual.getId(), manual.getTitle());
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/saveManualBlock", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse saveManualBlock(HttpSession session, @RequestBody ManualBlock manualBlock) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().saveManualBlock(userNick, manualBlock);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
}
