package control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.ManualService;
import util.ServiceReturn;

@Controller
public class ManualsController {
    
    @RequestMapping(value = "/manualsDownload", method = RequestMethod.GET)
    public String requestManualsDownload(@RequestParam int idManual, ModelMap model) {
        try {
            ManualService manualService = new ManualService();
            ServiceReturn serviceReturn = manualService.loadManual(idManual);
            model.addAttribute("manual", serviceReturn.getItem("manual"));
        } catch (Exception e) {
            System.err.println("-- ERROR: " + e.getMessage());
        }
        return "/WEB-INF/jsp/view/manuals/manualHtml.jsp";
    }
    
    @RequestMapping(value = "/manuals", method = RequestMethod.GET)
    public String requestManuals(ModelMap model) {
        return "/static/manuals.html";
    }
    
}
