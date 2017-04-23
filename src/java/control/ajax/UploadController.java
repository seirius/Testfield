package control.ajax;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import util.AjaxResponse;

/**
 *
 * @author Andriy
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {
    
    @RequestMapping(value = "/uploadManualFiles", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse uploadManualFiles(@RequestParam MultipartFile[] files,
            @RequestParam("manualId") int manualId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        for (MultipartFile file: files) {
            System.out.println(file);
        }
        return ajaxResponse;
    }
    
}
