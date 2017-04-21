package model.bean.manual;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.beans.Transient;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import model.bean.widthtype.RelBlockWidthType;
import model.bean.widthtype.WidthType;
import org.hibernate.annotations.GenericGenerator;
import util.enums.BlockWidthTypeEnum;

/**
 *
 * @author Andriy
 */

@Entity
@Table(name = "manual_block", catalog = "testfield")
public class ManualBlock implements Serializable {
    
    @Id
    @Column(name = "ID", nullable = false, unique = true, length = 60)
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @GeneratedValue(generator = "generator")
    private String id;
    
    @JsonBackReference
    @Column(name = "MANUAL_ROW", nullable = false, length = 60)
    private String manualRow;
    
    @Column(name = "BLOCK_ORDER", nullable = false, length = 5)
    private int blockOrder;
    
    @Column(name = "CONTENT", nullable = false, length = 65535)
    private String content;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_block_width_type", catalog = "testfield", 
        joinColumns = {
            @JoinColumn(name = "MANUAL_BLOCK", nullable = false, updatable = false)
        },
        inverseJoinColumns = {
            @JoinColumn(name = "WIDTH_TYPE", nullable = false, updatable = false)
        }
    )
    private List<WidthType> widthTypes;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANUAL_BLOCK")
    private List<RelBlockWidthType> relBlockWidthTypes;

    public List<RelBlockWidthType> getRelBlockWidthTypes() {
        return relBlockWidthTypes;
    }

    public void setRelBlockWidthTypes(List<RelBlockWidthType> relBlockWidthTypes) {
        this.relBlockWidthTypes = relBlockWidthTypes;
    }

    public String getId() {
        return id;
    }

    public List<WidthType> getWidthTypes() {
        return widthTypes;
    }

    public void setWidthTypes(List<WidthType> widthTypes) {
        this.widthTypes = widthTypes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManualRow() {
        return manualRow;
    }

    public void setManualRow(String manualRow) {
        this.manualRow = manualRow;
    }

    public int getBlockOrder() {
        return blockOrder;
    }

    public void setBlockOrder(int blockOrder) {
        this.blockOrder = blockOrder;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Transient
    public String getWidthCssStyle() {
        String clazz = "";
        if (widthTypes == null || relBlockWidthTypes == null) {
            return clazz;
        }
        
        int amount, widthTypeId;
        for (RelBlockWidthType relWidthType: relBlockWidthTypes) {
            amount = relWidthType.getAmount();
            widthTypeId = relWidthType.getId().getWidthType();
            for (int i = 0; i < widthTypes.size(); i++) {
                WidthType widthType = widthTypes.get(i);
                if (widthType.getWidthType() == widthTypeId) {
                    BlockWidthTypeEnum widthTypeEnum = BlockWidthTypeEnum
                            .getWidthTypeEnum(widthTypeId);
                    clazz += String.format("col-%s-%d ", widthTypeEnum.getCss(), 
                            amount);
                    i = widthTypes.size();
                }
            }
        }
        
        return clazz;
    }
    
}
