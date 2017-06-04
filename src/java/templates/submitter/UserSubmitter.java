package templates.submitter;

import dao.UserDAO;
import model.bean.user.UserTestfield;
import templates.forms.Form;
import util.ServiceManager;
import util.ServiceReturn;
import util.enums.DAOList;
import util.exceptions.ServiceException;

/**
 *
 * @author Andriy
 */
public class UserSubmitter {
    
    public ServiceReturn login(Form form) throws SubmitterException {
        ServiceReturn result = new ServiceReturn();
        
        try {
            ServiceManager manager = form.getManager();
            
            String userNick = form.getInputByName("user").getValue(String.class);
            String psw = form.getInputByName("password").getValue(String.class);
            
            UserTestfield user = new UserTestfield();
            user.setUserNick(userNick);
            user.setPassword(psw);
            
            UserDAO userDAO = manager.getUserDAO();
            user = userDAO.login(user);
            if (user == null) {
                throw new ServiceException("User or Password incorrect.");
            }
            
            form.getRequest().getSession().setAttribute("user", user.getUserNick());
        } catch(Exception e) {
            throw new SubmitterException("Couldn't login, try again later.", e);
        }
        
        return result;
    }
    
    public ServiceReturn register(Form form) throws SubmitterException {
        ServiceReturn result = new ServiceReturn();
        try {
            String userNick = form.getInputByName("user").getValue(String.class);
            String psw = form.getInputByName("password").getValue(String.class);
            String email = form.getInputByName("password").getValue(String.class);
            
            UserDAO userDao = (UserDAO) form.getManager().getDAO(DAOList.USER);
            userDao.createUser(userNick, psw, email);
            result.addItem("user", userNick);
        } catch (Exception e) {
            throw new SubmitterException("An error has ocurred creating the user, try again later.", e);
        }
        return result;
    }
    
}
