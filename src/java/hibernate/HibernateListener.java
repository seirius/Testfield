/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Andriy
 */
public class HibernateListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("-- SessionFactory.start");
        HibernateUtil.getSessionFactory(); // Just call the static initializer of that class      
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("-- SessionFactory.stop");
        HibernateUtil.close();// Free all resources  
    }
}
