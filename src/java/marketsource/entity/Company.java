/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "company")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c"),

    @NamedQuery(name = "Company.findByCompanyName", query = "SELECT c FROM Company c WHERE c.companyName = :companyName"),
    @NamedQuery(name = "Company.findByIsin", query = "SELECT c FROM Company c WHERE c.isin = :isin"),

    @NamedQuery(name = "Company.findBySymbol", query = "SELECT c FROM Company c WHERE c.symbol = :symbol")})
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "companyId")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Short companyId;
    @Size(max = 70)
    @Column(name = "companyName")
    private String companyName;
    @Size(max = 24)
    @Column(name = "ISIN")
    private String isin;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(max = 12)
    @Column(name = "Symbol")
    private String symbol;
    @OneToMany(mappedBy = "company", targetEntity = Trade.class,
            fetch = FetchType.EAGER)
    private Collection trades;

    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "record_id")
    private ZseCompanyProfile profile;

    @JoinTable(name = "peer_detail", joinColumns = {
        @JoinColumn(name = "principal_company", referencedColumnName = "Symbol", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "peer", referencedColumnName = "Symbol", nullable = false)})
    @ManyToMany
    private Collection<Company> peers;

    public Company() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Collection getTrades() {
        return trades;
    }

    public void setTrades(Collection trades) {
        this.trades = trades;
    }

    public ZseCompanyProfile getProfile() {
        return profile;
    }

    public void setProfile(ZseCompanyProfile profile) {
        this.profile = profile;
    }

    public Collection<Company> getPeers() {
        return peers;
    }

    public void setPeers(Collection<Company> peers) {
        this.peers = peers;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (symbol != null ? symbol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.symbol == null && other.symbol != null) || (this.symbol != null && !this.symbol.equals(other.symbol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return symbol;
        // return "marketsource.entity.Company[ symbol=" + symbol + " ]";
    }

}
