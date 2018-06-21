/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author dell
 */
@Named(value = "styleBean")
@SessionScoped
public class StyleBean implements Serializable {
    
    

    /**
     * Creates a new instance of StyleBean
     */
    public StyleBean() {
    }

    

    public String getShareChangeColor(int indicator) {
        String colorString = null;
    
     
       
        switch (indicator) {
            case 1:  colorString = "green";
                     break;
            case -1:  colorString = "red";
                     break;
            
            case 0:  colorString = "grey";
                     break;
            
        }
        
        return colorString;
    }
    
    public boolean showUpArrow(int indicator){
    boolean display = true;
    
    switch (indicator) {
            case 1:  display = true;
                     break;
            case -1:  display = false;
                     break;
            
            case 0:  display = false;
                     break;
            
        }
    
    return display;
    
    
    }
    
    public boolean showDownArrow(int indicator){
    boolean display = true;
    
    switch (indicator) {
            case 1:  display = false;
                     break;
            case -1:  display = true;
                     break;
            
            case 0:  display = false;
                     break;
            
        }
    
    return display;
    
    
    }
    
    public boolean showSquareIcon(int indicator){
    boolean display = true;
    
    switch (indicator) {
            case 1:  display = false;
                     break;
            case -1:  display = false;
                     break;
            
            case 0:  display = true;
                     break;
            
        }
    
    return display;
    
    
    }
    
    

    
    
   
    
    
    
    
    
}
