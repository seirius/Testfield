package model.bean.file;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "file_user", catalog = "testfield")
public class FileUser implements Serializable {
    
    @EmbeddedId
    private FileUserId id;

    public FileUser() {}
    
    public FileUser(String userNick, int fileId) {
        id = new FileUserId();
        id.setFileId(fileId);
        id.setUserNick(userNick);
    }
    
    public FileUserId getId() {
        return id;
    }

    public void setId(FileUserId id) {
        this.id = id;
    }
    
}
