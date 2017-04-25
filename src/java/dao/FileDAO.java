package dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.bean.file.File;
import model.bean.file.FileManual;
import model.bean.file.FileUser;
import org.hibernate.Session;
import org.springframework.web.multipart.MultipartFile;
import util.DAOValidator;
import util.enums.FilePath;
import util.exceptions.BeanException;
import util.exceptions.DAOException;

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
            file = new File();
            file.setPath(webPath);
            session.save(file);
            FileUser fileUser = new FileUser(user, file.getId());
            session.save(fileUser);
            FileManual fileManual = new FileManual(file.getId(), manualId);
            session.save(fileManual);
            
            java.io.File convFile = new java.io.File(path);
            sFile.transferTo(convFile);
        } catch (IOException | IllegalStateException | BeanException e) {
            DAOValidator.errorOnInsert("File", e);
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
    
}
