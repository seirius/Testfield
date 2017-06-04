package templates.validation.server_validator;

import templates.forms.inputs.Input;
import util.ErrorMsgs;
import util.ServiceReturn;

/**
 *
 * @author Andriy
 */
public class UserSV extends ServerValidator {
    
    public ServiceReturn userDuplicate(Input input) {
        ServiceReturn result = new ServiceReturn();
        try {
            
            result.addItem("ok", false);
        } catch (Exception e) {
            ErrorMsgs.sysLogThis(e);
        }
        
        return result;
    }
    
}
