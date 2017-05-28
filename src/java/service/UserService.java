package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dao.UserDAO;
import model.bean.user.UserTestfield;
import util.ServiceReturn;
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
    
    public ServiceReturn login(ObjectNode form) throws Exception {
        JsonNode formJson = form.get("form");
        JsonNode inputs = formJson.get("inputs");
        String userNick = inputs.get(0).get("value").asText();
        String pw = inputs.get(1).get("value").asText();
        UserTestfield user = new UserTestfield();
        user.setUserNick(userNick);
        user.setPassword(pw);
        return login(user);
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
            
            session.setAttribute("user", user.getUserNick());
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
    
}
