package dao;

import model.bean.style.FontColor;
import org.hibernate.Session;

/**
 *
 * @author Andriy
 */
public class FontColorDAO extends DAO {
    
    public FontColorDAO(Session session) {
        super(session);
    }
    
    public FontColor insert(FontColor fontColor) {
        session.save(fontColor);
        return fontColor;
    }
    
}
