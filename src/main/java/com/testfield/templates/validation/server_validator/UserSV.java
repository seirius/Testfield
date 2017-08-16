package com.testfield.templates.validation.server_validator;

import com.testfield.dao.UserDAO;
import com.testfield.model.bean.user.UserTestfield;
import com.testfield.service.UserService;
import com.testfield.templates.forms.inputs.Input;
import com.testfield.util.ErrorMsgs;
import com.testfield.util.ServiceReturn;

/**
 *
 * @author Andriy
 */
public class UserSV extends ServerValidator {
    
    public ServiceReturn userDuplicate(Input input) throws SVException {
        ServiceReturn result = new ServiceReturn();
        try {
            String userNick = input.getValue(String.class);
            UserTestfield userTf = new UserService().getUser(userNick);
            result.addItem("ok", userTf == null);
        } catch (Exception e) {
            ErrorMsgs.sysLogThis(e);
            throw new SVException("Error validating user's nick.", e);
        }
        
        return result;
    }
    
    public ServiceReturn emailDuplicate(Input input) throws SVException {
        ServiceReturn result = new ServiceReturn();
        try {
            String userEmail = input.getValue(String.class);
            UserDAO user = manager.getDAO(UserDAO.class);
            result.addItem("ok", user.getUserByEmail(userEmail) == null);
        } catch (Exception e) {
            ErrorMsgs.sysLogThis(e);
            throw new SVException("Error validating user's email", e);
        }
        return result;
    }
    
}
