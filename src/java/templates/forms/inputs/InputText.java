package templates.forms.inputs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import templates.validation.FormValidationException;

/**
 *
 * @author Andriy
 */
public class InputText extends Input {
    protected String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    @JsonIgnore
    @Override
    public void validate() throws FormValidationException {
        required();
        inputVerify();
        server();
        switch(typeName) {
            case "text":
                minLength();
                maxLength();
                break;
            case "number":
                minNumberLength();
                maxNumberLength();
                break;
        }
    }

}
