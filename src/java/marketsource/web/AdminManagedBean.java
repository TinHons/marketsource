/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import marketsource.ejb.AdminBean;

/**
 *
 * @author dell
 */
@Named(value = "adminManagedBean")
@ConversationScoped
public class AdminManagedBean implements Serializable {
    
    @EJB
    private AdminBean adminBean;

    private int companyId;
    

    private String ISIN;

    private String symbol;
    
    private String companyName;

    /**
     * Get the value of companyName
     *
     * @return the value of companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set the value of companyName
     *
     * @param companyName new value of companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    /**
     * Get the value of symbol
     *
     * @return the value of symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Set the value of symbol
     *
     * @param symbol new value of symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Get the value of ISIN
     *
     * @return the value of ISIN
     */
    public String getISIN() {
        return ISIN;
    }

    /**
     * Set the value of ISIN
     *
     * @param ISIN new value of ISIN
     */
    public void setISIN(String ISIN) {
        this.ISIN = ISIN;
    }

    /**
     * Get the value of companyId
     *
     * @return the value of companyId
     */
    public int getCompanyId() {
        return companyId;
    }

    /**
     * Set the value of companyId
     *
     * @param companyId new value of companyId
     */
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    /**
     * Creates a new instance of AdminManagedBean
     */
    public AdminManagedBean() {
    }

    public void addCompany(String companyName, String ISIN, String symbol) {
        System.out.println(companyName + " " +  ISIN + " " + symbol);
        
        adminBean.addCompany(companyName, ISIN, symbol);
        
        

    }

    public void deleteCompany(int companyId) {
        System.out.println("Done!");

    }

}
