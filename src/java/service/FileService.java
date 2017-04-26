package service;

import dao.FileDAO;
import java.util.List;
import model.bean.file.File;
import model.bean.manual.Manual;
import org.springframework.web.multipart.MultipartFile;
import util.ErrorMsgs;
import util.Security;
import util.ServiceReturn;
import util.enums.DAOList;
import util.enums.FilePath;
import util.exceptions.DAOException;
import util.exceptions.ServiceException;

/**
 *
 * @author Andriy
 */
public class FileService extends Service {
    
    public ServiceReturn insertManualFiles(String userNick, int manualId, 
            String contextPath, FilePath filePath, MultipartFile[] sFiles) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            Manual manual = MANAGER.getManualDAO().getManual(manualId);
            if (!Security.permissionModManual(manual, userNick)) {
                throw new ServiceException(ErrorMsgs.ACC_DEN);
            }
            
            List<File> files = ((FileDAO) MANAGER.getDAO(DAOList.FILE))
                    .insertFiles(userNick, manualId, contextPath, filePath, sFiles);
            result.addItem("files", files);
            
            MANAGER.commit();
        } catch (DAOException | ServiceException e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn getUsersImages(String userNick) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            FileDAO fileDao = (FileDAO) MANAGER.getDAO(DAOList.FILE);
            List<File> files = fileDao.getUsersImages(userNick);
            result.addItem("files", files);
            MANAGER.commit();
        } catch (Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
}
