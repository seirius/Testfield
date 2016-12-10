/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import hibernate.HibernateUtil;
import java.util.List;
import model.bean.widthtype.WidthType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import util.ServiceManager;


/**
 *
 * @author andse
 */
public class Test {
    
    
    public static void main(String[] args) {
        ServiceManager manager = new ServiceManager();
        try {
            manager.beginTransaction();
            Session session = manager.getSession();
            
            Criteria criteria = session.createCriteria(WidthType.class);
            List<WidthType> widthTypes = criteria.list();
            for (WidthType widthType: widthTypes) {
                System.out.println(widthType);
            }
            
            manager.commit();
        } catch(Exception e) {
            e.printStackTrace();
        }  finally {
            manager.close();
            HibernateUtil.close();
        }
        
    }
}
