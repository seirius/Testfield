package test;

import templates.forms.inputs.InputSelect;
import templates.forms.inputs.InputText;

/**
 *
 * @author Andriy
 */
public class Test {
    

    public static void main(String[] args) {
        try {
            System.out.println(new InputText().getClass().equals(new InputText().getClass()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

}
