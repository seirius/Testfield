package templates.forms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Andriy
 */
public class Form {
    
    private List<Input> inputs;

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }
    
    public String ngInit() throws ParseException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonForm = mapper.createObjectNode();
        List<Object> values;
        for (Input input: inputs) {
            switch (input.getFormGroup()) {
                case TEXT:
                case RADIO:
                case SELECT:
                case TEXTAREA:
                    jsonForm.put(input.getName(), input.getValue());
                    break;
                case CHECKBOX:
                    jsonForm.put(input.getName(), input.getValueCheckbox());
                    break;
                case CHECKBOX_GRP:
                    List<String> names = input.getNames();
                    values = input.getValues();
                    for (int i = 0; i < names.size(); i++) {
                        String name = names.get(i);
                        jsonForm.put(name, (Boolean) values.get(i));
                    }
                    break;
            }
        }
        
        return "form = " + jsonForm.toString();
    }
    
}
