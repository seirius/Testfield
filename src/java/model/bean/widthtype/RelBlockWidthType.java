/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean.widthtype;

import java.beans.Transient;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "rel_block_width_type", catalog = "testfield")
public class RelBlockWidthType implements Serializable {
    
    @EmbeddedId
    private RelBlockWidthTypeId id;
    
    @Column(name = "AMOUNT", nullable = false, length = 4)
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public RelBlockWidthTypeId getId() {
        return id;
    }

    public void setId(RelBlockWidthTypeId id) {
        this.id = id;
    }
    
    @Transient
    public void setManual(String idBlock) {
        if (id == null) {
            id = new RelBlockWidthTypeId();
        }
        
        id.setManualBlock(idBlock);
    }
    
    @Transient
    public void setWidthType(int widthType) {
        if (id == null) {
            id = new RelBlockWidthTypeId();
        }
        
        id.setWidthType(widthType);
    }
    
}
