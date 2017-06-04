package templates.forms.chargers;

import dao.FontFamilyDAO;
import java.lang.reflect.Method;
import java.util.List;
import model.bean.manual.Manual;
import model.bean.style.FontColor;
import model.bean.style.FontFamily;
import templates.forms.Form;
import templates.forms.inputs.Input;
import templates.forms.inputs.InputSelect;
import templates.forms.inputs.subinputs.Option;
import util.ServiceManager;
import util.enums.DAOList;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public class StyleCharger extends Charger {
    
    public StyleCharger(Form form) {
        super(form);
    }
    
    public void loadFontFamilies(Input input) throws ChargerException, DAOException {
        try {
            ServiceManager manager = form.getManager();
            
            FontFamilyDAO fontFamilyDao = (FontFamilyDAO) manager
                    .getDAO(DAOList.FONT_FAMILY);
            List<FontFamily> fontFamilies = fontFamilyDao.getAllFontFamilies();
            
            InputSelect inputFormFamily = input.getAsSelect();
            for (FontFamily fontFamily: fontFamilies) {
                String value = String.valueOf(fontFamily.getId());
                inputFormFamily.addOption(new Option(value, fontFamily.getFontName()));
            }
            
            int manualId = form.getFormLoadData().getAs("manualId", Integer.class);
            Manual manual = shareManual(manualId);
            int idFormFamily = manual.getManualConf().getFontFamily().getId();
            inputFormFamily.setValue(String.valueOf(idFormFamily));
            
        } catch (DAOException e) {
            throw e;
        } catch(Exception e) {
            throw new ChargerException(e);
        }
    }
    
    public void loadFontColor(Input input) throws ChargerException, DAOException {
        try {
            int manualId = form.getFormLoadData().getAs("manualId", Integer.class);
            Manual manual = shareManual(manualId);
            
            String getName = String.format("get%s", input.getName().toUpperCase());
            FontColor fontColor = manual.getManualConf().getFontColor();
            Method classMethod = fontColor.getClass()
                    .getMethod(getName);
            int rgb = (Integer) classMethod.invoke(fontColor);
            input.setValue(rgb);
            
        } catch (DAOException e) {
            throw e;
        } catch(Exception e) {
            throw new ChargerException(e);
        }
    }
    
    
}
