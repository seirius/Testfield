package service;

import dao.ConnectionsDAO;
import dao.UserDAO;
import java.util.HashMap;
import model.bean.Connections;
import model.bean.UserTestfield;
import util.ServiceReturn;
import util.exceptions.ServiceException;

/**
 * @author Andriy Yednarovych
 */
public class UserService extends Service {
    
    public UserTestfield getUser(String userId) throws Exception {
        UserTestfield user = null;
        
        try {
            user = MANAGER.getUserDAO().getUser(userId);
        } catch(Exception e) {
            throw treatException("Couldn't get User", e);
        } finally {
            MANAGER.close();
        }
        
        return user;
    }
    
    public UserTestfield saveUser(String userId, String password) throws Exception {
        UserTestfield user = null;
        
        try {
            user = new UserTestfield(userId, password);
            
            MANAGER.beginTransaction();
            MANAGER.getUserDAO().saveUser(user);
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException("Couldn't save User", e);
        } finally {
            MANAGER.close();
        }
        
        return user;
    }
    
    /**
     * Check if the user exists in the Database by userNick and password 
     * 
     * @param userId
     * @param password
     * @return ServiceReturn {
     *      UserTestfield user
     * }
     * @throws Exception 
     */
    public ServiceReturn login(String userId, String password) throws Exception {
        ServiceReturn result = new ServiceReturn();
        
        try {
            MANAGER.beginTransaction();
            UserDAO userDAO = MANAGER.getUserDAO();
            UserTestfield user = userDAO.login(userId, password);
            if (user == null) {
                throw new ServiceException("User or Passowrd incorrect.");
            }
            
            result.addItem("user", user);
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException("Couldn't login, try again later.", e);
        } finally {
            MANAGER.close();
        }
        
        return result;
    }
    
    public HashMap<String, Object> login(String userId, String password, int maxConnections) throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            MANAGER.beginTransaction();
            UserDAO userDAO = MANAGER.getUserDAO();
            
            UserTestfield user = userDAO.login(userId, password);
            Connections connection = null;
            if (user != null) {
                ConnectionsDAO connectionsDAO = MANAGER.getConnectionsDAO();
                int numConnections = connectionsDAO.getNumberConnections(user.getUserNick());
                if (numConnections < maxConnections) {
                    connection = connectionsDAO.save(user.getUserNick());
                } else {
                    throw new ServiceException("You reached the maximum amount of connections at the same time.");
                }
            } else {
                throw new ServiceException("User or password incorrect.");
            }
            
            result.put("user", user);
            result.put("connection", connection);
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException("Couldn't login, try again later.", e);
        } finally {
            MANAGER.close();
        }
        
        return result;
    }
    
    
}
