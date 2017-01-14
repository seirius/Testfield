/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ManualBlockDAO;
import dao.ManualDAO;
import dao.ManualPageDAO;
import dao.ManualRowDAO;
import java.util.ArrayList;
import java.util.List;
import model.bean.manual.Manual;
import model.bean.manual.ManualBlock;
import model.bean.manual.ManualPage;
import model.bean.manual.ManualRow;
import model.bean.widthtype.WidthTypeHelper;
import util.ErrorMsgs;
import util.Security;
import util.ServiceReturn;
import util.enums.BlockWidthTypeEnum;
import util.enums.DeleteOptions;
import util.enums.ManualState;
import util.enums.ManualVisibility;
import util.enums.MoveOptions;
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
            int idManual = manual.getId();
            ManualPage manualPage = MANAGER.getManualPageDAO().insert(idManual, 1);
            ManualRow manualRow = MANAGER.getManualRowDAO().insert(manualPage.getId(), 1);
//            List<BlockWidthTypeEnum> widthTypes = new ArrayList<>();
//            widthTypes.add(BlockWidthTypeEnum.MD);
            
            List<WidthTypeHelper> widthTypes = new ArrayList<>();
            widthTypes.add(new WidthTypeHelper(BlockWidthTypeEnum.MD, 12));
            
            MANAGER.getManualBlockDAO().insert(manualRow.getId(), "Write something new here!", 1, widthTypes);
            result.addItem("manual", manual, true);
            
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        
        return result;
    }
    
    public ServiceReturn loadManual(int idManual) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().getManual(idManual);
            result.addItem("manual", manual, true);
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
            
            MANAGER.commitClose();
            result.addItem("manuals", manuals);
            manuals.forEach((manual) -> {
                manual.setPages(null);
                manual.setTags(null);
            });
        } catch(Exception e) {
            MANAGER.rollback(); 
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn setManualTitle(String userNick, int manualId, String newTitle) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            ManualDAO manualDAO = MANAGER.getManualDAO();
            Manual manual = manualDAO.getManual(manualId);
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            manual.setTitle(newTitle);
            
            MANAGER.commit();
            
            result.addItem("manual", manual, true);
        } catch(DAOException e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn updateBlockContent(String userNick, String idBlock, String content) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().getManualByBlock(idBlock);
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            ManualBlockDAO blockDao = MANAGER.getManualBlockDAO();
            ManualBlock block = blockDao.getBlock(idBlock);
            block.setContent(content);
            blockDao.updateBlock(block);
            
            result.addItem("manual", manual, true);
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn updateBlockSize(String userNick, String idBlock, List<WidthTypeHelper> widthTypes) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            ManualDAO manualDAO = MANAGER.getManualDAO();
            Manual manual = manualDAO.getManualByBlock(idBlock);
            
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            MANAGER.getManualBlockDAO().modifyWidthTypes(idBlock, widthTypes);
            
            result.addItem("manual", manual, true);
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn addPage(String userNick, int idManual, int pageOrder) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            ManualDAO manualDAO = MANAGER.getManualDAO();
            Manual manual = manualDAO.getManual(idManual);
            
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            ManualPageDAO pageDAO = MANAGER.getManualPageDAO();
            if (pageOrder == 0) {
                pageDAO.insertLast(idManual);
            } else {
                pageDAO.insertPlusOne(idManual, pageOrder);
            }
            result.addItem("manual", manual, true);
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn addRow(String userNick, String idPage, int rowOrder) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            ManualDAO manualDAO = MANAGER.getManualDAO();
            Manual manual = manualDAO.getManualByPage(idPage);
            
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            ManualRowDAO rowDAO = MANAGER.getManualRowDAO();
            if (rowOrder == 0) {
                rowDAO.insertLast(idPage);
            } else {
                rowDAO.insertPlusOne(idPage, rowOrder);
            }
            result.addItem("manual", manual, true);
            
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn addBlock(String userNick, String idRow, String content, int blockOrder, List<WidthTypeHelper> widthTypes) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            ManualDAO manualDAO = MANAGER.getManualDAO();
            Manual manual = manualDAO.getManualByRow(idRow);
            
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            ManualBlockDAO blockDAO = MANAGER.getManualBlockDAO();
            if (blockOrder == 0) {
                blockDAO.insertLast(idRow, content, widthTypes);
            } else {
                blockDAO.insertPlusOne(idRow, content, blockOrder, widthTypes);
            }
            
            result.addItem("manual", manual, true);
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn getWidthTypes() throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            result.addItem("widthTypes", MANAGER.getWidthTypeDAO().getWidthTypes());
            
            MANAGER.commit();
        } catch(Exception e) {
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn deleteBlock(String userNick, String idBlock) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().getManualByBlock(idBlock);
            
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            MANAGER.getManualBlockDAO().delete(idBlock);
            result.addItem("manual", manual, true);
            
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn deleteRow(String userNick, String idRow) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().getManualByRow(idRow);
            
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            MANAGER.getManualRowDAO().delete(idRow);
            result.addItem("manual", manual, true);
            
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn deletePage(String userNick, String idPage) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().getManualByPage(idPage);
            
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            MANAGER.getManualPageDAO().delete(idPage);
            result.addItem("manual", manual, true);
            
            MANAGER.commit();
        } catch(Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn deleteOptions(String userNick, DeleteOptions deleteOption, String id) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            switch(deleteOption) {
                
                case BLOCK:
                    result = deleteBlock(userNick, id);
                    break;
                    
                case ROW:
                    result = deleteRow(userNick, id);
                    break;
                    
                case PAGE:
                    result = deletePage(userNick, id);
                    break;
                    
            }
        } catch(ServiceException e) {
            throw e;
        } catch(Exception e) {
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn moveBlock(String userNick, MoveOptions moveOption, String idBlock) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().getManualByBlock(idBlock);
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            switch(moveOption) {
                case LEFT:
                    MANAGER.getManualBlockDAO().moveBackward(idBlock);
                    break;
                    
                case RIGHT:
                    MANAGER.getManualBlockDAO().moveFoward(idBlock);
                    break;
            }
            
            result.addItem("manual", manual, true);
            
            MANAGER.commit();
        } catch(Exception e) {
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn movePage(String userNick, MoveOptions moveOption, String idPage) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().getManualByPage(idPage);
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            switch(moveOption) {
                case UP:
                    MANAGER.getManualPageDAO().moveBackward(idPage);
                    break;
                    
                case DOWN:
                    MANAGER.getManualPageDAO().moveFoward(idPage);
                    break;
            }
            
            result.addItem("manual", manual, true);
            MANAGER.commit();
        } catch(Exception e) {
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn moveRow(String userNick, MoveOptions moveOption, String idRow) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().getManualByRow(idRow);
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            switch(moveOption) {
                case UP:
                    MANAGER.getManualRowDAO().moveBackward(idRow);
                    break;
                    
                case DOWN:
                    MANAGER.getManualRowDAO().moveFoward(idRow);
                    break;
            }
            
            result.addItem("manual", manual, true);
            MANAGER.commit();
        } catch(Exception e) {
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
}
