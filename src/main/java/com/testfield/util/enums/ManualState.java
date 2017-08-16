package com.testfield.util.enums;

/**
 *
 * @author Andriy
 */
public enum ManualState {
    PROGRESS(0, "In progress");
    
    private final int value;
    private final String text;
    
    ManualState(int value, String text) {
        this.value = value;
        this.text = text;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getText() {
        return text;
    }
    
    public static ManualState parse(int value) {
        switch(value) {
            case 0:
                return PROGRESS;
                
            default: 
                return null;
        }
    }
}
