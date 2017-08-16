package com.testfield.templates.forms.chargers;

import com.testfield.dao.ManualBlockDAO;
import com.testfield.dao.ManualDAO;
import com.testfield.model.bean.manual.Manual;
import com.testfield.model.bean.manual.ManualBlock;
import com.testfield.templates.forms.Form;
import com.testfield.templates.forms.FormData;
import com.testfield.util.enums.DAOList;
import com.testfield.util.exceptions.DAOException;

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
