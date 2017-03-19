/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.bean.manual.Manual;
import model.bean.manual.ManualConf;
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
    
    public ManualConf insertOrUpdate(int manualId) throws BeanException, DAOException {
        return insertOrUpdate(manualId, null);
    }
    
    public ManualConf insertOrUpdate(ManualConf manualConf) throws DAOException {
        try {
            session.saveOrUpdate(manualConf);
        } catch (Exception e) {
            throw new DAOException("Error while creating or updating the manual's configuration.", e);
        }
        return manualConf;
    }
    
    public ManualConf insertOrUpdate(int manualId, String manualBackground) throws BeanException, DAOException {
        ManualConf manualConf = null;
        try {
            manualConf = new ManualConf();
            manualConf.setManualId(manualId);
            if (manualBackground != null) {
                manualConf.setManualBackground(manualBackground);
            }
            FontConfDAO fontConfDao = new FontConfDAO(session);
            manualConf.setFontColor(fontConfDao.insertOrUpdate(FontStyle.COLOR, "#333"));
            manualConf.setFontFamily(fontConfDao.insertOrUpdate(FontStyle.FAMILY, "\"Orbitron\", sans-serif"));
            insertOrUpdate(manualConf);
        } catch (BeanException e) {
            throw (BeanException) e;
        }
        return manualConf;
    }
    
}
