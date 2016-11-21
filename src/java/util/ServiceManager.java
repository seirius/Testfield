package util;

import dao.BlockWidthTypeDAO;
import dao.ManualBlockDAO;
import dao.ManualDAO;
import dao.ManualPageDAO;
import dao.ManualRowDAO;
import dao.TagDAO;
import dao.UserDAO;
import dao.UserInfoDAO;
import dao.WidthTypeDAO;
import hibernate.HibernateUtil;
import org.hibernate.Session;

/**
 * @author Andriy Yednarovych
 */
public class ServiceManager {

    private Session session;

    public ServiceManager() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public UserDAO getUserDAO() {
        return new UserDAO(session);
    }
    
    public UserInfoDAO getUserInfoDAO() {
        return new UserInfoDAO(session);
    }
    
    public ManualDAO getManualDAO() {
        return new ManualDAO(session);
    }
    
    public ManualPageDAO getManualPageDAO() {
        return new ManualPageDAO(session);
    }
    
    public ManualRowDAO getManualRowDAO() {
        return new ManualRowDAO(session);
    }
    
    public ManualBlockDAO getManualBlockDAO() {
        return new ManualBlockDAO(session);
    }
    
    public TagDAO getTagDAO() {
        return new TagDAO(session);
    }
    
    public WidthTypeDAO getWidthTypeDAO() {
        return new WidthTypeDAO(session);
    }
    
    public BlockWidthTypeDAO getBlockWidthTypeDAO() {
        return new BlockWidthTypeDAO(session);
    }

    public Session getSession() {
        return session;
    }

    public void beginTransaction() {
        session.beginTransaction();
    }

    public void rollback() {
        session.getTransaction().rollback();
    }

    public void rollbackClose() {
        session.getTransaction().rollback();
        session.close();
    }

    public void commitClose() {
        session.getTransaction().commit();
        session.close();
    }

    public void commit() {
        session.getTransaction().commit();
    }

    public void close() {
        session.close();
    }
    
    public void openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

}
