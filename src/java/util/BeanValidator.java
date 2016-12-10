/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import util.exceptions.BeanException;

/**
 *
 * @author Andriy
 */
public class BeanValidator {
    
    public static void validateLength(String item, int number, String name) throws BeanException {
        if (item.length() > number) {
            throw new BeanException(name + "'s length can't be more than " + number + " characters");
        }
    }
    
    public static void validateLength(int item, int number, String name) throws BeanException {
        validateLength(String.valueOf(item), number, name);
    }
    
    public static void validateEmpty(String item, String name) throws BeanException {
        if (item == null || item.length() == 0) {
            throw new BeanException(name + " can't be empty.");
        }
    }
    
    public static void validateNull(Object item, String name) throws BeanException {
        if (item == null) {
            throw new BeanException(name + " can't be null.");
        }
    }
    
}
