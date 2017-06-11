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
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            String hql = ""
                    + "from Manual "
                    + "where id = :id"
                    + "";
            Query query = session.createQuery(hql);
            query.setInteger("id", 69);
            Manual manual = (Manual) query.list().get(0);
            System.out.println(manual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

}
