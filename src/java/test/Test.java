package test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dao.ManualDAO;
import model.bean.manual.Manual;
import util.ServiceManager;

/**
 *
 * @author Andriy
 */
public class Test {

    public static void main(String[] args) {
        ServiceManager manager = new ServiceManager();
        try {
            ManualDAO manualDao = manager.getManualDAO();
            Manual manual = manualDao.getManual(66);
            
            ObjectMapper mapper = new ObjectMapper();
            
            ObjectNode jsonManual = mapper.createObjectNode();
            jsonManual.put("title", manual.getTitle());
            
            ArrayNode pages = mapper.createArrayNode();
            
            manual.getPages().forEach((manualPage) -> {
                ArrayNode rows = mapper.createArrayNode();
                manualPage.getRows().forEach((manualRow) -> {
                    ArrayNode blocks = mapper.createArrayNode();
                    manualRow.getBlocks().forEach((manualBlock) -> {
                        ObjectNode jsonBlock = mapper.createObjectNode();
                        jsonBlock.put("content", manualBlock.getContent());
                        blocks.add(jsonBlock);
                    });
                    rows.add(blocks);
                });
                pages.add(rows);
            });
            
            jsonManual.put("pages", pages);
            
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonManual));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            manager.close();
        }
    } 

}
