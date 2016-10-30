/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.ajax;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.AjaxResponse;

/**
 *
 * @author andse
 */
@Controller
public class AngularController extends MyController {
    
    @RequestMapping(value = "/angularArray", method = RequestMethod.GET)
    public @ResponseBody AjaxResponse logoutRequest() {
        ajaxResponse.add("Type", "213 Grove Street");
        
        return ajaxResponse;
    }
    
}
