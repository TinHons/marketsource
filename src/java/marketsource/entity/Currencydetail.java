/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "currencydetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Currencydetail.findAll", query = "SELECT c FROM Currencydetail c"),
    @NamedQuery(name = "Currencydetail.findByCurrency", query = "SELECT c FROM Currencydetail c WHERE c.currency = :currency"),
    @NamedQuery(name = "Currencydetail.findByCurrencyName", query = "SELECT c FROM Currencydetail c WHERE c.currencyName = :currencyName")})
public class Currencydetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "currency")
    private String currency;
    @Size(max = 100)
    @Column(name = "currencyName")
    private String currencyName;

    public Currencydetail() {
    }

    public Currencydetail(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currency != null ? currency.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Currencydetail)) {
            return false;
        }
        Currencydetail other = (Currencydetail) object;
        if ((this.currency == null && other.currency != null) || (this.currency != null && !this.currency.equals(other.currency))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "marketsource.entity.Currencydetail[ currency=" + currency + " ]";
    }
    
}
