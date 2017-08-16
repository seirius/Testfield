package com.testfield.util;

/**
 *
 * @author Andriy
 */
public enum ServiceNicks {
    FILE("FILE", "com.testfield.service.FileService"),
    MANUAL("MANUAL", "com.testfield.service.ManualService"),
    STYLE("STYLE", "com.testfield.service.StyleService"),
    USER("USER", "com.testfield.service.UserService"),
    CH_STYLE("CH_STYLE", "com.testfield.templates.forms.chargers.StyleCharger"),
    CH_MANUAL("CH_MANUAL", "com.testfield.templates.forms.chargers.ManualCharger"),
    SB_MANUAL("SB_MANUAL", "com.testfield.templates.submitter.ManualSubmitter"),
    SB_USER("SB_USER", "com.testfield.templates.submitter.UserSubmitter"),
    SV_USER("SV_USER", "com.testfield.templates.validation.server_validator.UserSV");
    
    private final String nick;
    private final String className;

    private ServiceNicks(String nick, String className) {
        this.nick = nick;
        this.className = className;
    } 
    
    public String getNick() {
        return nick;
    }
    
    public String getClassName() {
        return className;
    }
    
    public static ServiceNicks toEnum(String nick) {
        for (ServiceNicks serviceNick: values()) {
            if (serviceNick.getNick().equals(nick)) {
                return serviceNick;
            }
        }
        
        return null;
    }
}
