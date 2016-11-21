/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.bean.ManualPage;
import model.bean.ManualRow;
import org.hibernate.Session;
import util.DAOValidator;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class ManualRowDAO {
    
    private final Session session;
    
    public ManualRowDAO(Session session) {
        this.session = session;
    }
    
    public ManualRow insert(String manualPage, int order) throws DAOException {
        ManualRow manualRow = null;
        try {
            ManualPage beanManualPage = new ManualPage();
            beanManualPage.setId(manualPage);
            
            manualRow = new ManualRow();
            manualRow.setManualPage(beanManualPage);
            manualRow.setOrder(order);
            session.save(manualRow);
        } catch(Exception e) {
            DAOValidator.errorOnInsert("Manual's row", e);
        }
        return manualRow;
    }
    
}
