package dao;

import java.io.File;
import java.util.List;
import model.bean.manual.Manual;
import model.bean.manual.ManualBlock;
import model.bean.manual.ManualPage;
import model.bean.manual.ManualRow;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import util.DAOValidator;
import util.enums.FilePath;
import util.enums.ManualState;
import util.enums.ManualVisibility;
import util.exceptions.BeanException;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class ManualDAO extends DAO {
    
    private final String ERR_GET = "Error getting Manual.";
    private final String ERR_GETS = "Error getting Manuals.";
 
    public ManualDAO(Session session) {
        super(session);
    }
    
    public Manual insert(String userNick, String title, ManualVisibility visibilty, 
            ManualState state) throws DAOException, BeanException {
        Manual manual = null;
        try {
            manual = new Manual();
            manual.setUserNick(userNick);
            manual.setTitle(title);
            manual.setManualsVisibility(visibilty);
            manual.setCurrentState(state);
            
            session.save(manual);
        } catch(BeanException e) {
            throw (BeanException) e;
        } catch(Exception e) {
            throw new DAOException("Error while creating new Manual.", e);
        }
        return manual;
    }
    
    public Manual getManual(int idManual) throws DAOException {
        Manual manual = null;
        try {
            Object objManual = session.get(Manual.class, idManual);
            if (objManual != null) {
                manual = (Manual) objManual;
            }
            
        } catch(Exception e) {
            throw new DAOException(ERR_GET, e);
        }
        return manual;
    }
    
    public Manual update(Manual manual) {
        session.update(manual);
        return manual;
    }
    
    public Manual getManualByBlock(String idBlock) throws DAOException {
        Manual manual = null;
        
        try {
            DetachedCriteria blocks = DetachedCriteria.forClass(ManualBlock.class)
                    .setProjection(Projections.property("manualRow"))
                    .add(Restrictions.eq("id", idBlock));
            
            DetachedCriteria rows = DetachedCriteria.forClass(ManualRow.class)
                    .setProjection(Projections.property("manualPage"))
                    .add(Property.forName("id").in(blocks));
            
            DetachedCriteria pages = DetachedCriteria.forClass(ManualPage.class)
                    .setProjection(Projections.property("manualId"))
                    .add(Property.forName("id").in(rows));
            
            Criteria manuals = session.createCriteria(Manual.class)
                    .add(Property.forName("id").in(pages));
            
            List<Manual> manualList = manuals.list();
            if (manualList.size() == 1) {
                manual = manualList.get(0);
            } else if (manualList.isEmpty()) {
                throw new Exception("No manuals found for manual block.");
            } else {
                throw new Exception("More than one manual found (impossible?).");
            }
        } catch(Exception e) {
            DAOValidator.errorOnSelect("Manual", e);
        }
        
        return manual;
    }
    
    public Manual getManualByRow(String idRow) throws DAOException {
        Manual manual = null;
        
        try {
            DetachedCriteria rows = DetachedCriteria.forClass(ManualRow.class)
                    .setProjection(Projections.property("manualPage"))
                    .add(Restrictions.eq("id", idRow));
            
            DetachedCriteria pages = DetachedCriteria.forClass(ManualPage.class)
                    .setProjection(Projections.property("manualId"))
                    .add(Property.forName("id").in(rows));
            
            Criteria manuals = session.createCriteria(Manual.class)
                    .add(Property.forName("id").in(pages));
            
            List<Manual> manualList = manuals.list();
            if (manualList.size() == 1) {
                manual = manualList.get(0);
            } else if (manualList.isEmpty()) {
                throw new Exception("No manuals found for manual row.");
            } else {
                throw new Exception("More than one manual found (impossible?).");
            }
        } catch(Exception e) {
            DAOValidator.errorOnSelect("Manual", e);
        }
        
        return manual;
    }
    
    public Manual getManualByPage(String idPage) throws DAOException {
        Manual manual = null;
        try {
            ManualPage page = (ManualPage) session.get(ManualPage.class, idPage);
            if (page == null) {
                throw new DAOException("No page found with this id: " + idPage);
            }
            
            int idManual = page.getManualId();
            manual = getManual(idManual);
        } catch(DAOException e) {
            throw e;
        } catch(Exception e) {
            throw new DAOException(ERR_GET, e);
        }
        return manual;
    }
    
    public List<Manual> getManuals(String userNick) throws DAOException {
        List<Manual> manuals = null;
        try {
            Criteria criteria = session.createCriteria(Manual.class);
            criteria.add(Restrictions.eq("userNick", userNick));
            manuals = criteria.list();
        } catch(HibernateException e) {
            throw new DAOException(ERR_GETS, e);
        }
        return manuals;
    }
    
    public Manual setTitle(int manualId, String newTitle) throws DAOException {
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
    
    public String getTitle(int manualId) throws DAOException {
        String hql = ""
                + "SELECT title "
                + "FROM Manual "
                + "WHERE id = :id"
                + "";
        Query query = session.createQuery(hql);
        query.setInteger("id", manualId);
        List<String> lista = query.list();
        if (lista.isEmpty()) {
            throw new DAOException(String.format("There is no manuals with this ID(%d)", manualId));
        }
        return lista.get(0);
    }
    
    public String treatManualPath(String contextPath, int manualId) {
        String path = String.format("%s%s%s%smanual_%d",
                contextPath,
                File.separator,
                FilePath.MANUAL_PATH.getPath(),
                File.separator,
                manualId);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        
        return path;
    }
     
}
