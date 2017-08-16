package com.testfield.templates.forms.inputs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.testfield.templates.validation.FormValidationException;

/**
 *
 * @author Andriy
 */
public class InputCheckbox extends Input {
    private boolean value;

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
    
    @JsonIgnore
    @Override
    public void validate() throws FormValidationException {
        required();
        server();
    }
}
