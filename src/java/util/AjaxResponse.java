package util;

import java.util.HashMap;
import templates.validation.FormValidationException;

/**
 *
 * @author Andriy Yednarovych
 */
public class AjaxResponse {

    private Integer errorCode = 0;
    private String errorMsg = "";
    private HashMap<String, Object> data;

    public AjaxResponse() {
        data = new HashMap<>();
    }
    
    public void setError(Exception e) {
        System.err.println("-- ERROR: " + e.getMessage());
        errorCode = -1;
        errorMsg = e.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public void setErrorMsg(Exception e) {
        if (e instanceof FormValidationException) {
            FormValidationException ex = (FormValidationException) e;
            int code = ex.getValidationCode().getCode();
            System.err.println(String.format("-- WARNING: Form Validation %d - %s", 
                    code, 
                    ex.getLabelName()));
            errorCode = code;
        } else {
            System.err.println("-- ERROR: " + e.getMessage());
            errorCode = -1;
        }
        errorMsg = ErrorMsgs.DEFAULT_MSG;
    }
    
    public void add(String elementName, Object element) {
        this.data.put(elementName, element);
    }
    
    public void digest(ServiceReturn result) {
        setData(result.getResult());
    }

}
