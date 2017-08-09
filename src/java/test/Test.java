package test;

import hibernate.HibernateUtil;
import model.bean.manual.Manual;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 *
 * @author Andriy
 */
public class Test {
    

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = ""
                    + "from UserTestfield "
                    + "where userNick = :user"
                    + "";
            Query query = session.createQuery(hql);
            query.setString("user", "andriy");
            System.out.println(query.list().get(0));
            
            hql = ""
                    + "from Manual "
                    + "where id = :id"
                    + "";
            query = session.createQuery(hql);
            query.setInteger("id", 69);
            System.out.println(query.list().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    } 

}
