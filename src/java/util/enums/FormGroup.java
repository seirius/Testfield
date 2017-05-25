package util.enums;

/**
 *
 * @author Andriy
 */
public enum FormGroup {
    TEXT("text"), 
    SELECT("select"), 
    TEXTAREA("textarea"), 
    RADIO("radio"),
    CHECKBOX("checkbox"), 
    CHECKBOX_GRP("checkboxGroup");
    
    private final String name;
    
    private FormGroup(String name) {
        this.name = name;
    }
    
    public static FormGroup toEnum(String name) {
        for (FormGroup formGroup: values()) {
            if (formGroup.getName().equals(name)) {
                return formGroup;
            }
        }
        
        return null;
    }
    
    public String getName() {
        return name;
    }
}
