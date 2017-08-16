package com.testfield.model.bean.manual;

import com.testfield.model.bean.style.FontColor;
import com.testfield.model.bean.style.FontFamily;
import com.testfield.util.BeanValidator;
import com.testfield.util.exceptions.BeanException;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
    private FontColor fontColor;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "font_family")
    private FontFamily fontFamily;
    
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

    public FontColor getFontColor() {
        return fontColor;
    }
    
    public void setFontColor(FontColor fontColorBean) {
        this.fontColor = fontColorBean;
    }
    
    public FontFamily getFontFamily() {
        return fontFamily;
    }
    
    public void setFontFamily(FontFamily fontFamily) {
        this.fontFamily = fontFamily;
    }

}
