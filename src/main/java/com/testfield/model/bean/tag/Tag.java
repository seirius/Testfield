package com.testfield.model.bean.tag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.testfield.model.bean.manual.Manual;
import com.testfield.util.BeanValidator;
import com.testfield.util.exceptions.BeanException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

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
