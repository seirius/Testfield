package com.testfield.model.bean.style;

import com.testfield.util.BeanValidator;
import com.testfield.util.exceptions.BeanException;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "font_family", catalog = "testfield")
public class FontFamily implements Serializable {
    
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 11)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @Column(name = "css_style", nullable = false, length = 255)
    private String cssStyle;
    
    @Column(name = "font_name", nullable = false, length = 100)
    private String fontName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) throws BeanException {
        BeanValidator.validateEmpty(cssStyle, "Css Style");
        BeanValidator.validateLength(cssStyle, 255, "Css Style");
        this.cssStyle = cssStyle;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) throws BeanException {
        BeanValidator.validateEmpty(fontName, "Font's name");
        BeanValidator.validateLength(fontName, 100, "Font's name");
        this.fontName = fontName;
    }
    
    
    
}
