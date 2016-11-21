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
    
    public static ManualVisibility parse(int value) {
        switch(value) {
            case 0:
                return VISIBLE;
            
            case 1:
                return HIDDEN;
                
            default:
                return null;
        }
    }
}
