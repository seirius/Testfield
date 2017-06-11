package templates.forms.chargers;

import java.util.List;
import model.bean.manual.ManualBlock;
import model.bean.widthtype.RelBlockWidthType;
import templates.forms.Form;
import templates.forms.inputs.Input;
import util.enums.BlockWidthTypeEnum;
import util.exceptions.DAOException;

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
