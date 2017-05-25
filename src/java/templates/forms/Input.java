package templates.forms;

import java.util.List;
import util.enums.FormGroup;

/**
 *
 * @author Andriy
 */
public class Input {
    
    private String label;
    private String name;
    private List<String> names;
    private String value;
    private String type;
    private String typeName;
    private List<Object> values;
    private List<String> texts;
    private String rows;
    private String cols;
    private Boolean valueCheckbox;

    public Boolean getValueCheckbox() {
        return valueCheckbox;
    }

    public void setValueCheckbox(Boolean valueCheckbox) {
        this.valueCheckbox = valueCheckbox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getCols() {
        return cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
    
    public FormGroup getFormGroup() {
        return FormGroup.toEnum(type);
    }
    
}
