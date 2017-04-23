package model.bean.file;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Andriy
 */
@Embeddable
public class FileUserId implements Serializable {
    
    @Column(name = "user_nick", unique = true, nullable = false, length = 30)
    private String userNick;
    
    @Column(name = "file_id", unique = true, nullable = false, length = 11)
    private int fileId;

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
    
    
    
}
