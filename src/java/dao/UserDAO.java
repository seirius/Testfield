
package dao;

import java.util.List;
import model.bean.UserTestfield;
import org.hibernate.Query;
import org.hibernate.Session;
import util.exceptions.DAOException;

/**
 * @author Andriy Yednarovych
 */
public class UserDAO {

    private final Session session;
    
    public UserDAO (Session session) {
        this.session = session;
    }
    
    public UserTestfield getUser(String userId) throws DAOException {
        UserTestfield user = null;
        
        try {
            user = (UserTestfield) session.get(UserTestfield.class, userId);
        } catch(Exception e) {
            throw new DAOException("Couldn't retrieve User.", e);
        }
        
        return user;
    }
    
    public UserTestfield saveUser(UserTestfield user) throws DAOException {
        
        try {
            session.save(user);
        } catch(Exception e) {
            throw new DAOException(e);
        }
        
        return user;
    }
    
    
    public UserTestfield login(String userId, String password) throws Exception {
        UserTestfield user = null;
        
        try {
            String hql = ""
                    + "from UserTestfield userT "
                    + "where userT.userNick = :user and userT.password = :password ";
            
            Query query = session.createQuery(hql);
            query.setString("user", userId);
            query.setString("password", password);
            
            List<UserTestfield> list = query.list();
            if (list.size() > 0) {
                user = list.get(0);
            }
            
            
        } catch(Exception e) {
            throw new DAOException(e);
        }
        
        return user;
    }
    
}
