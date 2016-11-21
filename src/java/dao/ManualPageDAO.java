/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.bean.Manual;
import model.bean.ManualPage;
import org.hibernate.Session;
import util.DAOValidator;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class ManualPageDAO {
    
    private final Session session;
    
    public ManualPageDAO(Session session) {
        this.session = session;
    }
    
    public ManualPage insert(String manual, int order) throws DAOException {
        ManualPage manualPage = null;
        try {
            Manual beanManual = new Manual();
            beanManual.setId(manual);
            manualPage = new ManualPage();
            manualPage.setManual(beanManual);
            manualPage.setOrder(order);
            session.save(manualPage);
        } catch(Exception e) {
            DAOValidator.errorOnInsert("Manual's page", e);
        }
        return manualPage;
    }
    
}
