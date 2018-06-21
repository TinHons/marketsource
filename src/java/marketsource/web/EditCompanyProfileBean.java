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
import marketsource.ejb.AdminBean;
import marketsource.entity.Company;
import marketsource.entity.CompanyProfile;
import marketsource.entity.Position;
import marketsource.entity.ZseCompanyProfile;
import marketsource.util.CityEnum;

/**
 *
 * @author dell
 */
@Named(value = "editCompanyProfileBean")
@SessionScoped
public class EditCompanyProfileBean implements Serializable {

    @EJB
    private AdminBean adminBean;

    private List<CompanyProfile> companyProfileList;

    private CompanyProfile profile;

    private List<Company> zseCompaniesList;

    private Company selectedZseCompany;

    private List<Position> positionsList;

    /**
     * Creates a new instance of EditCompanyProfileBean
     */
    public EditCompanyProfileBean() {
    }

    public List<CompanyProfile> getCompanyProfileList() {
        return companyProfileList;
    }

    public void setCompanyProfileList(List<CompanyProfile> companyProfileList) {
        this.companyProfileList = companyProfileList;
    }

    public CompanyProfile getProfile() {
        return profile;
    }

    public void setProfile(CompanyProfile profile) {
        this.profile = profile;
    }

    public List<Company> getZseCompaniesList() {
        return zseCompaniesList;
    }

    public void setZseCompaniesList(List<Company> zseCompaniesList) {
        this.zseCompaniesList = zseCompaniesList;
    }

    public Company getSelectedZseCompany() {
        return selectedZseCompany;
    }

    public void setSelectedZseCompany(Company selectedZseCompany) {
        this.selectedZseCompany = selectedZseCompany;
    }

    public List<Position> getPositionsList() {
        return positionsList;
    }

    public void setPositionsList(List<Position> positionsList) {
        this.positionsList = positionsList;
    }

    public String retrieveCompaniesList() {

        setCompanyProfileList(adminBean.retrieveCompanies());

        return "editcompanies";
    }

    public String retrieveCompanyInfo(Integer profileId) {

        for (CompanyProfile profile : companyProfileList) {

            Integer id = profile.getRecordId();
            if (profileId.equals(id)) {

                setProfile(profile);

            }

            List<Position> positionsList = adminBean.retrieveProfilePositions(profileId);
            System.out.println(positionsList);

            setPositionsList(positionsList);

        }

        if (profile instanceof ZseCompanyProfile) {
            setZseCompaniesList(adminBean.retrieveZseCompaniesList());
            System.out.println(zseCompaniesList);
            for (Company company : zseCompaniesList) {

                if (company.getProfile() != null) {

                    Integer extractedProfileId = company.getProfile().getRecordId();
                    if (extractedProfileId.equals(profileId)) {

                        setSelectedZseCompany(company);
                    }

                }

            }
        }
        return "editcompanyprofile";
    }

    public String deletePosition(Integer id) {

        adminBean.deletePosition(id);

        return "success";
    }

    public void deleteCompanyProfile(CompanyProfile profile) {

        adminBean.deleteCompanyProfile(profile);

    }

    public String updateCompanyProfile() {
        if (isZseCompany()) {
            ZseCompanyProfile profile_1 = (ZseCompanyProfile) profile;

            profile_1.setZseCompany(selectedZseCompany);
            System.out.println("From editCompanyProfile: ..." + profile.getAddress().getLine1());
            adminBean.updateCompanyProfile(profile_1);
        } else {

            adminBean.updateCompanyProfile(profile);

        }

        return "done";
    }

    public CityEnum[] getCityValues() {

        return CityEnum.values();

    }

    public boolean isZseCompany() {

        if (profile instanceof ZseCompanyProfile) {

            return true;
        } else {
            return false;
        }

    }

}
