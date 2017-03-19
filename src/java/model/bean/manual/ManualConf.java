/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean.manual;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import model.bean.style.FontConf;
import util.BeanValidator;
import util.exceptions.BeanException;

/**
 *
 * @author Andriy
 */

@Entity
@Table(name = "manual_conf", catalog = "testfield")
public class ManualConf implements Serializable {
    
    @Id
    @Column(name = "manual", unique = true, nullable = false, length = 11)
    private int manualId;
    
    @Column(name = "manual_background", nullable = false, length = 255)
    private String manualBackground = "none";
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "font_color")
    private FontConf fontColor;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "font_family")
    private FontConf fontFamily;

    public int getManualId() {
        return manualId;
    }

    public void setManualId(int manualId) throws BeanException {
        BeanValidator.validateLength(manualId, 11, "ID");
        this.manualId = manualId;
    }

    public String getManualBackground() {
        return manualBackground;
    }

    public void setManualBackground(String manualBackground) throws BeanException {
        BeanValidator.validateEmpty(manualBackground, "Manual Background");
        BeanValidator.validateLength(manualBackground, 255, "Manual Background");
        this.manualBackground = manualBackground;
    }

    public FontConf getFontColor() {
        return fontColor;
    }
    
    public void setFontColor(FontConf fontColorBean) {
        this.fontColor = fontColorBean;
    }
    
    public FontConf getFontFamily() {
        return fontFamily;
    }
    
    public void setFontFamily(FontConf fontFamilyBean) {
        this.fontFamily = fontFamilyBean;
    }

}
