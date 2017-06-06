package templates.forms.inputs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import service.FormService;
import templates.forms.Form;
import templates.validation.FormValidationException;
import templates.validation.NgModelOptions;
import templates.validation.Validation;
import templates.validation.ValidationCode;
import util.exceptions.ServiceException;

/**
 *
 * @author Andriy
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = InputText.class, name = "InputText")
    ,
    @JsonSubTypes.Type(value = InputSelect.class, name = "InputSelect")
    ,
    @JsonSubTypes.Type(value = InputTextarea.class, name = "InputTextarea")
    ,
    @JsonSubTypes.Type(value = InputRadio.class, name = "InputRadio")
    ,
    @JsonSubTypes.Type(value = InputCheckbox.class, name = "InputCheckbox")
})
public abstract class Input {

    @JsonIgnore
    protected Form form;
    protected String label;
    protected String name;
    protected Boolean showLabel;
    protected Object value;
    protected String controller;
    protected Validation validation;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getShowLabel() {
        return showLabel;
    }

    public void setShowLabel(Boolean showLabel) {
        this.showLabel = showLabel;
    }

    public Object getValue() {
        return value;
    }

    @JsonIgnore
    public <T> T getValue(Class<T> clazz) {
        return clazz.cast(value);
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    @JsonIgnore
    public void launchController() throws ServiceException {
        if (controller != null) {
            String[] ctrl = controller.split("\\.");
            FormService.callCharger(ctrl[0], ctrl[1], form, this);
        }
    }

    @JsonIgnore
    public void validate() throws FormValidationException {
    }

    @JsonIgnore
    public InputText getAsText() {
        return (InputText) this;
    }

    @JsonIgnore
    public InputSelect getAsSelect() {
        return (InputSelect) this;
    }

    public void required() throws FormValidationException {
        if (validation != null
                && validation.getRequired() != null
                && validation.getRequired()) {
            if (value == null
                    || (value instanceof String
                    && ((String) value).trim().length() == 0)) {
                throw new FormValidationException(label, ValidationCode.REQUIRED);
            }
        }
    }

    @JsonIgnore
    public void minLength() throws FormValidationException {
        if (validation != null
                && validation.getMinLength() != null
                && value != null
                && value instanceof String
                && ((String) value).length() < validation.getMinLength()) {
            throw new FormValidationException(label, ValidationCode.MIN_LENGTH);
        }
    }

    @JsonIgnore
    public void maxLength() throws FormValidationException {
        if (validation != null
                && validation.getMaxLength() != null
                && value != null
                && value instanceof String
                && ((String) value).length() > validation.getMaxLength()) {
            throw new FormValidationException(label, ValidationCode.MAX_LENGTH);
        }
    }

    @JsonIgnore
    public void minNumberLength() throws FormValidationException {
        if (validation != null
                && validation.getMinLength() != null
                && value != null) {
            if (value instanceof Integer) {
                Integer aux = (Integer) value;
                if (aux < validation.getMinLength()) {
                    throw new FormValidationException(label, ValidationCode.MIN_LENGTH);
                }
            }

            if (value instanceof Double) {
                Double aux = (Double) value;
                if (aux < validation.getMinLength()) {
                    throw new FormValidationException(label, ValidationCode.MIN_LENGTH);
                }
            }
        }
    }

    @JsonIgnore
    public void maxNumberLength() throws FormValidationException {
        if (validation != null
                && validation.getMaxLength() != null
                && value != null) {
            if (value instanceof Integer) {
                Integer aux = (Integer) value;
                if (aux > validation.getMaxLength()) {
                    throw new FormValidationException(label, ValidationCode.MIN_LENGTH);
                }
            }

            if (value instanceof Double) {
                Double aux = (Double) value;
                if (aux > validation.getMaxLength()) {
                    throw new FormValidationException(label, ValidationCode.MIN_LENGTH);
                }
            }
        }
    }

    @JsonIgnore
    public void inputVerify() throws FormValidationException {
        if (validation != null
                && validation.getInputVerify() != null) {
            String modelInputName = validation.getInputVerify();
            Input modelInput = form.getInputByName(modelInputName);
            if (modelInput == null || modelInput.getValue() == null
                    || value == null || !value.equals(modelInput.getValue())) {
                throw new FormValidationException(label, ValidationCode.INPUT_VER);
            }
        }
    }

    @JsonIgnore
    public void cloneValue(Input input) {
        this.value = input.getValue();
    }

}
