/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.FontFamilyDAO;
import java.util.List;
import model.bean.style.FontFamily;
import util.ServiceReturn;
import util.enums.DAOList;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class StyleService extends Service {
    
    public ServiceReturn getAllFontFamilies() throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            FontFamilyDAO fontFamilyDao = (FontFamilyDAO) MANAGER.getDAO(DAOList.FONT_FAMILY);
            List<FontFamily> fontFamilies = fontFamilyDao.getAllFontFamilies();
            result.addItem("fontFamilies", fontFamilies);
            
            MANAGER.commit();
        } catch (DAOException e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
}
