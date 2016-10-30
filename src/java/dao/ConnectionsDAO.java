/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import model.bean.Connections;
import model.bean.ConnectionsId;
import model.bean.UserTestfield;
import org.hibernate.Query;
import org.hibernate.Session;
import util.exceptions.DAOException;

/**
 * @author Andriy Yednarovych
 */
public class ConnectionsDAO {
    
    private final Session session;
    
    public ConnectionsDAO(Session session) {
        this.session = session;
    }
    
    public Connections getConnection(String user, String token) throws DAOException {
        Connections connection = null;
        
        try {
            connection = (Connections) session.get(Connections.class, new ConnectionsId(token, user));
        } catch(Exception e) {
            throw new DAOException("Couldn't retrieve the user Connection.");
        }
        
        return connection;
    }

    public Connections save(String user) throws DAOException {
        Connections connection = null;
        
        try {
            connection = new Connections(user);
            session.save(connection);
        } catch(Exception e) {
            throw new DAOException("Couldn't save Connection.", e);
        }
        
        return connection;
    }
    
    public int getNumberConnections(String user) throws DAOException {
        int numConnections = 0;
        
        try {
            String hql = ""
                    + "from Connections con "
                    + "where con.id.userNick = :userNick "
                    + "";
            
            Query query = session.createQuery(hql);
            query.setParameter("userNick", user);
            numConnections = query.list().size();

        } catch(Exception e) {
            throw new DAOException("Couldn't get the number of Connections.", e);
        }
        
        return numConnections;
    }
    
    public void delete(String user, String token) throws DAOException {
        try {
            Connections connection = getConnection(user, token);
            session.delete(connection);
        } catch(Exception e) {
            throw new DAOException("Couldn't delete the connection.", e);
        }
    }
    
}
