package com.testfield.service;

import com.testfield.dao.UserDAO;
import com.testfield.model.bean.user.UserTestfield;
import com.testfield.templates.forms.Form;
import com.testfield.templates.forms.inputs.InputText;
import com.testfield.util.ServiceReturn;
import com.testfield.util.exceptions.DAOException;
import com.testfield.util.exceptions.ServiceException;

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
    
    public ServiceReturn login(Form form) throws Exception {
        InputText userInput = form.getInputByName("user").getAsText();
        InputText userPassword = form.getInputByName("password").getAsText();
        UserTestfield user = new UserTestfield();
        user.setUserNick(userInput.getValue(String.class));
        user.setPassword(userPassword.getValue(String.class));
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
            
            request.getSession().setAttribute("user", user.getUserNick());
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
