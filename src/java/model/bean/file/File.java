package model.bean.file;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import util.BeanValidator;
import util.exceptions.BeanException;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "file", catalog = "testfield")
public class File implements Serializable {
    
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 11)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(name = "path", nullable = false, length = 255)
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) throws BeanException {
        BeanValidator.validateEmpty(path, "Path");
        BeanValidator.validateLength(path, 255, "Path");
        this.path = path;
    }
    
}
