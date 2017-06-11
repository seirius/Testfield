package dao;

import java.util.ArrayList;
import java.util.List;
import model.bean.manual.ManualBlock;
import model.bean.widthtype.WidthType;
import model.bean.widthtype.WidthTypeHelper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import util.DAOValidator;
import util.ErrorMsgs;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class ManualBlockDAO extends DAO {
    
    public ManualBlockDAO(Session session) {
        super(session);
    }
    
    public ManualBlock insert(String manualRow, String content, int order, List<WidthTypeHelper> widthTypes) throws DAOException {
        ManualBlock manualBlock = null;
        try {
            manualBlock = new ManualBlock();
            manualBlock.setManualRow(manualRow);
            manualBlock.setContent(content);
            manualBlock.setBlockOrder(order);
            List<WidthType> auxTypes = new ArrayList<>();
            for (WidthTypeHelper widthType: widthTypes) {
                WidthType type = new WidthType();
                type.setBlockWidthType(widthType.getWidthType());
                auxTypes.add(type);
            }
            manualBlock.setWidthTypes(auxTypes);
            session.save(manualBlock);
            for (WidthTypeHelper widthType: widthTypes) {
                updateWidthTypeAmount(widthType.getWidthType().getValue(), manualBlock.getId(), widthType.getAmount());
            }
        } catch(Exception e) {
            DAOValidator.errorOnInsert("Manual's block", e);
        }
        return manualBlock;
    }
    
    public ManualBlock modifyWidthTypes(String idBlock, List<WidthTypeHelper> widthTypes) throws DAOException {
        ManualBlock manualBlock = null;
        try {
            manualBlock = getBlock(idBlock);
            if (manualBlock == null) {
                throw new DAOException(String.format("Manual Block with ID: {0}, doesn't exist.", idBlock));
            }
            List<WidthType> auxTypes = new ArrayList<>();
            for (WidthTypeHelper widthType: widthTypes) {
                WidthType type = new WidthType();
                type.setBlockWidthType(widthType.getWidthType());
                auxTypes.add(type);
            }
            manualBlock.setWidthTypes(auxTypes);
            session.update(manualBlock);
            for (WidthTypeHelper widthType: widthTypes) {
                updateWidthTypeAmount(widthType.getWidthType().getValue(), manualBlock.getId(), widthType.getAmount());
            }
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Manual's block", e);
        }
        return manualBlock;
    }
    
    public ManualBlock insertPlusOne(String manualRow, String content, int blockOrder, List<WidthTypeHelper> widthTypes) throws DAOException {
        ManualBlock manualBlock = null;
        try {
            plusOne(manualRow, blockOrder);
            manualBlock = insert(manualRow, content, blockOrder, widthTypes);
        } catch(Exception e) {
            DAOValidator.errorOnInsert("Manual's block", e);
        }
        return manualBlock;
    }
    
    public ManualBlock insertLast(String idRow, String content, List<WidthTypeHelper> widthTypes) throws DAOException {
        ManualBlock manualBlock = null;
        try {
            ManualBlock auxBlock = getLastBlock(idRow);
            int blockOrder = auxBlock == null ? 1 : auxBlock.getBlockOrder() + 1;
            manualBlock = insert(idRow, content, blockOrder, widthTypes);
        } catch(Exception e) {
            DAOValidator.errorOnInsert("Manual's block", e);
        }
        return manualBlock;
    }
    
    public int updateWidthTypeAmount(int widthType, String idBlock, int amount) throws DAOException {
        int updated = -1;
        try {
            String hql = ""
                    + "update RelBlockWidthType "
                    + "set "
                    + " amount = :amount "
                    + "where "
                    + " id.widthType = :widthType "
                    + "and "
                    + " id.manualBlock = :manualBlock "
                    + "";
            
            updated = session.createQuery(hql)
                    .setInteger("amount", amount)
                    .setInteger("widthType", widthType)
                    .setString("manualBlock", idBlock)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Manual's block", e);
        }
        return updated; 
    }
    
    public int plusOne(String idRow, int startPosition) throws DAOException {
        int updated = -1;
        try {
            String hql = ""
                    + "update ManualBlock "
                    + "set"
                    + " blockOrder = blockOrder + 1 "
                    + "where "
                    + " manualRow = :manualRow "
                    + "and "
                    + " blockOrder >= :blockOrder";
            
            updated = session.createQuery(hql)
                    .setString("manualRow", idRow)
                    .setInteger("blockOrder", startPosition)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Manual's block", e);
        }
        return updated;
    }
    
    public int plusOneWRange(String idRow, int startPosition, int endPosition) throws DAOException {
        int updated = -1;
        try {
            String hql = ""
                    + "update ManualBlock "
                    + "set "
                    + " blockOrder = blockOrder + 1 "
                    + "where "
                    + " manualRow = :manualRow "
                    + "and "
                    + " blockOrder >= :startPosition "
                    + "and "
                    + " blockOrder <= :endPosition "
                    + "";
            
            updated = session.createQuery(hql)
                    .setString("manualRow", idRow)
                    .setInteger("startPosition", startPosition)
                    .setInteger("endPosition", endPosition)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Manual's block", e);
        }
        return updated;
    }
    
    public int minusOneWRange(String idRow, int startPosition, int endPosition) throws DAOException {
        int updated = -1;
        try {
            String hql = ""
                    + "update ManualBlock "
                    + "set "
                    + " blockOrder = blockOrder - 1 "
                    + "where "
                    + " manualRow = :manualRow "
                    + "and "
                    + " blockOrder >= :startPosition "
                    + "and "
                    + " blockOrder <= :endPosition "
                    + "";
            
            updated = session.createQuery(hql)
                    .setString("manualRow", idRow)
                    .setInteger("startPosition", startPosition)
                    .setInteger("endPosition", endPosition)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Manual's blocks", e);
        }
        return updated;
    }
    
    public ManualBlock updateBlock(ManualBlock manualBlock) throws DAOException {
        try {
            session.update(manualBlock);
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Manual's block", e);
        }
        return manualBlock;
    }
    
    public ManualBlock getBlock(String idBlock) throws DAOException {
        ManualBlock manualBlock = null;
        try {
            manualBlock = (ManualBlock) session.get(ManualBlock.class, idBlock);
        } catch(Exception e) {
            DAOValidator.errorOnSelect("Manual's block", e);
        }
        return manualBlock;
    }
    
    public ManualBlock getLastBlock(String rowId) throws DAOException {
        ManualBlock manualBlock = null;
        try {
            DetachedCriteria rows = DetachedCriteria.forClass(ManualBlock.class)
                    .setProjection(Projections.max("blockOrder"))
                    .add(Restrictions.eq("manualRow", rowId));
            
            Criteria blocks = session.createCriteria(ManualBlock.class)
                    .add(Property.forName("blockOrder").eq(rows))
                    .add(Restrictions.eq("manualRow", rowId));
            
            List<ManualBlock> blockList = blocks.list();
            if (blockList.size() > 0) {
                manualBlock = blockList.get(0);
            }
        } catch(Exception e) {
            DAOValidator.errorOnSelect("Manual's block", e);
        }
        return manualBlock;
    }
    
    public int delete(String idBlock) throws DAOException {
        int delete = -1;
        try {
            String hql = "delete ManualBlock where id = :id";
            delete = session.createQuery(hql)
                    .setString("id", idBlock)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnDelete("Manual's block", e);
        }
        
        return delete;
    }
    
    public int deleteByRow(String idRow) throws DAOException {
        int deleted = -1;
        try {
            String hql = ""
                    + "delete ManualBlock "
                    + "where "
                    + " manualRow = :manualRow";
            
            deleted = session.createQuery(hql)
                    .setString("manualRow", idRow)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnDelete("Manual's blocks", e);
        }
        return deleted;
    }
    
    public int updateOrder(String idBlock, int blockOrder) throws DAOException {
        int updated = -1;
        try {
            String hql = ""
                    + "update ManualBlock "
                    + "set "
                    + " blockOrder = :blockOrder "
                    + "where "
                    + " id = :id "
                    + "";
            
            updated = session.createQuery(hql)
                    .setInteger("blockOrder", blockOrder)
                    .setString("id", idBlock)
                    .executeUpdate();
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Manual's block", e);
        }
        return updated; 
    }
    
    public void moveFoward(String idBlock) throws DAOException {
        try {
            if (isLast(idBlock)) {
                ErrorMsgs.sysLogInfo(("Can't move last block foward."));
                return;
            }
            
            ManualBlock block = getBlock(idBlock);
            int newOrder = block.getBlockOrder() + 1;
            minusOneWRange(block.getManualRow(), newOrder, newOrder);
            updateOrder(idBlock, newOrder);
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Manual's block", e);
        }
    }
    
    public void moveBackward(String idBlock) throws DAOException {
        try {
            if (isFirst(idBlock)) {
                ErrorMsgs.sysLogInfo("Can't move first block backwards.");
                return;
            }
            
            ManualBlock block = getBlock(idBlock);
            int newOrder = block.getBlockOrder() - 1;
            plusOneWRange(block.getManualRow(), newOrder, newOrder);
            updateOrder(idBlock, newOrder);
        } catch(Exception e) {
            DAOValidator.errorOnUpdate("Manual's block", e);
        }
    }
    
    public boolean isLast(String idBlock) throws DAOException {
        boolean isLast = false;
        try {
            ManualBlock block = getBlock(idBlock);
            ManualBlock lastBlock = getLastBlock(block.getManualRow());
            
            isLast = block.getBlockOrder() == lastBlock.getBlockOrder();
        } catch(Exception e) {
            DAOValidator.errorOnCheck("on checking if block is last", e);
        }
        return isLast;
    }
    
    public boolean isFirst(String idBlock) throws DAOException {
        boolean isFirst = false;
        try {
            isFirst = 1 == getBlock(idBlock).getBlockOrder();
        } catch(Exception e) {
            DAOValidator.errorOnCheck("on checking if block is first", e);
        }
        return isFirst;
    }
    
}
