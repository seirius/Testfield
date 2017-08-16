package com.testfield.util.enums;

/**
 *
 * @author Andriy
 */
public enum DeleteOptions {
    MANUAL(0), PAGE(1), ROW(2), BLOCK(3);
    
    private final int value;
    
    DeleteOptions(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    public static DeleteOptions toDeleteOptions(String value) {
        switch (value) {
            case "MANUAL":
                return DeleteOptions.MANUAL;
                
            case "PAGE": 
                return DeleteOptions.PAGE;
                
            case "ROW":
                return DeleteOptions.ROW;
                
            case "BLOCK": 
                return DeleteOptions.BLOCK;
                
            default:
                return null;
        }
    }
} 
