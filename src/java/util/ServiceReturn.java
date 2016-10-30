package util;

import java.util.HashMap;
import util.exceptions.ServiceReturnException;

/**
 *
 * @author Andriy
 */
public class ServiceReturn {
    
    private final HashMap<String, Object> RESULT;
    
    public ServiceReturn () {
        RESULT = new HashMap<>();
    }
    
    public HashMap<String, Object> getResult() {
        return RESULT;
    }
    
    public ServiceReturn addItem(String itemName, Object item) {
        RESULT.put(itemName, item);
        return this;
    }
    
    public Object getItem(String itemName) {
        return RESULT.get(itemName);
    }
    
    public String getAsString(String itemName) {
        String item;
        
        try {
            item = (String) RESULT.get(itemName);
        } catch(Exception e) {
            item = null;
            System.err.println(e.getMessage());
        }
        
        return item;
    }
    
    public Integer getAsInteger(String itemName) {
        Integer item;
        
        try {
            item = (Integer) RESULT.get(itemName);
        } catch(Exception e) {
            item = null;
            System.err.println(e.getMessage());
        }
        
        return item;
    }
    
    public Boolean getAsBoolean(String itemName) {
        Boolean item;
        
        try {
            item = (Boolean) RESULT.get(itemName);
        } catch(Exception e) {
            item = null;
            System.err.println(e.getMessage());
        }
        
        return item;
    }
    
    public Double getAsDouble(String itemName) {
        Double item;
        
        try {
            item = (Double) RESULT.get(itemName);
        } catch(Exception e) {
            item = null;
            System.err.println(e.getMessage());
        }
        
        return item;
    }
    
    public Float getAsFloat(String itemName) {
        Float item;
        
        try {
            item = (Float) RESULT.get(itemName);
        } catch(Exception e) {
            item = null;
            System.err.println(e.getMessage());
        }
        
        return item;
    }
    
    public int getAsInt(String itemName) throws ServiceReturnException {
        int item;
        
        try {
            item = (int) RESULT.get(itemName);
        } catch(Exception e) {
            throw new ServiceReturnException(ErrorMsgs.ERR_PARSING);
        }
        
        return item;
    }
    
}
