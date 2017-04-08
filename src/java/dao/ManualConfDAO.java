/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.bean.manual.ManualConf;
import model.bean.style.FontConf;
import model.bean.style.FontFamily;
import org.hibernate.Session;
import util.enums.FontStyle;
import util.exceptions.BeanException;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class ManualConfDAO extends DAO {
    
    public ManualConfDAO(Session session) {
        super(session);
    }
    
    public ManualConf insertOrUpdate(ManualConf manualConf) throws DAOException {
        session.saveOrUpdate(manualConf);
        return manualConf;
    }
    
    public ManualConf createDefaultForManual(int manualId) throws DAOException, BeanException {
        if (getManualConf(manualId) != null) {
            throw new DAOException("There is already a ManualConf created.");
        }
        ManualConf manualConf = new ManualConf();
        manualConf.setManualId(manualId);
        FontConfDAO fontConfDao = new FontConfDAO(session);
        FontConf fontConfColor = fontConfDao.insert(FontStyle.COLOR, "rgb(0, 0, 0)");
        FontFamilyDAO fontFamilyDao = new FontFamilyDAO(session);
        FontFamily fontFamily = fontFamilyDao.getFirstFontFamily();
        String cssStyle = "";
        if (fontFamily != null) {
            cssStyle = fontFamily.getCssStyle();
        }
        FontConf fontConfFamily = fontConfDao.insert(FontStyle.FAMILY, cssStyle);
        manualConf.setFontColor(fontConfColor);
        manualConf.setFontFamily(fontConfFamily);
        session.save(manualConf);
        return manualConf;
    }
    
    public ManualConf update(ManualConf manualConf) {
        session.update(manualConf);
        return manualConf;
    }
    
    public ManualConf getManualConf(int manualId) {
        return (ManualConf) session.get(ManualConf.class, manualId);
    }
    
}
