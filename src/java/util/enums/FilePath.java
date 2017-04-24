package util.enums;

import java.io.File;

/**
 *
 * @author Andriy
 */
public enum FilePath {
    MANUAL_PATH(String.format("%sfiles%smanual", File.separator, File.separator));
    
    private final String path;
    private FilePath(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }
}
