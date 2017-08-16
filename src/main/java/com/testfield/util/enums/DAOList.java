package com.testfield.util.enums;

/**
 *
 * @author Andriy
 */
public enum DAOList {
    BLOCK_WIDTH_TYPE("BlockWidthTypeDAO"),
    FONT_COLOR("FontColorDAO"),
    MANUAL_BLOCK("ManualBlockDAO"),
    MANUAL_CONF("ManualConfDAO"),
    MANUAL("ManualDAO"),
    MANUAL_PAGE("ManualPageDAO"),
    MANUAL_ROW("ManualRowDAO"),
    TAG("TagDAO"),
    USER("UserDAO"),
    USER_INFO("UserInfoDAO"),
    WIDTH_TYPE("WidthTypeDAO"),
    FONT_FAMILY("FontFamilyDAO"),
    FILE("FileDAO");
    
    private final String value;
    private final String path = "dao";
    
    private DAOList(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return String.format("%s.%s", path, value);
    }
}
