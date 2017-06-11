package control.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexRequest(ModelMap model) {
        return "/static/index.html";
    }
    
    @RequestMapping(value = "/indexM", method = RequestMethod.GET)
    public String indexMRequest(ModelMap model) {
        return "/static/indexM.html";
    }
//    
//    @RequestMapping(value = "/part/login", method = RequestMethod.GET)
//    public String partLogin(ModelMap model) {
//        return "/static/htmlParts/login/loginPart.html";
//    }
//    
//    @RequestMapping(value = "/part/register", method = RequestMethod.GET)
//    public String partRegister(ModelMap model) {
//        return "/static/htmlParts/login/registerPart.html";
//    }
//    
    @RequestMapping(value = "/choseApp", method = RequestMethod.GET)
    public String partChoseAPp() {
        return "/static/choseApp.html";
    }
    
    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public String files() {
        return "/static/files.html";
    }
//    
//    @RequestMapping(value = "/choseApp", method = RequestMethod.GET)
//    public String choseAppRequest(ModelMap model) {
//        return "/static/chose-app.html";
//    }
//    
//    @RequestMapping(value = "/jspdf-test", method = RequestMethod.GET)
//    public String jspdf(ModelMap model) {
//        
//        return "jsPDF-test";
//    }
//    
//    @RequestMapping(value = "/default-imports", method = RequestMethod.GET)
//    public String importRequest(ModelMap model) {
//        return "/static/includes/default-imports.html";
//    }
//    
//    @RequestMapping(value = "/navbar-testfield", method = RequestMethod.GET)
//    public String importNavbarRequest(ModelMap model) {
//        return "/includes/imports/navbar-testfield";
//    }
    
//    @RequestMapping(value = "/testfield-manuals", method = RequestMethod.GET)
//    public String importManuals(ModelMap model) {
//        return "/includes/imports/testfield-manuals";
//    }
    
//    @RequestMapping(value = "/test-field", method = RequestMethod.GET)
//    public String testField(ModelMap model) {
//        return "test-field";
//    }
//    
//    @RequestMapping(value = "/angular-test", method = RequestMethod.GET)
//    public String angularTest(ModelMap model) {
//        return "angular-test";
//    }
//    
//    @RequestMapping(value = "/angular-test-2", method = RequestMethod.GET)
//    public String angularTest2(ModelMap model) {
//        return "angular-test-2";
//    }
//    
//    @RequestMapping(value = "/index2", method = RequestMethod.GET)
//    public String index2(ModelMap model) {
//        return "index2";
//    }
    
}
