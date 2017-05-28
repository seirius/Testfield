package util;

/**
 *
 * @author Andriy
 */
public enum ServiceNicks {
    FILE("FILE", "service.FileService"),
    MANUAL("MANUAL", "service.ManualService"),
    STYLE("STYLE", "service.StyleService"),
    USER("USER", "service.UserService");
    
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
