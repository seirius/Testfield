package com.testfield.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author andse
 */
public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;
    private static StandardServiceRegistry serviceRegistry;
    
    static {
//        try {
//            // Create the SessionFactory from standard (hibernate.cfg.xml) 
//            // config file.
//            Configuration configuration = new Configuration();
//            URL url = HibernateUtil.class.getClassLoader().getResource("hibernate.cfg.xml");
//            System.out.println("URL: " + url);
//            configuration.configure(url);
//            configuration.setInterceptor(new ManualsInterceptor());
//            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//            SESSION_FACTORY = configuration.buildSessionFactory(serviceRegistry);
//        } catch (HibernateException ex) {
//            // Log the exception. 
//            System.err.println("Initial SessionFactory creation failed." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
        try {
            serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure(HibernateUtil.class.getClassLoader().getResource("hibernate.cfg.xml")).build();
            Metadata metaData = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
            SESSION_FACTORY = metaData.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void close() {
        SESSION_FACTORY.close();
        if (serviceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }

}
