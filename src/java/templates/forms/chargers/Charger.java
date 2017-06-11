package templates.forms.chargers;

import dao.ManualBlockDAO;
import dao.ManualDAO;
import model.bean.manual.Manual;
import model.bean.manual.ManualBlock;
import templates.forms.Form;
import templates.forms.FormData;
import util.enums.DAOList;
import util.exceptions.DAOException;

/**
 *
 * @author Andriy
 */
public abstract class Charger {
    
    protected Form form;
    
    public Charger() {}
    
    public Charger(Form form) {
        this.form = form;
    }
    
    protected Manual shareManual(int manualId) throws DAOException {
        FormData formData = form.getFormLoadData();
        Manual manual = formData.getAs("manual", Manual.class);
        if (manual == null) {
            manual = ((ManualDAO) form.getManager()
                    .getDAO(DAOList.MANUAL))
                    .getManual(manualId);
        }
        return manual;
    }
    
    protected ManualBlock shareManualBlock(String idBlock) throws DAOException {
        FormData formData = form.getFormLoadData();
        ManualBlock manualBlock = formData.getAs("manualBlock", ManualBlock.class);
        if (manualBlock == null) {
            manualBlock = (form.getManager().getDAO(ManualBlockDAO.class))
                    .getBlock(idBlock);
        }
        return manualBlock;
    }


}
