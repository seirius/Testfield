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
public enum FontStyle {
    COLOR(1), FAMILY(2);
    
    private final int value;
    
    private FontStyle(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    public FontStyle toFontStyle(int value) {
        FontStyle chosenStyle = null;
        for (FontStyle fontStyle: FontStyle.values()) {
            if (value == fontStyle.getValue()) {
                chosenStyle = fontStyle;
            }
        }
        return chosenStyle;
    }
} 
