/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean.tag;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "rel_tag_manual", catalog="testfield")
public class RelTagManual implements Serializable {
    
    @EmbeddedId
    private RelTagManualId id;

    public RelTagManualId getId() {
        return id;
    }

    public void setId(RelTagManualId id) {
        this.id = id;
    }
    
}
