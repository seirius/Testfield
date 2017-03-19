package test;

import dao.DAO;
import java.lang.reflect.Constructor;
import org.hibernate.Session;

/**
 *
 * @author Andriy
 */
public class Test {
    
    public class Hola {
        public Hola() {
            System.out.println("Hola");
        }
    }
    
    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("util.AjaxResponse");
            Constructor<?> constructor = clazz.getConstructor();
            Object object = constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + 1);
        }
    }
    
}
