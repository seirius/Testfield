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
@Table(name = "file_manual", catalog = "testfield")
public class FileManual implements Serializable {
    
    @EmbeddedId
    private FileManualId id;
    
    public FileManual() {}
    
    public FileManual(int fileId, int manualId) {
        id = new FileManualId();
        id.setFileId(fileId);
        id.setManualId(manualId);
    }

    public FileManualId getId() {
        return id;
    }

    public void setId(FileManualId id) {
        this.id = id;
    }
    
}
