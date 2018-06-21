/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author dell
 */
@Entity
@DiscriminatorValue("PUBLIC")
public class ZseCompanyProfile extends CompanyProfile implements Serializable {

    @OneToOne(mappedBy = "profile",targetEntity = Company.class)
    private Company zseCompany;

    public Company getZseCompany() {
        return zseCompany;
    }

    public void setZseCompany(Company zseCompany) {
        this.zseCompany = zseCompany;
    }
    
    

}
