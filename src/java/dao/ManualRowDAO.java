/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.bean.manual.ManualRow;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
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
            manualRow = new ManualRow();
            manualRow.setManualPage(manualPage);
            manualRow.setRowOrder(order);
            session.save(manualRow);
        } catch(Exception e) {
            DAOValidator.errorOnInsert("Manual's row", e);
        }
        return manualRow;
    }
    
    public ManualRow insertLast(String manualPage) throws DAOException {
        ManualRow manualRow = null;
        try {
            ManualRow lastRow = getLastRow(manualPage);
            int order = 1;
            if (lastRow != null) {
                order = lastRow.getRowOrder() + 1;
            } 
            
            manualRow = insert(manualPage, order);
        } catch(DAOException e) {
            throw e;
        } catch(Exception e) {
            DAOValidator.errorOnInsert("Manual's row", e);
        }
        return manualRow;
    }
    
    public ManualRow insertPlusOne(String pageId, int rowOrder) throws DAOException {
        ManualRow manualRow = null;
        try {
            plusOne(pageId, rowOrder);
            manualRow = insert(pageId, rowOrder);
        } catch(Exception e) {
            DAOValidator.errorOnInsert("Manual's row", e);
        }
        return manualRow;
    }
    
    public ManualRow getLastRow(String manualPage) throws DAOException {
        ManualRow manualRow = null;
        try {
            DetachedCriteria maxRow = DetachedCriteria.forClass(ManualRow.class)
                    .setProjection(Projections.max("rowOrder"));
            Criteria rows = session.createCriteria(ManualRow.class)
                    .add(Property.forName("rowOrder").eq(maxRow));
            
            List<ManualRow> rowList = rows.list();
            if (rowList.size() == 1) {
                manualRow = rowList.get(0);
            }
        } catch(Exception e) {
            DAOValidator.errorOnSelect("Manual's row", e);
        }
        return manualRow;
    }
    
    public int plusOne(String pageId, int startPosition) throws DAOException {
        int updated;
        try {
            String hql = ""
                    + "update ManualRow "
                    + "set "
                    + " rowOrder = rowOrder + 1 "
                    + "where "
                    + " rowOrder >= :rowOrder "
                    + "and "
                    + " manualPage = :manualPage "
                    + "";
            
            updated = session.createQuery(hql)
                    .setInteger("rowOrder", startPosition)
                    .setString("manualPage", pageId)
                    .executeUpdate();
        } catch(Exception e) {
            updated = -1;
            DAOValidator.errorOnUpdate("Rows", e);
        }
        return updated;
    }
    
    public int delete(String idRow) throws DAOException {
        int deleted = -1;
        try {
            new ManualBlockDAO(session).deleteByRow(idRow);
            
            String hql = ""
                    + "delete ManualRow "
                    + "where id = :id";
            
            deleted = session.createQuery(hql)
                    .setString("id", idRow)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnDelete("Manual's Row", e);
        }
        return deleted;
    }
    
    public void deleteByPage(String idPage) throws DAOException {
        try {
            List<ManualRow> rows = session.createCriteria(ManualRow.class)
                    .add(Restrictions.eq("manualPage", idPage))
                    .list();
            for (ManualRow row: rows) {
                delete(row.getId());
            }
        } catch(Exception e) {
            DAOValidator.errorOnDelete("Manual's Rows", e);
        }
    }
    
}
