/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean.tag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import model.bean.manual.Manual;
import org.hibernate.annotations.GenericGenerator;
import util.BeanValidator;
import util.exceptions.BeanException;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "tag", catalog = "testfield")
public class Tag implements Serializable {

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false, length = 60)
    private String id;

    @Column(name = "DESCRIPTION", nullable = false, length = 255)
    private String description;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @JsonBackReference
    private List<Manual> manuals;

    public List<Manual> getManuals() {
        return manuals;
    }

    public void setManuals(List<Manual> manuals) {
        this.manuals = manuals;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws BeanException {
        BeanValidator.validateNull(id, "Id");
        BeanValidator.validateLength(id, 60, "Id");
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws BeanException {
        BeanValidator.validateEmpty(description, "Description");
        BeanValidator.validateLength(description, 255, "Description");
        this.description = description;
    }
    
    @Override
    public String toString() {
        return ""
                + "\nTAG ("
                + "\nID: " + id
                + "\nDESCRIPTION: " + description
                + "\n)"
                + "";
    }

}
