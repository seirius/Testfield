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
    XS(1, "Phones"), SM(2, "Tablets"), MD(3, "Default"), LG(4, "Large display");
    
    private final int value;
    private final String text;
    
    BlockWidthTypeEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getText() {
        return text;
    }
}
