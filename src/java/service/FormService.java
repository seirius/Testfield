package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletContext;
import templates.forms.Form;
import templates.forms.FormData;
import templates.forms.inputs.Input;
import templates.validation.Validation;
import templates.validation.server_validator.ServerValidator;
import util.FileUtil;
import util.ServiceNicks;
import util.ServiceReturn;
import util.UrlUtil;
import util.exceptions.ServiceException;

/**
 *
 * @author Andriy
 */
public class FormService extends Service {
    
    public static String loadFormFile(ServletContext servletContext, 
            String formName) throws IOException {
        String realPath = servletContext
                .getRealPath(UrlUtil.getUrl(new String[]{
            "WEB-INF", "form_templates", formName + ".json"
        }));
        return FileUtil.readFile(
                realPath,
                StandardCharsets.UTF_8
        );
    }
    
    public static Form loadForm(ServletContext servletContext, 
            String formName) throws IOException {
        return loadForm(servletContext, formName, Form.class);
    }
    
    public static <T> T loadForm(ServletContext servletContext, 
            String formName, Class<T> s) throws IOException {
        String jsonFile = loadFormFile(servletContext, formName);
        return new ObjectMapper().readValue(jsonFile, s);
    }
    
    public ServiceReturn callService(String calledBy, Form form) throws ServiceException {
        ServiceReturn result = null;
        
        try {
            Class<?> clazz = Class.forName(ServiceNicks.toEnum(form.getServiceClass()).getClassName());
            Object serviceClass = clazz.newInstance();
            Method classMethod = serviceClass.getClass().getMethod(form.getServiceMethod(), Form.class);
            result = (ServiceReturn) classMethod.invoke(serviceClass, form);
        } catch(Exception e) {
            throw new ServiceException("Error with the form.", e);
        }
        
        return result;
    }
    
    public static void callCharger(String chargerName, String chargerMethod, 
            Form form, Input input) throws ServiceException {
        try {
            Class<?> clazz = Class.forName(ServiceNicks.toEnum(chargerName)
                    .getClassName());
            Constructor<?> constructor = clazz.getConstructor(Form.class);
            Object charger = constructor.newInstance(form);
            
            Method classMethod = charger.getClass()
                    .getMethod(chargerMethod, Input.class);
            classMethod.invoke(charger, input);
        } catch(Exception e) {
            throw new ServiceException("Error with the callCharger.", e);
        }
    }
    
    public ServiceReturn callServerValidator(String svName, String svMethod, 
            Input input) throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException, NoSuchMethodException, 
            IllegalArgumentException, InvocationTargetException {
        Class<?> clazz = Class.forName(ServiceNicks.toEnum(svName)
                .getClassName());
        Object svClass = clazz.newInstance();
        ServerValidator sv = (ServerValidator) svClass;
        sv.setManager(MANAGER);
        sv.setRequest(request);

        Method classMethod = sv.getClass()
                .getMethod(svMethod, Input.class);
        return (ServiceReturn) classMethod.invoke(sv, input);
    }
    
    public ServiceReturn loadForm(String formName) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            ObjectNode form = FormService.loadForm(request.getServletContext(), formName, ObjectNode.class);
            result.addItem("form", form);
            
            MANAGER.commit();
        } catch (Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn loadForm(ObjectNode requestedForm) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            
            String formName = requestedForm.get("formName").asText();
            FormData formData = new ObjectMapper()
                    .readValue(requestedForm.get("data").toString(), FormData.class);
            
            Form form = FormService.loadForm(request.getServletContext(), formName);
            form.setFormLoadData(formData);
            form.initInputs(MANAGER, request);
            
            result.addItem("form", form);
            MANAGER.commit();
        } catch (Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
    public ServiceReturn sendForm(ObjectNode form) throws Exception {
        ServiceReturn result = null;
        try {
            MANAGER.beginTransaction();
            
            JsonNode formJson = form.get("form");
            Form dataForm = new ObjectMapper().readValue(formJson.toString(), Form.class);
            dataForm.setRequest(request);
            dataForm.setManager(MANAGER);
            dataForm.validate();
            result = callService("service", dataForm);
            
            MANAGER.commit();
        } catch (Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }

    public ServiceReturn serverValidation(Input input) throws Exception {
        ServiceReturn result = new ServiceReturn();
        try {
            MANAGER.beginTransaction();
            Validation validation = input.getValidation();
            String[] svNames = validation.getServer().split("\\.");
            result = callServerValidator(svNames[0], svNames[1], input);
            MANAGER.commit();
        } catch (Exception e) {
            MANAGER.rollback();
            throw treatException(e);
        } finally {
            MANAGER.close();
        }
        return result;
    }
    
}
