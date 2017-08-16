package com.testfield.model.bean.widthtype;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Andriy
 */
@Embeddable
public class RelBlockWidthTypeId implements Serializable {
    
    @Column(name = "WIDTH_TYPE", unique = true, nullable = false, length = 2)
    private int widthType;
    
    @Column(name = "MANUAL_BLOCK", unique = true, nullable = false, length = 60)
    private String manualBlock;

    public int getWidthType() {
        return widthType;
    }

    public void setWidthType(int widthType) {
        this.widthType = widthType;
    }

    public String getManualBlock() {
        return manualBlock;
    }

    public void setManualBlock(String manualBlock) {
        this.manualBlock = manualBlock;
    }
    
}
