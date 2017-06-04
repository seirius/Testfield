package templates.validation;

/**
 *
 * @author Andriy
 */
public class Validation {
    
    private Boolean required;
    private Integer minLength;
    private Integer maxLength;
    private String inputVerify;
    private String server;

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getInputVerify() {
        return inputVerify;
    }

    public void setInputVerify(String inputVerify) {
        this.inputVerify = inputVerify;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
    
}
