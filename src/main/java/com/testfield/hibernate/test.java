/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testfield.hibernate;

import java.io.File;

/**
 *
 * @author Andriy
 */
public class test {
    
    public static void main(String[] args) {
        System.out.println("Hibernate xml: " + new File("resources/hibernate.cfg.xml").exists());
    }
    
}
