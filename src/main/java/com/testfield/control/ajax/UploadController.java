package com.testfield.control.ajax;

import com.testfield.service.FileService;
import com.testfield.util.AjaxResponse;
import com.testfield.util.Security;
import com.testfield.util.ServiceReturn;
import com.testfield.util.enums.FilePath;
import com.testfield.util.exceptions.ServiceException;
import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Andriy
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {
    
    @Autowired
    private ServletContext servletContext;
    
    @RequestMapping(value = "/uploadManualFiles", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse uploadManualFiles(HttpSession session, 
            @RequestParam MultipartFile[] files, @RequestParam("manualId") int manualId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String relativePath = String.format("%s%s%s", File.separator, 
                    "WEB-INF", File.separator);
            String realPath = servletContext.getRealPath(relativePath);
            String userNick = Security.isSessionOpened(session);
            FileService fileService = new FileService();
            ServiceReturn serviceReturn = fileService.insertManualFiles(
                    userNick, manualId, realPath, FilePath.MANUAL_PATH, files);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
}
