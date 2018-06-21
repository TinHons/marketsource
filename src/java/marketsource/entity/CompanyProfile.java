/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "companyType", discriminatorType = DiscriminatorType.STRING, length = 10)
@DiscriminatorValue("PRIVATE")
@Table(name = "company_profile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompanyProfile.findAll", query = "SELECT c FROM CompanyProfile c"),
    @NamedQuery(name = "CompanyProfile.findByRecordId", query = "SELECT c FROM CompanyProfile c WHERE c.recordId = :recordId"),
    @NamedQuery(name = "CompanyProfile.findByName", query = "SELECT c FROM CompanyProfile c WHERE c.name = :name"),
    @NamedQuery(name = "CompanyProfile.findByProducts", query = "SELECT c FROM CompanyProfile c WHERE c.products = :products")})
public class CompanyProfile implements Serializable, Comparable<CompanyProfile> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "record_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer recordId;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "company_overview")
    private String companyOverview;
    @Size(max = 500)
    @Column(name = "products")
    private String products;
    //revenue in USD
    @Column(name = "revenue", precision = 20, scale = 2)
    private BigDecimal revenue;
    @Size(max = 100)
    @Column(name = "revenue_description")
    private String revenueDescription;
    @Column(name = "companyType", length = 10)
    private String companyType;
    
    @OneToMany(cascade=CascadeType.ALL,mappedBy = "profile",targetEntity = Position.class,fetch=FetchType.EAGER)
    private List<Position> role;

    @Embedded
    private Address address;

    public CompanyProfile() {
    }

    public CompanyProfile(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getRecordId() {
        return recordId;
    }

//    public void setRecordId(Integer recordId) {
//        this.recordId = recordId;
//    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Position> getRole() {
        return role;
    }

    public void setRole(List<Position> role) {
        this.role = role;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordId != null ? recordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyProfile)) {
            return false;
        }
        CompanyProfile other = (CompanyProfile) object;
        if ((this.recordId == null && other.recordId != null) || (this.recordId != null && !this.recordId.equals(other.recordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "marketsource.entity.CompanyProfile[ recordId=" + recordId + " ]";
    }

    @Override
    public int compareTo(CompanyProfile other) {

        return this.name.compareTo(other.name);

    }

}
