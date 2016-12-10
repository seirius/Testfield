/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.bean.manual.ManualPage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import util.DAOValidator;
import util.exceptions.BeanException;
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
    
    public ManualPage insert(int manual, int order) throws DAOException {
        ManualPage manualPage = null;
        try {
            manualPage = new ManualPage();
            manualPage.setManualId(manual);
            manualPage.setPageOrder(order);
            session.save(manualPage);
        } catch(BeanException e) {
            DAOValidator.errorOnInsert("Manual's page", e);
        }
        return manualPage;
    }
    
    public ManualPage insertPlusOne(int manualId, int pageOrder) throws DAOException {
        ManualPage manualPage = null;
        try {
            plusOne(manualId, pageOrder);
            manualPage = insert(manualId, pageOrder);
        } catch(Exception e) {
            DAOValidator.errorOnInsert("Manual's page", e);
        }
        return manualPage;
    }
    
    public ManualPage insertLast(int manual) throws DAOException {
        ManualPage manualPage = null;
        try {
            ManualPage lastPage = getLastPage(manual);
            int pageOrder = lastPage == null ? 1 : lastPage.getPageOrder() + 1;
            manualPage = insert(manual, pageOrder);
        } catch(Exception e) {
            DAOValidator.errorOnInsert("Manual's page", e);
        }
        return manualPage;
    } 
    
    public ManualPage getLastPage(int manual) throws DAOException {
        ManualPage manualPage = null;
        try {
            DetachedCriteria maxPage = DetachedCriteria.forClass(ManualPage.class)
                    .setProjection(Projections.max("pageOrder"))
                    .add(Restrictions.eq("manualId", manual));
            
            Criteria pages = session.createCriteria(ManualPage.class)
                    .add(Property.forName("pageOrder").eq(maxPage))
                    .add(Restrictions.eq("manualId", manual));
            
            List<ManualPage> pageList = pages.list();
            if (pageList.size() == 1) {
                manualPage = pageList.get(0);
            }
        } catch(Exception e) {
            DAOValidator.errorOnSelect("Manual's page", e);
        }
        return manualPage;
    }
    
    public int plusOne(int manual, int startPosition) throws DAOException {
        int updated;
        try {
            String hql = ""
                    + "update "
                    + "ManualPage "
                    + "set "
                    + " pageOrder = pageOrder + 1 "
                    + "where "
                    + " manualId = :manualId "
                    + "and "
                    + " pageOrder >= :pageOrder "
                    + "";
            updated = session.createQuery(hql)
                    .setInteger("manualId", manual)
                    .setInteger("pageOrder", startPosition)
                    .executeUpdate();
        } catch(Exception e) {
            updated = -1;
            DAOValidator.errorOnUpdate("Manual's pages", e);
        }
        return updated;
    }
    
    public int delete(String idPage) throws DAOException {
        int deleted = -1;
        try {
            new ManualRowDAO(session).deleteByPage(idPage);
            
            String hql = ""
                    + "delete ManualPage "
                    + "where id = :id"
                    + "";
            
            deleted = session.createQuery(hql)
                    .setString("id", idPage)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnDelete("Manual's Page", e);
        }
        return deleted;
    }
    
}
