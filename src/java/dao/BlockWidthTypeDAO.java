/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.bean.BlockWidthType;
import model.bean.BlockWidthTypeId;
import org.hibernate.Session;
import util.enums.BlockWidthTypeEnum;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class BlockWidthTypeDAO {
    
    private final Session session;
    
    public BlockWidthTypeDAO(Session session) {
        this.session = session;
    }
    
    public BlockWidthType insert(BlockWidthTypeEnum type, String idManual) throws DAOException {
        BlockWidthType blockWidthType;
        try {
            BlockWidthTypeId id = new BlockWidthTypeId();
            id.setWidthType(type.getValue());
            id.setManualBlock(idManual);
            
            blockWidthType = new BlockWidthType();
            blockWidthType.setId(id);
            
            session.save(blockWidthType);
        } catch(Exception e) {
            throw new DAOException(e);
        }
        return blockWidthType;
    }
     
}
