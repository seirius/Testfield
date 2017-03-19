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
import util.enums.FontStyle;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "font_conf", catalog = "testfield")
public class FontConf implements Serializable {
    
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 11)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @Column(name = "type", nullable = false, length = 1)
    private int type;
    
    @Column(name = "css_style", nullable = false, length = 255)
    private String cssStyle = "initial";
    
    public FontConf() {}
    
    public FontConf(FontStyle type) {
        this.type = type.getValue();
    }
    
    public FontConf(FontStyle type, String cssStyle) {
        this.type = type.getValue();
        this.cssStyle = cssStyle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getType() {
        return type;
    }

    public void setType(FontStyle fontStyle) {
        this.type = fontStyle.getValue();
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }
    
    
    
}
