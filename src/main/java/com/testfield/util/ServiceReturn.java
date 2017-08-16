package com.testfield.util;

import com.testfield.util.exceptions.ServiceReturnException;
import com.testfield.util.interfaces.WebBean;
import java.util.HashMap;
import java.util.List;

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
    
    public ServiceReturn addItem(String itemName, Object item, boolean prepareForWeb) throws Exception {
        if (prepareForWeb && item instanceof WebBean) {
            if (item instanceof WebBean) {
                ((WebBean) item).prepareForWeb();
            } else if (item instanceof List) {
                for (WebBean webBean: (List<WebBean>) item) {
                    webBean.prepareForWeb();
                }
            } else {
                System.err.println("Item is not an WebBean.");
            }
        }
        addItem(itemName, item);
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
