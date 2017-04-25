package test;

import java.io.File;
import util.ServiceManager;

/**
 *
 * @author Andriy
 */
public class Test {

    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\Andriy\\Desktop\\Dev\\Dev_projects\\Netbeans\\Testfield\\build\\web\\WEB-INF\\MANUAL_PATH\\manual_69");
            file.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    } 

}
