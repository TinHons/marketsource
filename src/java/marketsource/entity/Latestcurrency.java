/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "latestcurrency")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Latestcurrency.findAll", query = "SELECT l FROM Latestcurrency l"),
    @NamedQuery(name = "Latestcurrency.findByDatey", query = "SELECT l FROM Latestcurrency l WHERE l.datey = :datey"),
    @NamedQuery(name = "Latestcurrency.findByCurrency", query = "SELECT l FROM Latestcurrency l WHERE l.currency = :currency"),
    @NamedQuery(name = "Latestcurrency.findByRate", query = "SELECT l FROM Latestcurrency l WHERE l.rate = :rate")})
public class Latestcurrency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "datey")
    
    private Date datey;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "currency")
    private String currency;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rate",precision=11,scale=6)
    private BigDecimal rate;

    public Latestcurrency() {
    }

    public Latestcurrency(String currency) {
        this.currency = currency;
    }

    public Date getDatey() {
        return datey;
    }

    public void setDatey(Date datey) {
        this.datey = datey;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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
        if (!(object instanceof Latestcurrency)) {
            return false;
        }
        Latestcurrency other = (Latestcurrency) object;
        if ((this.currency == null && other.currency != null) || (this.currency != null && !this.currency.equals(other.currency))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "marketsource.entity.Latestcurrency[ currency=" + currency + " ]";
    }
    
}
