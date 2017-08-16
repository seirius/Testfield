package com.testfield.util.enums;

/**
 *
 * @author Andriy
 */
public enum ManualVisibility {
    VISIBLE(0, "Visible"), HIDDEN(1, "Hidden");
    
    private final int value;
    private final String text;
    
    ManualVisibility(int value, String text) {
        this.value = value;
        this.text = text;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getText() {
        return text;
    }
    
    public static ManualVisibility toEnum(int value) {
        for (ManualVisibility vis: values()) {
            if (vis.getValue() == value) {
                return vis;
            }
        }
        return null;
    }
}
