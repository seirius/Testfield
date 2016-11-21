/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import java.util.Set;
import model.bean.Manual;
import model.bean.ManualPage;
import model.bean.ManualRow;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import util.enums.ManualState;
import util.enums.ManualVisibility;
import util.exceptions.BeanException;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class ManualDAO {
 
    private final Session session;
    
    public ManualDAO(Session session) {
        this.session = session;
    }
    
    public Manual insert(String userNick, String title, ManualVisibility visibilty, ManualState state) throws DAOException, BeanException {
        Manual manual = null;
        try {
            manual = new Manual();
            manual.setUserNick(userNick);
            manual.setTitle(title);
            manual.setVisibility(visibilty);
            manual.setStateCurrent(state);
            Date dateNow = new Date();
            manual.setDateCreation(dateNow);
            manual.setDateLastMod(dateNow);
            
            session.save(manual);
        } catch(BeanException e) {
            throw (BeanException) e;
        } catch(Exception e) {
            throw new DAOException("Error while creating new Manual.", e);
        }
        return manual;
    }
    
    public Manual getManual(String idManual) throws DAOException {
        Manual manual = null;
        try {
            Object objManual = session.get(Manual.class, idManual);
            if (objManual != null) {
                manual = (Manual) objManual;
                Set<ManualPage> pages = manual.getManualPages();
                pages.size();
                pages.forEach((page) -> {
                    Set<ManualRow> rows = page.getManualRows();
                    rows.size();
                    rows.forEach((row) -> {
                        row.getManualBlocks().size();
                    });
                });
                manual.getTags().size();
            }
            
        } catch(Exception e) {
            throw new DAOException("Error getting Manual.", e);
        }
        return manual;
    }
    
    public List<Manual> getManuals(String userNick) throws DAOException {
        List<Manual> manuals = null;
        try {
            Criteria criteria = session.createCriteria(Manual.class);
            criteria.add(Restrictions.eq("userNick", userNick));
            manuals = criteria.list();
            manuals.forEach((manual) -> {
                manual.setManualPages(null);
                manual.setTags(null);
            });
            
        } catch(HibernateException e) {
            throw new DAOException("Error getting Manuals.", e);
        }
        return manuals;
    }
    
    public Manual setTitle(String manualId, String newTitle) throws DAOException {
        Manual manual;
        try {
            manual = new Manual();
            manual.setId(manualId);
            manual.setTitle(newTitle);
            session.update(manual);
        } catch(BeanException e) {
            throw new DAOException("Error updating the Manual's title.", e);
        }
        return manual;
    }
     
}
