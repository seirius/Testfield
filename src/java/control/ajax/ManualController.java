/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.ajax;

import java.util.List;
import javax.servlet.http.HttpSession;
import model.bean.manual.Manual;
import model.bean.manual.ManualBlock;
import model.bean.widthtype.WidthTypeHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ManualService;
import util.AjaxResponse;
import util.Security;
import util.ServiceReturn;
import util.enums.DeleteOptions;
import util.enums.MoveOptions;
import util.exceptions.ServiceException;

/**
 *
 * @author Andriy
 */
@Controller
public class ManualController extends MyController {
    
    @RequestMapping(value = "/manual/createManual", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse createManual(HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().createManual(userNick);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/loadManual", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse loadManual(HttpSession session, @RequestBody Manual manual) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().loadManual(manual.getId());
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/loadManuals", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse loadManuals(HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().loadManuals(userNick);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/setTitle", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse setTitle(HttpSession session, @RequestParam int idManual, @RequestParam String title) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().setManualTitle(userNick, idManual, title);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/saveManualBlock", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse saveManualBlock(HttpSession session, @RequestParam String idBlock, @RequestParam String content) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().updateBlockContent(userNick, idBlock, content);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/addRow", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse addRow(HttpSession session, @RequestParam String idPage, @RequestParam int rowOrder) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().addRow(userNick, idPage, rowOrder);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/addPage", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse addRow(HttpSession session, @RequestParam int idManual, @RequestParam int pageOrder) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().addPage(userNick, idManual, pageOrder);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/getWidthTypes", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse getWidthTypes() {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            ServiceReturn serviceReturn = new ManualService().getWidthTypes();
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    

    @RequestMapping(value = "/manual/modifyBlockSize", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse modifyBlockSize(HttpSession session, @RequestParam String idBlock, @RequestParam String widthTypes) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            List<WidthTypeHelper> widthTypeList = WidthTypeHelper.parseWidthTypes(widthTypes);
            ServiceReturn serviceReturn = new ManualService().updateBlockSize(userNick, idBlock, widthTypeList);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse; 
    }
    
    /**
     *
     * @param session
     * @param idRow
     * @param content
     * @param blockOrder
     * @param widthTypes
     * @return
     */
    @RequestMapping(value = "/manual/addBlock", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse addBlock(
            HttpSession session, 
            @RequestParam String idRow, 
            @RequestParam String content, 
            @RequestParam int blockOrder, 
            @RequestParam String widthTypes
    ) 
    {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            List<WidthTypeHelper> widthTypeList = WidthTypeHelper.parseWidthTypes(widthTypes);
            ServiceReturn serviceReturn = new ManualService().addBlock(userNick, idRow, content, blockOrder, widthTypeList);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/deleteOptions", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse deleteOptions(HttpSession session, @RequestParam String deleteType, @RequestParam String id) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().deleteOptions(userNick, DeleteOptions.toDeleteOptions(deleteType), id);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/moveBlock", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse moveBlock(HttpSession session, @RequestParam String idBlock, @RequestParam int moveOption) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().moveBlock(userNick, MoveOptions.toMoveOptions(moveOption), idBlock);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "/manual/movePage", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse movePage(HttpSession session, @RequestParam String idPage, @RequestParam int moveOption) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().movePage(userNick, MoveOptions.toMoveOptions(moveOption), idPage);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse; 
    }
    
    @RequestMapping(value = "/manual/moveRow", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse moveRow(HttpSession session, @RequestParam String idRow, @RequestParam int moveOption) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String userNick = Security.isSessionOpened(session);
            ServiceReturn serviceReturn = new ManualService().moveRow(userNick, MoveOptions.toMoveOptions(moveOption), idRow);
            ajaxResponse.digest(serviceReturn);
        } catch(ServiceException e) {
            ajaxResponse.setError(e);
        } catch(Exception e) {
            ajaxResponse.setErrorMsg(e);
        }
        return ajaxResponse; 
    }
    
    
}
