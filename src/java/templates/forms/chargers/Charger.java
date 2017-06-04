package templates.forms.chargers;

import dao.ManualDAO;
import model.bean.manual.Manual;
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
        Manual manual = null;
        FormData formData = form.getFormLoadData();
        manual = formData.getAs("manual", Manual.class);
        if (manual == null) {
            manual = ((ManualDAO) form.getManager()
                    .getDAO(DAOList.MANUAL))
                    .getManual(manualId);
        }
        return manual;
    }


}
