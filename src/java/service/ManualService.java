package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dao.FontFamilyDAO;
import dao.ManualBlockDAO;
import dao.ManualConfDAO;
import dao.ManualDAO;
import dao.ManualPageDAO;
import dao.ManualRowDAO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.bean.manual.Manual;
import model.bean.manual.ManualBlock;
import model.bean.manual.ManualConf;
import model.bean.manual.ManualPage;
import model.bean.manual.ManualRow;
import model.bean.manual.pojo.ManualStylePojo;
import model.bean.style.FontColor;
import model.bean.style.FontFamily;
import model.bean.widthtype.WidthTypeHelper;
import util.ErrorMsgs;
import util.Security;
import util.ServiceReturn;
import util.enums.BlockWidthTypeEnum;
import util.enums.DAOList;
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
            
            Manual manual = MANAGER.getManualDAO()
                    .insert(userNick, "Temporal title", ManualVisibility.HIDDEN, ManualState.PROGRESS);
            int idManual = manual.getId();
            ManualPage manualPage = MANAGER.getManualPageDAO().insert(idManual, 1);
            ManualRow manualRow = MANAGER.getManualRowDAO().insert(manualPage.getId(), 1);
            
            List<WidthTypeHelper> widthTypes = new ArrayList<>();
            widthTypes.add(new WidthTypeHelper(BlockWidthTypeEnum.MD, 12));
            
            MANAGER.getManualBlockDAO().insert(manualRow.getId(), "Write something new here!", 1, widthTypes);
            result.addItem("manual", manual, true);
            
            MANAGER.getManualConfDAO().createDefaultForManual(idManual);
            
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
            manualDAO.update(manual);
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
    
    public ServiceReturn getManualTitle(int manulaId) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            ManualDAO manualDao = MANAGER.getManualDAO();
            String title = manualDao.getTitle(manulaId);
            MANAGER.commit();
            result.addItem("title", title);
        } catch (DAOException e) {
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
            
            if (widthTypes.isEmpty()) {
                throw new ServiceException(ErrorMsgs.SIZE_REQ);
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
            
            if (widthTypes.isEmpty()) {
                throw new ServiceException(ErrorMsgs.SIZE_REQ);
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
            MANAGER.rollback();
            throw e;
        } catch(Exception e) {
            MANAGER.rollback();
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
            MANAGER.rollback();
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
            MANAGER.rollback();
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
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn updateManualsStyle(String userNick, 
            ManualStylePojo stylePojo) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().getManual(stylePojo.manualId);
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            ManualConfDAO manualConfDao = 
                    (ManualConfDAO) MANAGER.getDAO(DAOList.MANUAL_CONF);
            FontFamilyDAO fontFamilyDao = 
                    (FontFamilyDAO) MANAGER.getDAO(DAOList.FONT_FAMILY);
            ManualConf manualConf = manual.getManualConf();
            FontColor fontColor = manualConf.getFontColor();
            fontColor.setRGB(stylePojo);
            FontFamily fontFamily = fontFamilyDao.getFontFamily(stylePojo.fontFamily);
            if (fontFamily == null) {
                throw new ServiceException("Font family doesn't exist.");
            }
            manualConf.setFontFamily(fontFamily);
            
            manualConf.setFontColor(fontColor);
            manualConfDao.update(manualConf);
            
            result.addItem("manual", manual, true);
            MANAGER.commit();
        } catch (DAOException | ServiceException e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn createJsonFileFromManual(String userNick, 
            int manualId) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            Manual manual = MANAGER.getManualDAO().getManual(manualId);
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonManual = mapper.createObjectNode();
            jsonManual.put("title", manual.getTitle());
            jsonManual.put("dateCreation", new SimpleDateFormat("dd-MM-yyyy")
                .format(manual.getDateCreation()));
            
            ArrayNode pages = jsonManual.putArray("pages");
            manual.getPages().forEach((manualPage) -> {
                ObjectNode page = mapper.createObjectNode();
                page.put("pageOrder", manualPage.getPageOrder());
                ArrayNode rows = page.putArray("rows");
                manualPage.getRows().forEach((manualRow) -> {
                    ObjectNode row = mapper.createObjectNode();
                    row.put("rowOrder", manualRow.getRowOrder());
                    ArrayNode blocks = row.putArray("blocks");
                    manualRow.getBlocks().forEach((manualBlock) -> {
                        ObjectNode block = mapper.createObjectNode();
                        block.put("blockOrder", manualBlock.getBlockOrder());
                        block.put("content", manualBlock.getContent());
                        blocks.add(block);
                    });
                    rows.add(row);
                });
                pages.add(page);
            });
            
            String sJsonManual = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonManual);
            
            result.addItem("jsonManual", sJsonManual);
        } catch (DAOException | ServiceException e) {
            throw treatException(e);
        }
        return result;
    }
    
}
