package com.testfield.control.ajax;

import com.testfield.service.FileService;
import com.testfield.util.AjaxResponse;
import com.testfield.util.Security;
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
@RequestMapping(value = "/file")
public class FileController {
    
    @RequestMapping(value = "getUsersImages", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getUsersImages(HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            FileService fileService = new FileService();
            ServiceReturn serviceReturn = fileService.getUsersImages(userNick);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
}
