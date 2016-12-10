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
public enum MoveOptions {
    UP(0), DOWN(1), LEFT(2), RIGHT(3);
    
    private final int value;
    
    MoveOptions(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    public static MoveOptions toMoveOptions(int value) {
        switch(value) {
            case 0:
                return UP;
                
            case 1:
                return DOWN;
                
            case 2:
                return LEFT;
                
            case 3:
                return RIGHT;
                
            default:
                return null;
        }
    }
}
