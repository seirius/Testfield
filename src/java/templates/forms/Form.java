package templates.forms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import service.FormService;
import templates.forms.inputs.Input;
import templates.validation.FormValidationException;
import templates.validation.ValidationCode;
import util.ServiceManager;
import util.exceptions.ServiceException;

/**
 *
 * @author Andriy
 */
public class Form {
    @JsonIgnore
    private FormData formLoadData;
    private FormData formSendData;
    @JsonIgnore
    private ServiceManager manager;
    @JsonIgnore
    private HttpServletRequest request;
    
    private String name;
    private String service;
    private List<Input> inputs;
    private JsonNode buttons;

    @JsonIgnore
    public void copyValuesTo(Form form) throws FormValidationException {
        for (Input input: inputs) {
            Input foreignInput = form.getInputByName(input.getName());
            if (foreignInput == null) {
                throw new FormValidationException("Not valid form (HUH).", ValidationCode.ERR);
            }
            if (!foreignInput.getClass().equals(input.getClass())) {
                throw new FormValidationException("Not valid form (HUE)", ValidationCode.ERR);
            }
            foreignInput.cloneValue(input);
        }
    }
    
    @JsonIgnore 
    public void validate() throws FormValidationException, IOException {
        Form originalForm = FormService.loadForm(request.getServletContext(), name);
        copyValuesTo(originalForm);
        for (Input origInput: originalForm.getInputs()) {
            origInput.setForm(originalForm);
            origInput.validate();
        }
    }
    
    @JsonIgnore
    public Input getInputByName(String name) {
        for (Input input: inputs) {
            if (input.getName().equals(name)) {
                return input;
            }
        }
        
        return null;
    }
    
    @JsonIgnore
    public void initInputs(ServiceManager manager, 
            HttpServletRequest request) throws ServiceException {
        this.manager = manager;
        this.request = request;
        for (Input input: inputs) {
            input.setForm(this);
            input.launchController();
        }
    }
     
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @JsonIgnore
    public String getServiceClass() {
        return service.split("\\.")[0];
    }
    
    @JsonIgnore
    public String getServiceMethod() {
        return service.split("\\.")[1];
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    public JsonNode getButtons() {
        return buttons;
    }

    public void setButtons(JsonNode buttons) {
        this.buttons = buttons;
    }

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

    public FormData getFormLoadData() {
        return formLoadData;
    }

    public void setFormLoadData(FormData formLoadData) {
        this.formLoadData = formLoadData;
    }

    public FormData getFormSendData() {
        return formSendData;
    }

    public void setFormSendData(FormData formSendData) {
        this.formSendData = formSendData;
    }
    
    
    
}
