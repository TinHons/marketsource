/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.validation.constraints.Digits;
import marketsource.ejb.AdminBean;
import marketsource.entity.Address;
import marketsource.entity.Company;
import marketsource.entity.CompanyProfile;
import marketsource.entity.ZseCompanyProfile;
import marketsource.util.CityEnum;

/**
 *
 * @author dell
 */
@Named(value = "companyProfileBean")
@SessionScoped
public class CompanyProfileBean implements Serializable {
    
    @EJB
    private AdminBean adminBean;
    
    private String profileName;
    
    private String companyOverview;
    
    private String products;
    
    @Digits(integer=11, fraction=2)
    private BigDecimal revenue;
    
    private String revenueDescription;
    
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private CityEnum city;
    private String phoneNumber;
    
    
    

    private List<Company> zseCompaniesList;
    
    
    
    private Company selectedZseCompany;
    
   
    /**
     * Creates a new instance of CompanyProfileBean
     */
    public CompanyProfileBean() {
    }

   
    
    

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getCompanyOverview() {
        return companyOverview;
    }

    public void setCompanyOverview(String companyOverview) {
        this.companyOverview = companyOverview;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public String getRevenueDescription() {
        return revenueDescription;
    }

    public void setRevenueDescription(String revenueDescription) {
        this.revenueDescription = revenueDescription;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public CityEnum getCity() {
        return city;
    }

    public void setCity(CityEnum city) {
        this.city = city;
    }

    

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    

    

    

    

    
    
    
public CityEnum[] getCityValues(){
        
        return CityEnum.values();
        
    }
    

    
    
    
    public String createPublicProfile(){
        System.out.println("Creating Public Profile...");
       System.out.println(profileName + companyOverview + products + revenue + selectedZseCompany);
    ZseCompanyProfile profile = new ZseCompanyProfile();
    profile.setName(profileName);
    profile.setCompanyOverview(companyOverview);
    profile.setProducts(products);
    profile.setRevenue(revenue);
    profile.setRevenueDescription(revenueDescription);
    profile.setZseCompany(selectedZseCompany);
    
    Address address = new Address();
    address.setLine1(addressLine1);
    address.setLine2(addressLine2);
    address.setLine3(addressLine3);
    address.setCity(city.toString());
    address.setPhoneNumber(phoneNumber);
    
    profile.setAddress(address);
    
    
    
    adminBean.persistProfile(profile);
    return "success";
   
    }
    
    public String createPrivateProfile(){
        System.out.println("Creating Public Profile...");
       System.out.println(profileName + companyOverview + products + revenue);
    CompanyProfile profile = new CompanyProfile();
    profile.setName(profileName);
    profile.setCompanyOverview(companyOverview);
    profile.setProducts(products);
    profile.setRevenue(revenue);
    profile.setRevenueDescription(revenueDescription);
    
    Address address = new Address();
    address.setLine1(addressLine1);
    address.setLine2(addressLine2);
    address.setLine3(addressLine3);
    address.setCity(city.toString());
    address.setPhoneNumber(phoneNumber);
    
    profile.setAddress(address);
    
    
    
    
    
    adminBean.persistProfile(profile);
    return "success";
   
    }
    
    public String retrieveZseCompanies(){
    System.out.println("Retrieving companies.....");
        setZseCompaniesList(adminBean.retrieveZseCompaniesList());
        
        
    
    
    
    return "createpublic";
    }
    
    
    
    
    
}
