package model.bean.style;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import model.bean.manual.pojo.ManualStylePojo;
import util.exceptions.BeanException;

/**
 *
 * @author Andriy
 */

@Entity
@Table(name = "font_color", catalog = "testfield")
public class FontColor implements Serializable {
    
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 11)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @Column(name = "r", nullable = false, length = 3)
    private int r;
    
    @Column(name = "g", nullable = false, length = 3)
    private int g;
    
    @Column(name = "b", nullable = false, length = 3)
    private int b;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) throws BeanException {
        isValidRGB(r);
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) throws BeanException {
        isValidRGB(g);
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) throws BeanException {
        isValidRGB(b);
        this.b = b;
    }
    
    @Transient
    private void isValidRGB(int number) throws BeanException {
        if (number < 0 || number > 255) {
            throw new BeanException("Value must be between 0 and 255");
        }
    }
    
    @Transient
    public void setRGB(ManualStylePojo stylePojo) throws BeanException {
        setR(stylePojo.R);
        setG(stylePojo.G);
        setB(stylePojo.B);
    }
    
    @Transient
    public String getCssColor() {
        return String.format("rgb(%d, %d, %d)", r, g, b);
    }
    
}
