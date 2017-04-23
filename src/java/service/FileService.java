package service;

import dao.FileDAO;
import model.bean.file.File;
import model.bean.manual.Manual;
import util.ErrorMsgs;
import util.Security;
import util.ServiceReturn;
import util.enums.DAOList;
import util.exceptions.DAOException;
import util.exceptions.ServiceException;

/**
 *
 * @author Andriy
 */
public class FileService extends Service {
    
    public ServiceReturn insertManualFile(String userNick, int manualId, 
            String path) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().getManual(manualId);
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            File file = ((FileDAO) MANAGER.getDAO(DAOList.FILE))
                    .insertFile(userNick, manualId, path);
            result.addItem("file", file);
            
            MANAGER.commit();
        } catch (DAOException | ServiceException e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
}
