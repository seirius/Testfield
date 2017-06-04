package templates.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.ServletContext;
import service.FormService;

/**
 *
 * @author Andriy
 */
public class FormValidator {
    
    private String formFileName;
    private JsonNode inputs;
    private ObjectNode form;
    
    public FormValidator(ServletContext servletContext, String formFileName, 
            JsonNode inputs) throws IOException {
        this.formFileName = formFileName;
        this.inputs = inputs;
        form = FormService.loadForm(servletContext, formFileName, ObjectNode.class);
    }
    
    public void validate() throws FormValidationException {
        JsonNode validation = form.get("validation");
        Iterator<String> validationNames = validation.fieldNames();
        while (validationNames.hasNext()) {
            String name = validationNames.next();
            JsonNode input = getInput(name);
            JsonNode valid = validation.get(name);
            
            
            Iterator<String> validNames = valid.fieldNames();
            while(validNames.hasNext()) {
                String validName = validNames.next();
                JsonNode validValue = valid.get(validName);
                validateByType(validValue, validName, input);
            }
        }
        
    }
    
    private void validateByType(JsonNode validValue, String validName, 
            JsonNode input) throws FormValidationException {
        validateRequired(validName, validValue, input);
        validateMinLength(validName, validValue, input);
        validateMaxLength(validName, validValue, input);
    }
    
    private void validateRequired(String validName, JsonNode validValue, 
            JsonNode input) throws FormValidationException {
        if (validName.equals("required")) {
            String label = input.get("label").asText();
            String value = input.get("value").asText();
            Boolean trueValue = validValue.asBoolean();
            if ( (value == null || value.length() == 0) && trueValue == true) {
                throw new FormValidationException(label, ValidationCode.REQUIRED);
            }
        }
    }
    
    private void validateMinLength(String validName, JsonNode validValue, 
            JsonNode input) throws FormValidationException {
        if (validName.equals("minLength")) {
            String label = input.get("label").asText();
            String value = input.get("value").asText();
            Integer trueValue = validValue.asInt();
            if (value != null && value.length() < trueValue) {
                throw new FormValidationException(label, ValidationCode.MIN_LENGTH);
            }
        }
    }
    
    private void validateMaxLength(String validName, JsonNode validValue, 
            JsonNode input) throws FormValidationException {
        if (validName.equals("maxLength")) {
            String label = input.get("label").asText();
            String value = input.get("value").asText();
            Integer trueValue = validValue.asInt();
            if (value != null && value.length() > trueValue) {
                throw new FormValidationException(label, ValidationCode.MAX_LENGTH);
            }
        }
    }
    
    public JsonNode getInput(String name) {
        for (JsonNode input: inputs) {
            if (input.get("name").asText().equals(name)) {
                return input;
            }
        }
        
        return null;
    }
    
}
