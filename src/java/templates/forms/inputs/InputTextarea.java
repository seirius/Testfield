package templates.forms.inputs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import templates.validation.FormValidationException;

/**
 *
 * @author Andriy
 */
public class InputTextarea extends Input {
    private int rows;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
    
    @JsonIgnore
    @Override
    public void validate() throws FormValidationException {
        required();
        minLength();
        maxLength();
        inputVerify();
        server();
    }
    
    
}
