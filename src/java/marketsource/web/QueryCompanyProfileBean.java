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
import marketsource.entity.CompanyProfile;
import marketsource.entity.Position;

/**
 *
 * @author dell
 */
@Named(value = "queryCompanyProfileBean")
@SessionScoped
public class QueryCompanyProfileBean implements Serializable {

    @EJB
    private BusinessBean businessBean;

    private CompanyProfile profile;

    private List<CompanyProfile> companyProfileList;

    private Integer recordId;

    private List<Position> positionsList;

    /**
     * Creates a new instance of QueryCompanyProfileBean
     */
    public QueryCompanyProfileBean() {
    }

    public CompanyProfile getProfile() {
        return profile;
    }

    public void setProfile(CompanyProfile profile) {
        this.profile = profile;
    }

    public List<CompanyProfile> getCompanyProfileList() {
        return companyProfileList;
    }

    public void setCompanyProfileList(List<CompanyProfile> companyProfileList) {
        this.companyProfileList = companyProfileList;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public List<Position> getPositionsList() {
        return positionsList;
    }

    public void setPositionsList(List<Position> positionsList) {
        this.positionsList = positionsList;
    }

//    public String retrieveCompanyInfo(Integer profileId) {
//
//        setProfile(businessBean.retrieveCompanyProfile(profileId));
//
//        return "companyprofile";
//
//    }
    public void retrieveCompanyInfo() {

        Integer profileId = this.recordId;

        for (CompanyProfile profile : companyProfileList) {

            Integer id = profile.getRecordId();
            if (profileId.equals(id)) {

                setProfile(profile);

            }

        }
        //retrive positions
        //Integer id = this.company.getProfile().getRecordId();
        List<Position> positionsList = businessBean.retrieveProfilePositions(profileId);
        System.out.println(positionsList);

        setPositionsList(positionsList);
        //return "companyprofile";
    }

    public void retrieveCompaniesList() {

        setCompanyProfileList(businessBean.retrieveCompanies());

    }

}
