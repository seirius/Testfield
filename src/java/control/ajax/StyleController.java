/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.ajax;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.StyleService;
import util.AjaxResponse;
import util.ServiceReturn;
import util.exceptions.ServiceException;

/**
 *
 * @author Andriy
 */
@Controller
public class StyleController extends MyController {
    
    @RequestMapping(value = "/style/fontFamilies", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getFontFamilies(HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            ServiceReturn serviceReturn = new StyleService().getAllFontFamilies();
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        
        return ajaxResponse;
    }
    
}
