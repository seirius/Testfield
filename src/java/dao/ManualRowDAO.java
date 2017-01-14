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
import util.ErrorMsgs;
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
    
    public ManualRow getRow(String idRow) throws DAOException {
        ManualRow manualRow = null;
        try {
            manualRow = (ManualRow) session.get(ManualRow.class, idRow);
        } catch(Exception e) {
            DAOValidator.errorOnSelect("Manual's row", e);
        }
        return manualRow;
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
    
    public int updateOrder(String idRow, int rowOrder) throws DAOException {
        int updated = -1;
        try {
            String hql = ""
                    + "update ManualRow "
                    + "set "
                    + " rowOrder = :rowOrder "
                    + "where "
                    + " id = :id "
                    + "";
            
            updated = session.createQuery(hql)
                    .setInteger("rowOrder", rowOrder)
                    .setString("id", idRow)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Row's block", e);
        }
        return updated; 
    }
    
    public int minusOneWRange(String idPage, int startPosition, int endPosition) throws DAOException {
        int updated = -1;
        try {
            String hql = ""
                    + "update ManualRow "
                    + "set "
                    + " rowOrder = rowOrder - 1 "
                    + "where "
                    + " manualPage = :manualPage "
                    + "and "
                    + " rowOrder >= :startPosition "
                    + "and "
                    + " rowOrder <= :endPosition "
                    + "";
            
            updated = session.createQuery(hql)
                    .setString("manualPage", idPage)
                    .setInteger("startPosition", startPosition)
                    .setInteger("endPosition", endPosition)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Page's rows", e);
        }
        return updated;
    }
    
    public int plusOneWRange(String idPage, int startPosition, int endPosition) throws DAOException {
        int updated = -1;
        try {
            String hql = ""
                    + "update ManualRow "
                    + "set "
                    + " rowOrder = rowOrder + 1 "
                    + "where "
                    + " manualPage = :manualPage "
                    + "and "
                    + " rowOrder >= :startPosition "
                    + "and "
                    + " rowOrder <= :endPosition "
                    + "";
            
            updated = session.createQuery(hql)
                    .setString("manualPage", idPage)
                    .setInteger("startPosition", startPosition)
                    .setInteger("endPosition", endPosition)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Page's rows", e);
        }
        return updated;
    }
    
    public boolean isLast(String idRow) throws DAOException {
        boolean isLast = false;
        try {
            ManualRow row = getRow(idRow);
            String idPage = row.getManualPage();
            ManualRow lastRow = getLastRow(idPage);
            isLast = row.getRowOrder() == lastRow.getRowOrder();
        } catch(Exception e) {
            DAOValidator.errorOnCheck("on checking if row is last", e);
        }
        return isLast;
    }
    
    public boolean isFirst(String idRow) throws DAOException {
        boolean isFirst = false;
        try {
            ManualRow row = getRow(idRow);
            isFirst = row.getRowOrder() == 1;
        } catch(Exception e) {
            DAOValidator.errorOnCheck("on checking if row is first", e);
        }
        return isFirst; 
    }
    
    public void moveFoward(String idRow) throws DAOException {
        try {
            if (isLast(idRow)) {
                ErrorMsgs.sysLogInfo(("Can't move last row foward."));
                return;
            }
            
            ManualRow row = getRow(idRow);
            int newOrder = row.getRowOrder() + 1;
            minusOneWRange(row.getManualPage(), newOrder, newOrder);
            updateOrder(idRow, newOrder);
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Manual's row", e);
        }
    }
    
    public void moveBackward(String idRow) throws DAOException {
        try {
            if (isFirst(idRow)) {
                ErrorMsgs.sysLogInfo("Can't move first row backwards.");
                return;
            }
            
            ManualRow row = getRow(idRow);
            int newOrder = row.getRowOrder() - 1;
            plusOneWRange(row.getManualPage(), newOrder, newOrder);
            updateOrder(idRow, newOrder);
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Page's block", e);
        }
    }
    
}
