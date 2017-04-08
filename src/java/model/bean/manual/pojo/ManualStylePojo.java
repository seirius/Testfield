package model.bean.manual.pojo;

/**
 *
 * @author Andriy
 */
public class ManualStylePojo {
    
    public int manualId;
    public int R, G, B;
    public int fontFamily;
    
    @Override
    public String toString() {
        return String.format(
                "R: %d\nG: %d\nB: %d\nFontFamily: %d",
                R, G, B, fontFamily);
    }
    
    public String getFontColor() {
        return String.format("rgb(%d, %d, %d)", R, G, B);
    }
    
}
