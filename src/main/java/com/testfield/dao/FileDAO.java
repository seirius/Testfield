package com.testfield.dao;

import com.testfield.model.bean.file.File;
import com.testfield.model.bean.file.FileManual;
import com.testfield.model.bean.file.FileManualId;
import com.testfield.model.bean.file.FileUser;
import com.testfield.model.bean.file.FileUserId;
import com.testfield.util.DAOValidator;
import com.testfield.util.enums.FilePath;
import com.testfield.util.exceptions.BeanException;
import com.testfield.util.exceptions.DAOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Andriy
 */
public class FileDAO extends DAO {
    
    public FileDAO(Session session) {
        super(session);
    }
    
    /**
     * Inserts FILE, FILE_USER and FILE_MANUAL
     * 
     * @param user
     * @param manualId
     * @param contextPath
     * @param filePath
     * @param sFile
     * @return
     * @throws DAOException 
     */
    public File insertFile(String user, int manualId, String contextPath, FilePath filePath, 
            MultipartFile sFile) throws DAOException {
        File file = null;
        java.io.File convFile = null;
        boolean thereWasAnError = true;
        try {
            String fileName = sFile.getOriginalFilename();
            String manualPath = new ManualDAO(session)
                    .treatManualPath(contextPath, manualId);
            String path = String.format("%s%s%s", manualPath, 
                    java.io.File.separator, fileName);
            String webPath = String.format("%s%s%s%s%s", filePath.getPath(),
                    java.io.File.separator, "manual_" + manualId,
                    java.io.File.separator, fileName);
            
            webPath = webPath.replace(java.io.File.separator, "/");
            file = insertFile(webPath);
            insertFileUser(user, file.getId());
            insertFileManual(file.getId(), manualId);
            
            convFile = new java.io.File(path);
            sFile.transferTo(convFile);
            thereWasAnError = false;
        } catch (IOException | IllegalStateException | BeanException e) {
            DAOValidator.errorOnInsert("File", e);
        } finally {
            if (thereWasAnError && convFile != null) {
                convFile.delete();
            }
        }
        
        return file;
    }
    
    public List<File> insertFiles(String user, int manualId, String contextPath, 
            FilePath filePath, MultipartFile[] sFile) throws DAOException {
        List<File> files = new ArrayList<>();
        for (MultipartFile file: sFile) {
            files.add(insertFile(user, manualId, contextPath, filePath, file));
        }
        return files;
    }
    
    public File insertFile(String path) throws BeanException {
        File file = getFileByPath(path);
        if (file == null) {
            file = new File();
            file.setPath(path);
            session.save(file);
        }
        return file;
    }
    
    public FileUser insertFileUser(String userNick, int fileId) {
        FileUserId id = new FileUserId();
        id.setUserNick(userNick);
        id.setFileId(fileId);
        FileUser fileUser = (FileUser) session.get(FileUser.class, id);
        if (fileUser == null) {
            fileUser = new FileUser(userNick, fileId);
            session.save(fileUser);
        }
        return fileUser;
    }
    
    public FileManual insertFileManual(int fileId, int manualId) {
        FileManualId id = new FileManualId();
        id.setFileId(fileId);
        id.setManualId(manualId);
        FileManual fileManual = (FileManual) session.get(FileManual.class, id);
        if (fileManual == null) {
            fileManual = new FileManual(fileId, manualId);
            session.save(fileManual);
        }
        return fileManual;
    }
    
    public File getFileByPath(String path) {
        File file = null;
        String hql = ""
                + "FROM File "
                + "WHERE path = :path"
                + "";
        Query query = session.createQuery(hql);
        query.setString("path", path);
        List<File> list = query.list();
        if (list.size() == 1) {
            file = list.get(0);
        }
        return file;
    }
    
    public List<File> getUsersImages(String userNick) {
        String hql = ""
                + "FROM File as file "
                + "WHERE file.id IN ( "
                + "     SELECT fileUser.id.fileId "
                + "     FROM FileUser as fileUser"
                + "     WHERE fileUser.id.userNick = :userNick "
                + ") "
                + "";
        Query query = session.createQuery(hql);
        query.setString("userNick", userNick);
        return query.list();
    }
    
}
