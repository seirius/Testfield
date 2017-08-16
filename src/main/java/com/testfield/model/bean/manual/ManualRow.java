package com.testfield.model.bean.manual;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "manual_row", catalog = "testfield")
public class ManualRow implements Serializable {
    
    @Id
    @Column(name = "ID", nullable = false, unique = false, length = 60)
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @GeneratedValue(generator = "generator")
    private String id;
    
    @Column(name = "MANUAL_PAGE", nullable = false, length = 60)
    @JsonBackReference
    private String manualPage;
    
    @Column(name = "ROW_ORDER", nullable = false, length = 5)
    private int rowOrder;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANUAL_ROW")
    @OrderBy("blockOrder")
    private List<ManualBlock> blocks;

    public List<ManualBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<ManualBlock> blocks) {
        this.blocks = blocks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManualPage() {
        return manualPage;
    }

    public void setManualPage(String manualPage) {
        this.manualPage = manualPage;
    }

    public int getRowOrder() {
        return rowOrder;
    }

    public void setRowOrder(int rowOrder) {
        this.rowOrder = rowOrder;
    }
    
}
