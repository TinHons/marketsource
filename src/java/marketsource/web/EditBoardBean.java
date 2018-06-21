/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import marketsource.ejb.AdminBean;
import marketsource.entity.Company;

/**
 *
 * @author dell
 */
@Named(value = "editBoardBean")
@SessionScoped
public class EditBoardBean implements Serializable {

    @EJB
    private AdminBean adminBean;

    private Company selectedZseCompany;

    private List<Company> zseCompaniesList;

    private String companyName;
    
    private String companySymbol;

    /**
     * Creates a new instance of EditBoardBean
     */
    public EditBoardBean() {
    }

    public Company getSelectedZseCompany() {
        return selectedZseCompany;
    }

    public void setSelectedZseCompany(Company selectedZseCompany) {
        this.selectedZseCompany = selectedZseCompany;
    }

    public List<Company> getZseCompaniesList() {
        return zseCompaniesList;
    }

    public void setZseCompaniesList(List<Company> zseCompaniesList) {
        this.zseCompaniesList = zseCompaniesList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanySymbol() {
        return companySymbol;
    }

    public void setCompanySymbol(String companySymbol) {
        this.companySymbol = companySymbol;
    }
    
    

    public String retrieveBoardInfo(String symbol, String companyName) {
        setCompanySymbol(symbol);
        setCompanyName(companyName);
        setZseCompaniesList(adminBean.retrieveZseCompaniesList());
        return "editBoard";
    }

    public void updatePeers() {
        adminBean.addPeer(companySymbol, selectedZseCompany.getSymbol());
        System.out.println("Selected peer: " + selectedZseCompany);
        System.out.println("Peers updated...");
    }

}
