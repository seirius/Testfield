package templates.validation.server_validator;

import javax.servlet.http.HttpServletRequest;
import util.ServiceManager;

/**
 *
 * @author Andriy
 */
public class ServerValidator {
    
    protected ServiceManager manager;
    protected HttpServletRequest request;
    
    public ServiceManager getManager() {
        return manager;
    }

    public void setManager(ServiceManager manager) {
        this.manager = manager;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
}
