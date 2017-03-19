/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.bean.user.UserInfo;
import org.hibernate.Session;
import util.exceptions.BeanException;
import util.exceptions.DAOException;

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
