/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.enums;

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
