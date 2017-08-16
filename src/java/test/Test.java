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
                    + "from RelMenuMenuList "
                    + "";
            Query query = session.createQuery(hql);
            System.out.println(query.list().size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    } 

}
