/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;

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
        errorCode = -1;
        errorMsg = e.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData2(HashMap<String, Object> data) {
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
    
    public void setErrorMsg() {
        this.errorMsg = ErrorMsgs.DEFAULT_MSG;
    }
    
    public void add(String elementName, Object element) {
        this.data.put(elementName, element);
    }

}
