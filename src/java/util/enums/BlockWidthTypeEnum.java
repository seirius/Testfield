package util.enums;

/**
 *
 * @author Andriy
 */
public enum BlockWidthTypeEnum {
    XS(1, "Phones", "s"), 
    SM(2, "Tablets", "m"), 
    LG(4, "Large display", "l");
    
    private final int value;
    private final String text;
    private final String css;
    
    BlockWidthTypeEnum(int value, String text, String css) {
        this.value = value;
        this.text = text;
        this.css = css;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getText() {
        return text;
    }
    
    public String getCss() {
        return css;
    }
    
    public static BlockWidthTypeEnum getWidthTypeEnum(int value) {
        for (BlockWidthTypeEnum val: values()) {
            if (val.getValue() == value) {
                return val;
            }
        }
        return null;
    }
    
    public static BlockWidthTypeEnum getWidthTypeEnum(String css) {
        for (BlockWidthTypeEnum val: values()) {
            if (val.getCss().equalsIgnoreCase(css)) {
                return val;
            }
        }
        return null;
    }
}
