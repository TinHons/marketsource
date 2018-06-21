/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "zsemarketindices")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zsemarketindices.findAll", query = "SELECT z FROM Zsemarketindices z"),
    @NamedQuery(name = "Zsemarketindices.findByDateOfTrade", query = "SELECT z FROM Zsemarketindices z WHERE z.dateOfTrade = :dateOfTrade"),
    @NamedQuery(name = "Zsemarketindices.findByIndustrialIndex", query = "SELECT z FROM Zsemarketindices z WHERE z.industrialIndex = :industrialIndex"),
    @NamedQuery(name = "Zsemarketindices.findByMiningIndex", query = "SELECT z FROM Zsemarketindices z WHERE z.miningIndex = :miningIndex")})
public class Zsemarketindices implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateOfTrade")

    private Date dateOfTrade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "industrialIndex")
    private Double industrialIndex;
    @Column(name = "miningIndex")
    private Double miningIndex;
    
    @Column(name = "allShareIndex")
    private Double allShareIndex;
    
    @Column(name = "top10Index")
    private Double top10Index;

    public Zsemarketindices() {
    }

    public Zsemarketindices(Date dateOfTrade) {
        this.dateOfTrade = dateOfTrade;
    }

    public Date getDateOfTrade() {
        return dateOfTrade;
    }

    public void setDateOfTrade(Date dateOfTrade) {
        this.dateOfTrade = dateOfTrade;
    }

    public Double getIndustrialIndex() {
        return industrialIndex;
    }

    public void setIndustrialIndex(Double industrialIndex) {
        this.industrialIndex = industrialIndex;
    }

    public Double getMiningIndex() {
        return miningIndex;
    }

    public void setMiningIndex(Double miningIndex) {
        this.miningIndex = miningIndex;
    }

    public Double getAllShareIndex() {
        return allShareIndex;
    }

    public void setAllShareIndex(Double allShareIndex) {
        this.allShareIndex = allShareIndex;
    }

    public Double getTop10Index() {
        return top10Index;
    }

    public void setTop10Index(Double top10Index) {
        this.top10Index = top10Index;
    }

    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dateOfTrade != null ? dateOfTrade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zsemarketindices)) {
            return false;
        }
        Zsemarketindices other = (Zsemarketindices) object;
        if ((this.dateOfTrade == null && other.dateOfTrade != null) || (this.dateOfTrade != null && !this.dateOfTrade.equals(other.dateOfTrade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "marketsource.entity.Zsemarketindices[ dateOfTrade=" + dateOfTrade 
                + " All Share Index=" + allShareIndex
                + " Top 10 Index=" + top10Index
                + " Industrial Index=" + industrialIndex
                + " Mining Index=" + miningIndex
                +" ]";
    }

}
