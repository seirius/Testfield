package templates.forms.chargers;

/**
 *
 * @author Andriy
 */
public class ChargerException extends Exception {

    private int code;
    
    public ChargerException(int code) {
        this.code = code;
    }
    
    public ChargerException(String msg) {
        super(msg);
    }
    
    public ChargerException(String msg, Exception e) {
        super(msg, e);
    }
    
    public ChargerException(Exception e) {
        super(e);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
}
