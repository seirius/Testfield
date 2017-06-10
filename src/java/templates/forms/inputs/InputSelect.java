package templates.forms.inputs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import templates.forms.inputs.subinputs.Option;
import templates.validation.FormValidationException;

/**
 *
 * @author Andriy
 */
public class InputSelect extends Input {
    private List<Option> options;

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
    
    @JsonIgnore
    public void addOption(Option option) {
        if (options == null) {
            options = new ArrayList<>();
        }
        options.add(option);
    }
    
    @JsonIgnore
    @Override
    public void validate() throws FormValidationException {
        required();
        inputVerify();
        server();
    }

}
