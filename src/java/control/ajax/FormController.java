package control.ajax;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.lang.reflect.Method;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.Service;
import templates.FormService;
import templates.validation.FormValidator;
import util.AjaxResponse;
import util.ServiceNicks;
import util.ServiceReturn;

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
            ObjectNode form = FormService.loadJsonForm(servletContext, formName);
            ajaxResponse.add("form", form);
        } catch (Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/sendForm", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse sendForm(@RequestBody ObjectNode form, 
            HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            JsonNode formJson = form.get("form");
            String formName = formJson.get("name").asText();
            JsonNode inputs = formJson.get("inputs");
            FormValidator formValidator = new FormValidator(servletContext, formName, inputs);
            formValidator.validate();
            
            String[] serviceLink = formJson.get("service").asText().split("\\.");
            String service = serviceLink[0];
            String method = serviceLink[1];
            
            Class<?> clazz = Class.forName(ServiceNicks.toEnum(service).getClassName());
            Object serviceClass = clazz.newInstance();
            ((Service) serviceClass).setSession(session);
            
            Method classMethod = serviceClass.getClass().getMethod(method, ObjectNode.class);
            ServiceReturn serviceReturn = (ServiceReturn) classMethod.invoke(serviceClass, form);
            ajaxResponse.digest(serviceReturn);
        } catch (Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
}
