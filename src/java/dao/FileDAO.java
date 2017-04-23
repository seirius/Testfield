package dao;

import model.bean.file.File;
import model.bean.file.FileManual;
import model.bean.file.FileUser;
import org.hibernate.Session;
import util.DAOValidator;
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
     * @param path
     * @return
     * @throws DAOException 
     */
    public File insertFile(String user, int manualId, String path) throws DAOException {
        File file = null;
        
        try {
            file = new File();
            file.setPath(path);
            session.save(file);
            FileUser fileUser = new FileUser(user, file.getId());
            session.save(fileUser);
            FileManual fileManual = new FileManual(file.getId(), manualId);
            session.save(fileManual);
        } catch (BeanException e) {
            DAOValidator.errorOnInsert("File", e);
        }
        
        return file;
    }
    
}
