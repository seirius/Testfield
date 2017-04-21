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
public enum BlockWidthTypeEnum {
    XS(1, "Phones", "xs"), 
    SM(2, "Tablets", "sm"), 
    MD(3, "Default", "md"), 
    LG(4, "Large display", "lg");
    
    private final int value;
    private final String text;
    private final String css;
    
    BlockWidthTypeEnum(int value, String text, String css) {
        this.value = value;
        this.text = text;
        this.css = css;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getText() {
        return text;
    }
    
    public String getCss() {
        return css;
    }
    
    public static BlockWidthTypeEnum getWidthTypeEnum(int value) {
        for (BlockWidthTypeEnum val: values()) {
            if (val.getValue() == value) {
                return val;
            }
        }
        return null;
    }
}
