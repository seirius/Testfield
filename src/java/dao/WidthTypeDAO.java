/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.bean.widthtype.WidthType;
import org.hibernate.Session;
import util.DAOValidator;
import util.enums.BlockWidthTypeEnum;
import util.exceptions.DAOException;

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
