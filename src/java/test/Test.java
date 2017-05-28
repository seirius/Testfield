package test;

/**
 *
 * @author Andriy
 */
public class Test {

    public static void main(String[] args) {
        try {
            String s = "USER login";
            String[] sArray = s.split(" ");
            System.out.println(sArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

}
