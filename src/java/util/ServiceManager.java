package util;

import dao.ConnectionsDAO;
import dao.UserDAO;
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
    
    public ConnectionsDAO getConnectionsDAO() {
        return new ConnectionsDAO(session);
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
