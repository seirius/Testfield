package control.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.AjaxResponse;
import util.FileUtil;
import util.UrlUtil;

/**
 *
 * @author Andriy
 */
@Controller
public class FormController {
    
    @Autowired
    private ServletContext servletContext;
    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public @ResponseBody AjaxResponse requestForm(@RequestParam String formName) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String realPath = servletContext
                    .getRealPath(UrlUtil.getUrl(new String[] {
                "WEB-INF", "form_templates", formName + ".json"
            }));
            String jsonFile = FileUtil.readFile(
                    realPath, 
                    StandardCharsets.UTF_8
            );
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode form = mapper.readValue(jsonFile, ObjectNode.class);
            ajaxResponse.add("form", form);
        } catch (Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/sendForm", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse sendForm(@RequestBody ObjectNode form) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            System.out.println(form);
            ajaxResponse.add("form", form);
        } catch (Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
}
