package com.testfield.hibernate.interceptors;

import com.testfield.model.bean.manual.Manual;
import com.testfield.model.bean.manual.ManualBlock;
import com.testfield.model.bean.manual.ManualPage;
import com.testfield.model.bean.manual.ManualRow;
import java.io.Serializable;
import java.util.Date;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

/**
 *
 * @author Andriy
 */
public class ManualsInterceptor extends EmptyInterceptor {
    
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        
        if (entity instanceof Manual) {
            forManualsSave(entity, id, state, propertyNames, types);
        } else if (entity instanceof ManualPage) {
            forManualPagesSave(entity, id, state, propertyNames, types);
        } else if (entity instanceof ManualRow) {
            forManualRowsSave(entity, id, state, propertyNames, types);
        } else if (entity instanceof ManualBlock) {
            forManualBlocksSave(entity, id, state, propertyNames, types);
        } else {
            return false;
        }
        
        return true;
    }
    
    private void forManualsSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        for (int i = 0; i < propertyNames.length; i++) {
            String propName = propertyNames[i];
            
            switch (propName) {
                
                case "dateCreation":
                case "dateLastMod":
                    if (state[i] == null) {
                        state[i] = new Date();
                    }
                    break;
                    
            }
        }
    }
    
    private void forManualPagesSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        for (int i = 0; i < propertyNames.length; i++) {
            String propName = propertyNames[i];
            
            switch (propName) {
                
//                case "pageOrder":
//                    state[i] = 0;
//                    break;
                
            }
        }
    }
    
    private void forManualRowsSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        for (int i = 0; i < propertyNames.length; i++) {
            String propName = propertyNames[i];
            
            switch (propName) {
                
//                case "rowOrder":
//                    state[i] = 0;
//                    break;
                
            }
        }
    }
    
    private void forManualBlocksSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        for (int i = 0; i < propertyNames.length; i++) {
            String propName = propertyNames[i];
            
            switch (propName) {
                
//                case "blockOrder":
//                    state[i] = 0;
//                    break;
                
            }
        }
    }
    
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        
        if (entity instanceof Manual) {
            forManualsUpdate(entity, id, currentState, previousState, propertyNames, types);
        } else {
            return false;
        }
        
        return true;
    }
    
    private void forManualsUpdate(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        for (int i = 0; i < propertyNames.length; i++) {
            String propName = propertyNames[i];
            
            switch (propName) {
                
                case "dateLastMod":
                    currentState[i] = new Date();
                    break;
                    
                
            }
        }
    }
    
}
