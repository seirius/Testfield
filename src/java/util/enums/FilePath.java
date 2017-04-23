package util.enums;

/**
 *
 * @author Andriy
 */
public enum FilePath {
    MANUAL_PATH("/files/manual");
    
    private final String path;
    private FilePath(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }
}
