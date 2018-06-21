/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import marketsource.ejb.AdminBean;

/**
 *
 * @author dell
 */
@Named(value = "dailySheetUploadBean")
@RequestScoped
public class DailySheetUploadBean {
    
    @EJB
    private AdminBean adminBean;

    private Part uploadedFile;
    private String text;

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Creates a new instance of FileUploadBean
     */
    public DailySheetUploadBean() {
    }

    public void upload() {

        if (null != uploadedFile) {
            try {
                InputStream is = uploadedFile.getInputStream();
                text = new Scanner(is).useDelimiter("\\A").next();
                adminBean.uploadDailySheet(uploadedFile);
            } catch (IOException ex) {
                
                ex.printStackTrace();
            }
        }
    }

}
