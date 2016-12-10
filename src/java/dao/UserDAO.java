
package dao;

import java.util.List;
import model.bean.user.UserInfo;
import model.bean.user.UserTestfield;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import util.exceptions.BeanException;
import util.exceptions.DAOException;
import util.exceptions.ServiceException;

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
                    + "where userT.userNick = :user and userT.password like :password ";
            
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
    
    public UserTestfield login(UserTestfield user) throws DAOException {
        try {
            Criteria criteria = session.createCriteria(UserTestfield.class);
            criteria
                    .add(Restrictions.eq("userNick", user.getUserNick()))
                    .add(Restrictions.eq("password", user.getPassword()));
            List<UserTestfield> users = criteria.list();
            int size = users.size();
            if (size == 1) {
                user = users.get(0);
            } else if (size > 1) {
                throw new DAOException("User duplicated.");
            } else {
                user = null;
            }
        } catch(HibernateException | DAOException e) {
            throw new DAOException(e);
        }
        return user;
    }
    
    public UserTestfield createUser(String userNick, String password, String email) throws DAOException {
        UserTestfield user = null;
        
        try {
            user = new UserTestfield();
            user.setUserNick(userNick);
            user.setPassword(password);
            session.save(user);
            
            UserInfoDAO infoDao = new UserInfoDAO(session);
            UserInfo userInfo = infoDao.create(userNick, email);
            session.save(userInfo);
        } catch(BeanException | DAOException e) {
            throw new DAOException(e);
        }
        
        return user;
    }
    
}
