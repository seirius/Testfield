package model.bean.file;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Andriy
 */
@Embeddable
public class FileManualId implements Serializable {
    
    @Column(name = "manual_id", unique = true, nullable = false, length = 11)
    private int manualId;
    
    @Column(name = "file_id", unique = true, nullable = false, length = 11)
    private int fileId;

    public int getManualId() {
        return manualId;
    }

    public void setManualId(int manualId) {
        this.manualId = manualId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
    
}
