/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean.tag;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import util.BeanValidator;
import util.exceptions.BeanException;

/**
 *
 * @author Andriy
 */
@Embeddable
public class RelTagManualId implements Serializable {
    
    @Column(name = "TAG", nullable = false, length = 60)
    private String tag;
    @Column(name = "MANUAL", nullable = false, length = 11)
    private int manual;
    
    public RelTagManualId() {}
    
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) throws BeanException {
        BeanValidator.validateEmpty(tag, "Tag");
        BeanValidator.validateLength(tag, 60, "Tag");
        this.tag = tag;
    }

    public int getManual() {
        return manual;
    }

    public void setManual(int manual) throws BeanException {
        BeanValidator.validateLength(manual, 11, "Manual");
        this.manual = manual;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        
        if (other == null) {
            return false;
        }
        
        if (!(other instanceof RelTagManualId)) {
            return false;
        }
        
        RelTagManualId auxOther = (RelTagManualId) other;
        int otherManual = auxOther.getManual();
        String otherTag = auxOther.getTag();
        return otherManual == manual && otherTag.equals(tag);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.tag);
        hash = 29 * hash + this.manual;
        return hash;
    }

    
}
