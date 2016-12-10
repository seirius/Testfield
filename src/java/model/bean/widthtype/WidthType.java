/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean.widthtype;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.beans.Transient;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import model.bean.manual.ManualBlock;
import util.enums.BlockWidthTypeEnum;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "width_type", catalog = "testfield")
public class WidthType implements Serializable {
    
    @Id
    @Column(name = "WIDTH_TYPE", unique = true, nullable = false, length = 2)
    private int widthType;

    @Column(name = "DESCRIPTION", unique = true, nullable = false, length = 60)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "widthTypes")
    @JsonBackReference
    private List<ManualBlock> manualBlocks;

    public List<ManualBlock> getManualBlocks() {
        return manualBlocks;
    }

    public void setManualBlocks(List<ManualBlock> manualBlocks) {
        this.manualBlocks = manualBlocks;
    }

    public int getWidthType() {
        return widthType;
    }

    public void setWidthType(int widthType) {
        this.widthType = widthType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Transient
    public void setBlockWidthType(BlockWidthTypeEnum blockWidthType) {
        setWidthType(blockWidthType.getValue());
        setDescription(blockWidthType.getText());
    }

    @Override
    public String toString() {
        return ""
                + "\nWidthType ("
                + "\n widthType: " + getWidthType()
                + "\n description: " + getDescription()
                + "\n);"
                + "";
    }

}
