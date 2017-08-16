package com.testfield.service;

import com.testfield.dao.FontFamilyDAO;
import com.testfield.model.bean.style.FontFamily;
import com.testfield.util.ServiceReturn;
import com.testfield.util.enums.DAOList;
import com.testfield.util.exceptions.DAOException;
import java.util.List;

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
