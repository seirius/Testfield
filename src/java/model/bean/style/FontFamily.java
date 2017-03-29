/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean.style;

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
