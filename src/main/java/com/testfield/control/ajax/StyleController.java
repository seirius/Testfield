package com.testfield.control.ajax;

import com.testfield.service.StyleService;
import com.testfield.util.AjaxResponse;
import com.testfield.util.ServiceReturn;
import com.testfield.util.exceptions.ServiceException;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
