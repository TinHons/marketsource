/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "trade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trade.findAll", query = "SELECT t FROM Trade t"),
    @NamedQuery(name = "Trade.findByCompany", query = "SELECT t FROM Trade t WHERE t.company = :company"),
    @NamedQuery(name = "Trade.findByRecordId", query = "SELECT t FROM Trade t WHERE t.recordId = :recordId"),
    @NamedQuery(name = "Trade.findByDateTime", query = "SELECT t FROM Trade t WHERE t.dateTime = :dateTime"),
    @NamedQuery(name = "Trade.findByClosingPrice", query = "SELECT t FROM Trade t WHERE t.closingPrice = :closingPrice"),
    @NamedQuery(name = "Trade.findByTotalSharesTraded", query = "SELECT t FROM Trade t WHERE t.totalSharesTraded = :totalSharesTraded"),
    @NamedQuery(name = "Trade.findByTotalValueTraded", query = "SELECT t FROM Trade t WHERE t.totalValueTraded = :totalValueTraded")})
public class Trade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "recordId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer recordId;
//    @Size(max = 12)
//    @Column(name = "Symbol")
//    private String symbol;
    @Column(name = "dateTime")
    private Date dateTime;
    @ManyToOne(optional=false)
    @JoinColumn(name="Symbol",referencedColumnName="Symbol")
    private Company company;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "closingPrice",precision=11,scale=6)
    private BigDecimal closingPrice;
    @Column(name = "totalSharesTraded")
    private BigInteger totalSharesTraded;
    @Column(name = "totalValueTraded",precision=17,scale=6)
    private BigDecimal totalValueTraded;

    public Trade() {
    }

    public Trade(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    

    
    
    

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    

    

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public BigInteger getTotalSharesTraded() {
        return totalSharesTraded;
    }

    public void setTotalSharesTraded(BigInteger totalSharesTraded) {
        this.totalSharesTraded = totalSharesTraded;
    }

    public BigDecimal getTotalValueTraded() {
        return totalValueTraded;
    }

    public void setTotalValueTraded(BigDecimal totalValueTraded) {
        this.totalValueTraded = totalValueTraded;
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
        if (!(object instanceof Trade)) {
            return false;
        }
        Trade other = (Trade) object;
        if ((this.recordId == null && other.recordId != null) || (this.recordId != null && !this.recordId.equals(other.recordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "marketsource.entity.Trade[ recordId=" + recordId + " ]";
        
        return "marketsource.entity.Trade[ recordId=" + recordId
                + ",company=" + company
                +",dateTime=" + dateTime
                + ",closingPrice="  + closingPrice
                + ",totalSharesTraded=" + totalSharesTraded
                +",totalValueTraded=" + totalValueTraded + " ]";
    }
    
}
