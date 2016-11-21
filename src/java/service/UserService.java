package service;

import dao.UserDAO;
import model.bean.UserTestfield;
import util.ServiceReturn;
import util.dummys.UserDummy;
import util.exceptions.DAOException;
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
            user = new UserTestfield();
            user.setUserNick(userId);
            user.setPassword(password);
            
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
                throw new ServiceException("User or Password incorrect.");
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
    
    /**
     * Check if the user exists in the Database by userNick and password 
     * 
     * @param user
     * @return ServiceReturn {
     *      UserTestfield user
     * }
     * @throws Exception 
     */
    public ServiceReturn login(UserTestfield user) throws Exception {
        ServiceReturn result = new ServiceReturn();
        
        try {
            MANAGER.beginTransaction();
            UserDAO userDAO = MANAGER.getUserDAO();
            user = userDAO.login(user);
            if (user == null) {
                throw new ServiceException("User or Password incorrect.");
            }
            
            result.addItem("user", user);
            MANAGER.commit();
        } catch(DAOException | ServiceException e) {
            MANAGER.rollback();
            throw treatException("Couldn't login, try again later.", e);
        } finally {
            MANAGER.close();
        }
        
        return result;
    }
    
    public ServiceReturn createUser(String userNick, String password, String email) throws Exception {
        ServiceReturn result = new ServiceReturn();
        
        try {
            MANAGER.beginTransaction();
            UserDAO userDAO = MANAGER.getUserDAO();
            UserTestfield user = userDAO.createUser(userNick, password, email);
            result.addItem("user", user);
            
            MANAGER.commit();
        } catch(DAOException e) {
            MANAGER.rollback();
            throw treatException("An error has ocurred creating the user, try again later.", e);
        } finally {
            MANAGER.close();
        }
        
        return result;
    }
    
    public ServiceReturn createUser(UserDummy user) throws Exception {
        return createUser(user.userNick, user.password, user.email);
    }
    
}
