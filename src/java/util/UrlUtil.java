package util;

import java.io.File;

/**
 *
 * @author Andriy
 */
public class UrlUtil {
    
    public static String getUrl(String[] keyWords) {
        String url = File.separator;
        for (int i = 0; i < keyWords.length; i++) {
            url += keyWords[i];
            
            if (i + 1 < keyWords.length) {
                url += File.separator;
            }
        }
        return url;
    }
    
}
