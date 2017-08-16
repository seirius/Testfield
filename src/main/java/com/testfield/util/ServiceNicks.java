package com.testfield.util;

/**
 *
 * @author Andriy
 */
public enum ServiceNicks {
    FILE("FILE", "service.FileService"),
    MANUAL("MANUAL", "service.ManualService"),
    STYLE("STYLE", "service.StyleService"),
    USER("USER", "service.UserService"),
    CH_STYLE("CH_STYLE", "templates.forms.chargers.StyleCharger"),
    CH_MANUAL("CH_MANUAL", "templates.forms.chargers.ManualCharger"),
    SB_MANUAL("SB_MANUAL", "templates.submitter.ManualSubmitter"),
    SB_USER("SB_USER", "templates.submitter.UserSubmitter"),
    SV_USER("SV_USER", "templates.validation.server_validator.UserSV");
    
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
