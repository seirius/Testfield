package dao;

import java.util.List;
import model.bean.style.FontFamily;
import org.hibernate.Criteria;
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
    
    public FontFamily getFirstFontFamily() throws DAOException {
        FontFamily fontFamily = null;
        try {
            Criteria criteria = session.createCriteria(FontFamily.class)
                    .setMaxResults(1);
            List<FontFamily> fontFamilies = criteria.list();
            if (fontFamilies.size() > 0) {
                fontFamily = fontFamilies.get(0);
            }
        } catch (HibernateException e) {
            throw new DAOException("Couldn't get font family.", e);
        }
        return fontFamily;
    }
    
    public FontFamily getFontFamily(int id) throws DAOException {
        FontFamily fontFamily;
        try {
            fontFamily = (FontFamily) session.get(FontFamily.class, id);
        } catch (Exception e) {
            throw new DAOException("Couldn't get font family.", e);
        }
        return fontFamily;
    }
    
}
