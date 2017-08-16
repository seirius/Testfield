package com.testfield.control.ajax;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.testfield.service.FormService;
import com.testfield.templates.forms.inputs.Input;
import com.testfield.util.AjaxResponse;
import com.testfield.util.ServiceReturn;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Andriy
 */
@Controller
public class FormController {
    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public @ResponseBody AjaxResponse requestForm(@RequestParam String formName,
            HttpServletRequest request) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            FormService formService = new FormService();
            formService.setRequest(request);
            ServiceReturn serviceReturn = formService.loadForm(formName);
            ajaxResponse.digest(serviceReturn);
        } catch (Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/formPost", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse requestFormPost(@RequestBody ObjectNode form,
            HttpServletRequest request) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            FormService formService = new FormService();
            formService.setRequest(request);
            ServiceReturn serviceReturn = formService.loadForm(form);
            ajaxResponse.digest(serviceReturn);
        } catch (Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/sendForm", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse sendForm(@RequestBody ObjectNode form, 
            HttpServletRequest request) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            FormService formService = new FormService();
            formService.setRequest(request);
            ServiceReturn serviceReturn = formService.sendForm(form);
            ajaxResponse.digest(serviceReturn);
        } catch (Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/serverValidation", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse serverValidation(@RequestBody Input input,
            HttpServletRequest request) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            FormService formService = new FormService();
            formService.setRequest(request);
            ServiceReturn serviceReturn = formService.serverValidation(input);
            ajaxResponse.digest(serviceReturn);
        } catch (Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    } 
    
}
