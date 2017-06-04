package templates.forms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;

/**
 *
 * @author Andriy
 */
public class FormData {
    private HashMap<String, Object> data;

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
    
    @JsonIgnore
    public <T> T getAs(String name, Class<T> s) {
        return s.cast(data.get(name));
    } 
    
    @JsonIgnore 
    public void addItem(String name, Object item) {
        data.put(name, item);
    }
    
}
