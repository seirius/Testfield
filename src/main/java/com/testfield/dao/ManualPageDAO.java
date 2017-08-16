package com.testfield.dao;

import com.testfield.model.bean.manual.ManualPage;
import com.testfield.util.DAOValidator;
import com.testfield.util.ErrorMsgs;
import com.testfield.util.exceptions.BeanException;
import com.testfield.util.exceptions.DAOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Andriy
 */
public class ManualPageDAO extends DAO {
    
    public ManualPageDAO(Session session) {
        super(session);
    }
    
    public ManualPage getPage(String idPage) throws DAOException {
        ManualPage page = null;
        try {
            page = (ManualPage) session.get(ManualPage.class, idPage);
        } catch(Exception e) {
            DAOValidator.errorOnSelect("Manual's page", e);
        }
        return page;
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
    
    public int updateOrder(String idPage, int pageOrder) throws DAOException {
        int updated = -1;
        try {
            String hql = ""
                    + "update ManualPage "
                    + "set "
                    + " pageOrder = :pageOrder "
                    + "where "
                    + " id = :id "
                    + "";
            
            updated = session.createQuery(hql)
                    .setInteger("pageOrder", pageOrder)
                    .setString("id", idPage)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Page's block", e);
        }
        return updated; 
    }
    
    public int minusOneWRange(int idManual, int startPosition, int endPosition) throws DAOException {
        int updated = -1;
        try {
            String hql = ""
                    + "update ManualPage "
                    + "set "
                    + " pageOrder = pageOrder - 1 "
                    + "where "
                    + " manualId = :manualId "
                    + "and "
                    + " pageOrder >= :startPosition "
                    + "and "
                    + " pageOrder <= :endPosition "
                    + "";
            
            updated = session.createQuery(hql)
                    .setInteger("manualId", idManual)
                    .setInteger("startPosition", startPosition)
                    .setInteger("endPosition", endPosition)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Page's blocks", e);
        }
        return updated;
    }
    
    public int plusOneWRange(int idManual, int startPosition, int endPosition) throws DAOException {
        int updated = -1;
        try {
            String hql = ""
                    + "update ManualPage "
                    + "set "
                    + " pageOrder = pageOrder + 1 "
                    + "where "
                    + " manualId = :manualId "
                    + "and "
                    + " pageOrder >= :startPosition "
                    + "and "
                    + " pageOrder <= :endPosition "
                    + "";
            
            updated = session.createQuery(hql)
                    .setInteger("manualId", idManual)
                    .setInteger("startPosition", startPosition)
                    .setInteger("endPosition", endPosition)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Page's block", e);
        }
        return updated;
    }
    
    public void moveFoward(String idPage) throws DAOException {
        try {
            if (isLast(idPage)) {
                ErrorMsgs.sysLogInfo(("Can't move last page foward."));
                return;
            }
            
            ManualPage page = getPage(idPage);
            int newOrder = page.getPageOrder() + 1;
            minusOneWRange(page.getManualId(), newOrder, newOrder);
            updateOrder(idPage, newOrder);
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Manual's block", e);
        }
    }
    
    public void moveBackward(String idPage) throws DAOException {
        try {
            if (isFirst(idPage)) {
                ErrorMsgs.sysLogInfo("Can't move first page backwards.");
                return;
            }
            
            ManualPage page = getPage(idPage);
            int newOrder = page.getPageOrder() - 1;
            plusOneWRange(page.getManualId(), newOrder, newOrder);
            updateOrder(idPage, newOrder);
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Page's block", e);
        }
    }
    
    public boolean isLast(String idPage) throws DAOException {
        boolean isLast = false;
        try {
            ManualPage page = getPage(idPage);
            int idManual = page.getManualId();
            ManualPage lastPage = getLastPage(idManual);
            isLast = page.getPageOrder() == lastPage.getPageOrder();
        } catch(Exception e) {
            DAOValidator.errorOnCheck("on checking if page is last", e);
        }
        return isLast;
    }
    
    public boolean isFirst(String idPage) throws DAOException {
        boolean isFirst = false;
        try {
            ManualPage page = getPage(idPage);
            isFirst = page.getPageOrder() == 1;
        } catch(Exception e) {
            DAOValidator.errorOnCheck("on checking if page is first", e);
        }
        return isFirst; 
    }
    
}
