/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ManualDAO;
import java.util.List;
import model.bean.Manual;
import model.bean.ManualBlock;
import model.bean.ManualPage;
import model.bean.ManualRow;
import util.Security;
import util.ServiceReturn;
import util.enums.BlockWidthTypeEnum;
import util.enums.ManualState;
import util.enums.ManualVisibility;
import util.exceptions.DAOException;
import util.exceptions.ServiceException;

/**
 *
 * @author Andriy
 */
public class ManualService extends Service {
    
    /**
     * Creates MANUAL, MANUAL_PAGE, MANUAL_ROW and MANUAL_BLOCK
     * 
     * @param userNick
     * @return Manual's ID
     * @throws Exception 
     */
    public ServiceReturn createManual(String userNick) throws Exception {
        ServiceReturn result = new ServiceReturn();
        
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().insert(userNick, "Temporal title", ManualVisibility.HIDDEN, ManualState.PROGRESS);
            String idManual = manual.getId();
            ManualPage manualPage = MANAGER.getManualPageDAO().insert(idManual, 1);
            ManualRow manualRow = MANAGER.getManualRowDAO().insert(manualPage.getId(), 1);
            ManualBlock manualBlock = MANAGER.getManualBlockDAO().insert(manualRow.getId(), "", 1, 12);
            MANAGER.getBlockWidthTypeDAO().insert(BlockWidthTypeEnum.MD, manualBlock.getId());
            result.addItem("manual", manual);
            
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        
        return result;
    }
    
    public ServiceReturn loadManual(String idManual) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().getManual(idManual);
            result.addItem("manual", manual);
            
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback(); 
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn loadManuals(String userNick) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            List<Manual> manuals = MANAGER.getManualDAO().getManuals(userNick);
            result.addItem("manuals", manuals);
            
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback(); 
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn setManualTitle(String userNick, String manualId, String newTitle) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            ManualDAO manualDAO = MANAGER.getManualDAO();
            Manual manual = manualDAO.getManual(manualId);
            String manualUser = manual.getUserNick();
            if (!manualUser.equals(userNick)) {
                throw new ServiceException("Access denied.");
            }
            manual.setTitle(newTitle);
            
            MANAGER.commit();
            
            result.addItem("manual", manual);
        } catch(DAOException e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn saveManualBlock(String userNick, ManualBlock manualBlock) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            manualBlock = MANAGER.getManualBlockDAO().updateBlock(manualBlock);
            Manual manual = manualBlock.getManualRow().getManualPage().getManual();
            if (Security.permissionModManual(manual, userNick)) {
                throw new ServiceException("Access denied.");
            }
            
            MANAGER.commit();
            result.addItem("manual", manual);
        } catch(DAOException | ServiceException e) {
            MANAGER.rollback();
            throw treatException(e);
        }
        return result;
    }
    
}
