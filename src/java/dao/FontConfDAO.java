/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.bean.style.FontConf;
import org.hibernate.Session;
import util.enums.FontStyle;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class FontConfDAO extends DAO {
    
    public FontConfDAO(Session session) {
        super(session);
    }
    
    public FontConf insert(FontStyle type) throws DAOException {
        return insert(type, null);
    }
    
    public FontConf insert(FontStyle type, String cssStyle) throws DAOException {
        FontConf fontConf;
        try {
            fontConf = new FontConf();
            fontConf.setType(type);
            if (cssStyle != null) {
                fontConf.setCssStyle(cssStyle);
            }
            session.saveOrUpdate(fontConf);
        } catch (Exception e) {
            throw new DAOException("Couldn't insert or update a FontConf.", e);
        }
        return fontConf;
    }
    
    public FontConf update(FontConf fontConf) throws DAOException {
        try {
            session.update(fontConf);
        } catch (Exception e) {
            throw new DAOException("Couldn't update Font Conf.", e);
        }
        return fontConf;
    }
    
    public FontConf updateCssStyle(int id, String cssStyle) throws DAOException {
        FontConf fontConf = null;
        try {
            fontConf = (FontConf) session.get(FontConf.class, id);
            fontConf.setCssStyle(cssStyle);
            session.update(fontConf);
        } catch (Exception e) {
            throw new DAOException("Couldn't update Font's style.", e);
        }
        return fontConf;
    }
    
}
