/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import model.bean.Connections;

/**
 * @author Andriy Yednarovych
 */
public class ConnectionsService extends Service {
    
    public Connections save(String user) throws Exception {
        Connections connection = null;
        
        try {
            MANAGER.beginTransaction();
            MANAGER.getConnectionsDAO().save(user);
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        
        return connection;
    }
    
    public void delete(String user, String token) throws Exception {
        try {
            MANAGER.beginTransaction();
            MANAGER.getConnectionsDAO().delete(user, token);
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
    }
    
    public int getNumberConnections(String user) throws Exception {
        int numConnections = 0;
        
        try {
            numConnections = MANAGER.getConnectionsDAO().getNumberConnections(user);
        } catch(Exception e) {
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        
        return numConnections;
    }
    
}
