package templates.forms.inputs;

import java.util.List;
import templates.forms.inputs.subinputs.Option;

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
    
}
