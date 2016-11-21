/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.bean.WidthType;
import org.hibernate.Session;
import util.enums.BlockWidthTypeEnum;
import util.exceptions.BeanException;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class WidthTypeDAO {
    
    private final Session session;
    
    public WidthTypeDAO(Session session) {
        this.session = session;
    }
    
    public WidthType insert(BlockWidthTypeEnum type) throws DAOException {
        WidthType widthType;
        try {
            widthType = new WidthType();
            widthType.setWidthType(type.getValue());
            widthType.setDescription(type.getText());
            session.save(widthType);
        } catch(BeanException e) {
            throw new DAOException(e);
        }
        return widthType;
    }
    
}
