/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import model.bean.UserTestfield;
import service.UserService;

/**
 *
 * @author andse
 */
public class Test {
    
    
    public static void main(String[] args) throws Exception {
        UserService userService = new UserService();
        System.out.println(userService.getUser("andriy").getPassword());
    }
}
