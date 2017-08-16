package com.testfield.dao;

import com.testfield.model.bean.widthtype.WidthType;
import com.testfield.util.DAOValidator;
import com.testfield.util.enums.BlockWidthTypeEnum;
import com.testfield.util.exceptions.DAOException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Andriy
 */
public class WidthTypeDAO extends DAO {
    
    public WidthTypeDAO(Session session) {
        super(session);
    }
    
    public WidthType insert(BlockWidthTypeEnum type) throws DAOException {
        WidthType widthType;
        try {
            widthType = new WidthType();
            widthType.setWidthType(type.getValue());
            widthType.setDescription(type.getText());
            session.save(widthType);
        } catch(Exception e) {
            throw new DAOException(e);
        }
        return widthType;
    }
    
    public List<WidthType> getWidthTypes() throws DAOException {
        List<WidthType> widthTypes = null;
        try {
            widthTypes = session.createCriteria(WidthType.class).list();
        } catch(Exception e) {
            DAOValidator.errorOnSelect("Width Types", e);
        }
        return widthTypes;
    }
    
}
