package dao;

import java.util.List;
import model.bean.style.FontFamily;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class FontFamilyDAO extends DAO {
    
    public FontFamilyDAO(Session session) {
        super(session);
    }
    
    public List<FontFamily> getAllFontFamilies() throws DAOException {
        List<FontFamily> fontFamilies;
        try {
            fontFamilies = session.createCriteria(FontFamily.class).list();
        } catch (HibernateException e) {
            throw new DAOException("Couldn't get all font families.", e);
        }
        return fontFamilies;
    }
    
}
