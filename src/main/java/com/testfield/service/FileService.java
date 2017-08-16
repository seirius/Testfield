package com.testfield.service;

import com.testfield.dao.FileDAO;
import com.testfield.model.bean.file.File;
import com.testfield.model.bean.manual.Manual;
import com.testfield.util.ErrorMsgs;
import com.testfield.util.Security;
import com.testfield.util.ServiceReturn;
import com.testfield.util.enums.DAOList;
import com.testfield.util.enums.FilePath;
import com.testfield.util.exceptions.DAOException;
import com.testfield.util.exceptions.ServiceException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

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
