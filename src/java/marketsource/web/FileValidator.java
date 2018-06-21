/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import java.io.InputStream;
import java.util.Scanner;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.*;           
import javax.servlet.http.Part;
import marketsource.ejb.AdminBean;

/**
 *
 * @author dell
 */

@FacesValidator(value="FileValidator")
public class FileValidator implements Validator{
    @EJB
    private AdminBean adminBean;
    
 @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Part file = (Part) value;
        String text = "";
        boolean exists = false;

        try {
            InputStream is = file.getInputStream();
            text = new Scanner(is).useDelimiter("\\A").next();
            exists = adminBean.isDateProcessed(file);
            // Do not accept an upload unless it contains the string
            // JSR-344
        } catch (Exception ex) {
            throw new ValidatorException(new FacesMessage("Invalid file"), ex);
        }
        if (!text.contains("REG")) {
            throw new ValidatorException(new FacesMessage("Invalid file.  File must contain special string"));
        }
        
        if (exists) {
            throw new ValidatorException(new FacesMessage("Invalid file.  Date already processed"));
        }

    }
    
}

