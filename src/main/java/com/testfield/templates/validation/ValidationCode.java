package com.testfield.templates.validation;

/**
 *
 * @author Andriy
 */
public enum ValidationCode {
    ERR(100), REQUIRED(101), MIN_LENGTH(102), MAX_LENGTH(103), INPUT_VER(104),
    SERVER(105);
    
    private final int code;
    
    private ValidationCode(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    public ValidationCode toEnum(int code) {
        for (ValidationCode validationCode: values()) {
            if (validationCode.getCode() == code) {
                return validationCode;
            }
        }
        return null;
    }
    
}
