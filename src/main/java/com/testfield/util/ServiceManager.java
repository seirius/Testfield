package com.testfield.util;

import com.testfield.dao.BlockWidthTypeDAO;
import com.testfield.dao.DAO;
import com.testfield.dao.ManualBlockDAO;
import com.testfield.dao.ManualConfDAO;
import com.testfield.dao.ManualDAO;
import com.testfield.dao.ManualPageDAO;
import com.testfield.dao.ManualRowDAO;
import com.testfield.dao.TagDAO;
import com.testfield.dao.UserDAO;
import com.testfield.dao.UserInfoDAO;
import com.testfield.dao.WidthTypeDAO;
import com.testfield.hibernate.HibernateUtil;
import com.testfield.util.enums.DAOList;
import java.lang.reflect.Constructor;
import org.hibernate.Session;

/**
 * @author Andriy Yednarovych
 */
public class ServiceManager {

    private Session session;

    public ServiceManager() {
        session = HibernateUtil.getSessionFactory().openSession();
    }
    
    public DAO getDAO(DAOList daoItem) {
        DAO dao = null;
        try {
            Class<?> clazz = Class.forName(daoItem.getValue());
            Constructor<?> constructor = clazz.getConstructor(Session.class);
            dao = (DAO) constructor.newInstance(new Object[] {session});
        } catch (Exception e) {
            ErrorMsgs.sysLogThis(e);
        }
        return dao;
    }
    
    public <T> T getDAO(Class<T> daoClass) {
        DAO dao = null;
        try {
            Constructor<?> constructor = daoClass.getConstructor(Session.class);
            dao = (DAO) constructor.newInstance(new Object[] {session});
        } catch (Exception e) {
            ErrorMsgs.sysLogThis(e);
        }
        return daoClass.cast(dao);
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
    
    public ManualConfDAO getManualConfDAO() {
        return new ManualConfDAO(session);
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
        close();
    }

    public void commitClose() {
        session.getTransaction().commit();
        close();
    }

    public void commit() {
        session.getTransaction().commit();
    }

    public void close() {
        if (session.isOpen()) {
            session.close();
        }
    }
    
    public void openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

}
