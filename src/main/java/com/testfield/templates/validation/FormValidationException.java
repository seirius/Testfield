package com.testfield.templates.validation;

/**
 *
 * @author Andriy
 */
public class FormValidationException extends Exception {
    
    private ValidationCode validationCode;
    private String labelName;
    
    public FormValidationException(String labelName) {
        this.labelName = labelName;
        this.validationCode = ValidationCode.ERR;
    }
    
    public FormValidationException(String labelName, ValidationCode validationCode) {
        this.labelName = labelName;
        this.validationCode = validationCode;
    }
    
    public FormValidationException(String labelName, ValidationCode validationCode,
            Exception e) {
        super(e);
        this.labelName = labelName;
        this.validationCode = validationCode;
    }
    
    public FormValidationException(String msg, Throwable e) {
        super(msg, e);
    }
    
    public FormValidationException(Throwable e) {
        super(e);
    }

    public ValidationCode getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(ValidationCode validationCode) {
        this.validationCode = validationCode;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
    
    
    
}
