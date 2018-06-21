/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import marketsource.ejb.BusinessBean;
import marketsource.entity.Company;

/**
 *
 * @author dell
 */
@Named(value = "investorBoardsBean")
@SessionScoped
public class InvestorBoardsBean implements Serializable {

    @EJB
    private BusinessBean businessBean;

    private List<Company> companiesList;

    /**
     * Creates a new instance of InvestorBoardsBean
     */
    public InvestorBoardsBean() {
    }

    public List<Company> getCompaniesList() {
        return companiesList;
    }

    public void setCompaniesList(List<Company> companiesList) {
        this.companiesList = companiesList;
    }

    public void retrieveCompaniesList() {
        setCompaniesList(businessBean.retrieveZseCompanies());

    }

}
