/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.bean.ManualBlock;
import model.bean.ManualRow;
import org.hibernate.Session;
import util.DAOValidator;
import util.exceptions.BeanException;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class ManualBlockDAO {
    
    private final Session session;
    
    public ManualBlockDAO(Session session) {
        this.session = session;
    }
    
    public ManualBlock insert(String manualRow, String content, int order, int width) throws DAOException {
        ManualBlock manualBlock = null;
        try {
            ManualRow beanManualRow = new ManualRow();
            beanManualRow.setId(manualRow);
            
            manualBlock = new ManualBlock();
            manualBlock.setManualRow(beanManualRow);
            manualBlock.setContent(content);
            manualBlock.setOrder(order);
            manualBlock.setWidth(width);
            session.save(manualBlock);
        } catch(Exception e) {
            DAOValidator.errorOnInsert("Manual's block", e);
        }
        return manualBlock;
    }
    
    public ManualBlock updateBlock(String idBlock, String content, int order, int width) throws DAOException {
        ManualBlock manualBlock = new ManualBlock();
        try {
            manualBlock.setId(idBlock);
            manualBlock.setContent(content);
            manualBlock.setOrder(order);
            manualBlock.setWidth(width);
        } catch(BeanException e) {
            throw new DAOException(e);
        }
        return updateBlock(manualBlock);
    }
    
    public ManualBlock updateBlock(ManualBlock manualBlock) throws DAOException {
        try {
            session.update(manualBlock);
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Manual's block", e);
        }
        return manualBlock;
    }
    
}
