/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
    
     
}
