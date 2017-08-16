package com.testfield.dao;

import com.testfield.model.bean.user.UserInfo;
import com.testfield.util.exceptions.BeanException;
import com.testfield.util.exceptions.DAOException;
import org.hibernate.Session;

/**
 *
 * @author Andriy
 */
public class UserInfoDAO extends DAO {
    
    public UserInfoDAO (Session session) {
        super(session);
    }
    
    public UserInfo create(String userNick, String email) throws DAOException {
        UserInfo userInfo = null;
        
        try {
            userInfo = new UserInfo();
            userInfo.setUserNick(userNick);
            userInfo.setEmail(email);
            session.save(userInfo);
        } catch(BeanException e) {
            throw new DAOException(e);
        }
        
        return userInfo;
    }
    
}
