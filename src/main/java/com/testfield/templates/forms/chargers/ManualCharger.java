package com.testfield.templates.forms.chargers;

import com.testfield.model.bean.manual.ManualBlock;
import com.testfield.model.bean.widthtype.RelBlockWidthType;
import com.testfield.templates.forms.Form;
import com.testfield.templates.forms.inputs.Input;
import com.testfield.util.enums.BlockWidthTypeEnum;
import com.testfield.util.exceptions.DAOException;
import java.util.List;

/**
 *
 * @author Andriy
 */
public class ManualCharger extends Charger {
    
    public ManualCharger(Form form) {
        super(form);
    }
    
    public void loadBlockSize(Input input) throws ChargerException {
        try {
            String idBlock = form.getFormLoadData().getAs("idBlock", String.class);
            ManualBlock manualBlock = shareManualBlock(idBlock);
            BlockWidthTypeEnum widthEnum = BlockWidthTypeEnum
                    .getWidthTypeEnum(input.getName());
            List<RelBlockWidthType> relTypes = manualBlock.getRelBlockWidthTypes();
            boolean relTypeFound = false;
            for (int i = 0; i < relTypes.size() && !relTypeFound; i++) {
                RelBlockWidthType relType = relTypes.get(i);
                if (relType.getId().getWidthType() == widthEnum.getValue()) {
                    input.setValue(relType.getAmount());
                    relTypeFound = true;
                }
            }
            if (!relTypeFound) {
                input.setValue(null);
            }
        } catch (DAOException e) {
            throw new ChargerException(e);
        }
    }
    
}
