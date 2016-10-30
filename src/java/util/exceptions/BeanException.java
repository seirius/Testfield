package util.exceptions;

/**
 * @author Andriy Yednarovych
 */
public class BeanException extends MyException {

    public BeanException (String msg) {
        super(msg);
    }
    
    public BeanException (String msg, Exception e) {
        super(msg, e);
    }
    
}
