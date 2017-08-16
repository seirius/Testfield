package com.testfield.templates.submitter;

import com.testfield.dao.FontFamilyDAO;
import com.testfield.dao.ManualBlockDAO;
import com.testfield.dao.ManualConfDAO;
import com.testfield.model.bean.manual.Manual;
import com.testfield.model.bean.manual.ManualBlock;
import com.testfield.model.bean.manual.ManualConf;
import com.testfield.model.bean.style.FontColor;
import com.testfield.model.bean.style.FontFamily;
import com.testfield.model.bean.widthtype.RelBlockWidthType;
import com.testfield.templates.forms.Form;
import com.testfield.templates.forms.FormData;
import com.testfield.templates.forms.inputs.Input;
import com.testfield.util.ErrorMsgs;
import com.testfield.util.Security;
import com.testfield.util.ServiceManager;
import com.testfield.util.ServiceReturn;
import com.testfield.util.enums.BlockWidthTypeEnum;
import com.testfield.util.enums.DAOList;
import org.hibernate.Session;

/**
 *
 * @author Andriy
 */
public class ManualSubmitter extends Submitter {
    public ServiceReturn updateManualsStyle(Form form) throws SubmitterException {
        ServiceReturn result = new ServiceReturn();
        try {
            ServiceManager manager = form.getManager();
            FormData formData = form.getFormSendData();
            int manualId = formData.getAs("manualId", Integer.class);
            
            String userNick = (String) form.getRequest()
                    .getSession()
                    .getAttribute("user");
            
            Manual manual = manager.getManualDAO().getManual(manualId);
            if (!Security.permissionModManual(manual, userNick)) {
                throw new SubmitterException(ErrorMsgs.ACC_DEN);
            }
            
            String idFontFamily = form.getInputByName("fontFamily").getValue(String.class);
            Integer r = form.getInputByName("r").getValue(Integer.class);
            Integer g = form.getInputByName("g").getValue(Integer.class);
            Integer b = form.getInputByName("b").getValue(Integer.class);
            
            ManualConfDAO manualConfDao = 
                    (ManualConfDAO) manager.getDAO(DAOList.MANUAL_CONF);
            FontFamilyDAO fontFamilyDao = 
                    (FontFamilyDAO) manager.getDAO(DAOList.FONT_FAMILY);
            ManualConf manualConf = manual.getManualConf();
            FontColor fontColor = manualConf.getFontColor();
            fontColor.setR(r);
            fontColor.setG(g);
            fontColor.setB(b);
            FontFamily fontFamily = fontFamilyDao.getFontFamily(Integer.parseInt(idFontFamily));
            if (fontFamily == null) {
                throw new SubmitterException("Font family doesn't exist.");
            }
            manualConf.setFontFamily(fontFamily);
            
            manualConf.setFontColor(fontColor);
            manualConfDao.update(manualConf);
            
            result.addItem("manual", manual, true);
        } catch (SubmitterException e) {
            throw e;
        } catch (Exception e) {
            throw new SubmitterException(e);
        }
        return result;
    }
    
    public ServiceReturn updateBlockOptions(Form form) throws SubmitterException {
        ServiceReturn result = new ServiceReturn();
        try {
            FormData formData = form.getFormSendData();
            String idBlock = formData.getAs("idBlock", String.class);
            Session session = form.getManager().getSession();
            for (BlockWidthTypeEnum type: BlockWidthTypeEnum.values()) {
                Input input = form.getInputByName(type.getCss());
                RelBlockWidthType relType = new RelBlockWidthType();
                relType.setManual(idBlock);
                relType.setWidthType(type.getValue());
                if (input.getValue() != null) {
                    relType.setAmount(input.getValue(Integer.class));
                    session.saveOrUpdate(relType);
                } else {
                    session.delete(relType);
                }
            }
            
            ManualBlock manualBlock = form
                    .getManager()
                    .getDAO(ManualBlockDAO.class)
                    .getBlock(idBlock);
            
            result.addItem("manualBlock", manualBlock, true);
        } catch (Exception e) {
            throw new SubmitterException(e);
        }
        return result;
    }
}
