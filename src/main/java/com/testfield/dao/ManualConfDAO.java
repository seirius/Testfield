package com.testfield.dao;

import com.testfield.model.bean.manual.ManualConf;
import com.testfield.model.bean.style.FontColor;
import com.testfield.model.bean.style.FontFamily;
import com.testfield.util.exceptions.BeanException;
import com.testfield.util.exceptions.DAOException;
import org.hibernate.Session;

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
        FontColorDAO fontColorDao = new FontColorDAO(session);
        FontColor fontColor = new FontColor();
        fontColor.setR(0);
        fontColor.setG(0);
        fontColor.setB(0);
        fontColorDao.insert(fontColor);
        FontFamilyDAO fontFamilyDao = new FontFamilyDAO(session);
        FontFamily fontFamily = fontFamilyDao.getFirstFontFamily();
        if (fontFamily == null) {
            throw new DAOException("No font families defined to create a manual.");
        }
        manualConf.setFontColor(fontColor);
        manualConf.setFontFamily(fontFamily);
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
