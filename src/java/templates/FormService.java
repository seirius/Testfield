package templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletContext;
import util.FileUtil;
import util.UrlUtil;

/**
 *
 * @author Andriy
 */
public class FormService {
    
    public static ObjectNode loadJsonForm(ServletContext servletContext, 
            String formName) throws IOException {
        String realPath = servletContext
                .getRealPath(UrlUtil.getUrl(new String[]{
            "WEB-INF", "form_templates", formName + ".json"
        }));
        String jsonFile = FileUtil.readFile(
                realPath,
                StandardCharsets.UTF_8
        );
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonFile, ObjectNode.class);
    }
    
}
