package com.testfield.templates.forms.inputs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.testfield.templates.forms.inputs.subinputs.Option;
import java.util.List;
import com.testfield.templates.validation.FormValidationException;

/**
 *
 * @author Andriy
 */
public class InputRadio extends Input {
    private List<Option> radios;

    public List<Option> getRadios() {
        return radios;
    }

    public void setRadios(List<Option> radios) {
        this.radios = radios;
    }
    
    @JsonIgnore
    @Override
    public void validate() throws FormValidationException {
        required();
        server();
    }
}
