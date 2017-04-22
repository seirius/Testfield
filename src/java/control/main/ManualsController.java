package control.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManualsController {
    
    @RequestMapping(value = "/manuals", method = RequestMethod.GET)
    public String requestManuals(ModelMap model) {
        return "/static/manuals.html";
    }
    
}
