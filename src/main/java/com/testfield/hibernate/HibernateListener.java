package com.testfield.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Andriy
 */
public class HibernateListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            System.out.println("-- SessionFactory.start");
            HibernateUtil.getSessionFactory(); // Just call the static initializer of that class      
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            System.out.println("-- SessionFactory.stop");
            HibernateUtil.close();// Free all resources  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
