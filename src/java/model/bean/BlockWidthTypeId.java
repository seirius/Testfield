package model.bean;
// Generated 18-nov-2016 19:09:37 by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BlockWidthTypeId generated by hbm2java
 */
@Embeddable
public class BlockWidthTypeId implements java.io.Serializable {

    private int widthType;
    private String manualBlock;

    public BlockWidthTypeId() {
    }

    public BlockWidthTypeId(int widthType, String manualBlock) {
        this.widthType = widthType;
        this.manualBlock = manualBlock;
    }

    @Column(name = "WIDTH_TYPE", nullable = false)
    public int getWidthType() {
        return this.widthType;
    }

    public void setWidthType(int widthType) {
        this.widthType = widthType;
    }

    @Column(name = "MANUAL_BLOCK", nullable = false, length = 60)
    public String getManualBlock() {
        return this.manualBlock;
    }

    public void setManualBlock(String manualBlock) {
        this.manualBlock = manualBlock;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof BlockWidthTypeId)) {
            return false;
        }
        BlockWidthTypeId castOther = (BlockWidthTypeId) other;

        return (this.getWidthType() == castOther.getWidthType())
                && ((this.getManualBlock() == castOther.getManualBlock()) || (this.getManualBlock() != null && castOther.getManualBlock() != null && this.getManualBlock().equals(castOther.getManualBlock())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getWidthType();
        result = 37 * result + (getManualBlock() == null ? 0 : this.getManualBlock().hashCode());
        return result;
    }

}
