package com.testfield.model.bean.manual;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.testfield.util.BeanValidator;
import com.testfield.util.exceptions.BeanException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "manual_page", catalog = "testfield")
public class ManualPage implements Serializable {
    
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 60)
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @GeneratedValue(generator = "generator")
    private String id;
    
    @Column(name = "MANUAL", nullable = false, length = 11)
    @JsonBackReference
    private int manualId;
    
    @Column(name = "PAGE_ORDER", nullable = false, length = 5)
    private int pageOrder;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANUAL_PAGE")
    @OrderBy("rowOrder")
    private List<ManualRow> rows;
    
    public List<ManualRow> getRows() {
        return rows;
    }

    public void setRows(List<ManualRow> rows) {
        this.rows = rows;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getManualId() {
        return manualId;
    }

    public void setManualId(int manualId) {
        this.manualId = manualId;
    }

    public int getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(int pageOrder) throws BeanException {
        BeanValidator.validateLength(pageOrder, 5, "Order");
        this.pageOrder = pageOrder;
    }
    
}
