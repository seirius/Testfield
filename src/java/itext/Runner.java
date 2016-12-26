/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itext;

import hibernate.HibernateUtil;
import model.bean.manual.ManualBlock;
import util.ServiceManager;

/**
 *
 * @author Andriy
 */
public class Runner {
    
    public static void main(String[] args) {
        ThreadController threadController = new ThreadController(new Object[] {
            "14b1e219-1b87-4ec9-8eab-c016008d3664", 
            "21232287-f518-4244-a366-d59dc6bf0886", 
            "5216dd6b-3804-40b7-877b-e13860cb81fb", 
            "acb119ba-daff-473a-b76e-79ab5add29ed", 
            "b22366a7-186e-41a6-967e-cf3dc4907d3c", 
            "ed6a823e-947c-4d12-a0ea-43934cdef02d"
        }, 3);
        threadController.setUnitInstructions(new UnitInstructions() {
            @Override
            public void instructions(Object data) {
                ServiceManager manager = new ServiceManager();
                try {
                    manager.beginTransaction();
                    
                    ManualBlock block = (ManualBlock) manager.getSession().get(ManualBlock.class, data.toString());
                    
                    manager.commit();   
                    System.out.println(block);
                } catch(Exception e) {
                    manager.rollback();
                } finally {
                    manager.close();
                }
                
            }
            
            @Override
            public void allDone() {
                HibernateUtil.close();
            }
        });
        threadController.run();
    }
    
}
