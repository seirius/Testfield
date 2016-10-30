package util.exceptions;

/**
 *
 * @author Andriy
 */
public class ServiceReturnException extends MyException {

    public ServiceReturnException (String msg) {
        super(msg);
    }

    public ServiceReturnException (String msg, Exception e) {
        super(msg, e);
    }
    
}

