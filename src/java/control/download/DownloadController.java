package control.download;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import model.bean.manual.Manual;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.ManualService;
import util.ServiceReturn;

/**
 *
 * @author Andriy
 */
@Controller
public class DownloadController {
    
    @RequestMapping(value = "/manual", method = RequestMethod.GET)
    public void requestManualsDownload(@RequestParam int id, ModelMap model, 
            HttpServletRequest request, HttpServletResponse response) {
        try {
            
            
            HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response) {
                private final StringWriter sw = new StringWriter();

                @Override
                public PrintWriter getWriter() throws IOException {
                    return new PrintWriter(sw);
                }

                @Override
                public String toString() {
                    return sw.toString();
                }
            };
            
            String url = String.format("/download/prepare-manual?id=%d", id);
            request.getRequestDispatcher(url).include(request, responseWrapper);
            String content = responseWrapper.toString();
            InputStream stream = 
                    new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
            String header = String.format("attachment; filename=\"%s.%s\"", 
                    "temporal_title", "html");
            response.setHeader("Content-Disposition", header);
            IOUtils.copy(stream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            System.err.println("-- ERROR: " + e.getMessage());
        }
    }
    
    @RequestMapping(value = "/prepare-manual", method = RequestMethod.GET)
    public String prepareManual(@RequestParam int id, ModelMap model, 
            HttpServletRequest request) {
        try {
            ManualService manualService = new ManualService();
            ServiceReturn serviceReturn = manualService.loadManual(id);
            Manual manual = (Manual) serviceReturn.getItem("manual");
            String manualTitle = manual.getTitle();
            model.addAttribute("manual", serviceReturn.getItem("manual"));
        } catch (Exception e) {
            System.err.println("-- ERROR: " + e.getMessage());
        }
        return "/WEB-INF/jsp/view/manuals/manualHtml.jsp";
    }
    
}
