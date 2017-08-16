package com.testfield.templates.validation;

/**
 *
 * @author Andriy
 */
public class NgModelOptions {
    private String updateOn;
    private int debounce;
    private boolean allowInvalid = false;
    private boolean getterSetter = false;

    public String getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(String updateOn) {
        this.updateOn = updateOn;
    }

    public int getDebounce() {
        return debounce;
    }

    public void setDebounce(int debounce) {
        this.debounce = debounce;
    }

    public boolean isAllowInvalid() {
        return allowInvalid;
    }

    public void setAllowInvalid(boolean allowInvalid) {
        this.allowInvalid = allowInvalid;
    }

    public boolean isGetterSetter() {
        return getterSetter;
    }

    public void setGetterSetter(boolean getterSetter) {
        this.getterSetter = getterSetter;
    }
    
}
