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
@Table(name = "zseprocesseddate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zseprocesseddate.findAll", query = "SELECT z FROM Zseprocesseddate z"),
    @NamedQuery(name = "Zseprocesseddate.findByProcessedDate", query = "SELECT z FROM Zseprocesseddate z WHERE z.processedDate = :processedDate")})
public class Zseprocesseddate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "processedDate")
    
    private Date processedDate;

    public Zseprocesseddate() {
    }

    public Zseprocesseddate(Date processedDate) {
        this.processedDate = processedDate;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (processedDate != null ? processedDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zseprocesseddate)) {
            return false;
        }
        Zseprocesseddate other = (Zseprocesseddate) object;
        if ((this.processedDate == null && other.processedDate != null) || (this.processedDate != null && !this.processedDate.equals(other.processedDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "marketsource.entity.Zseprocesseddate[ processedDate=" + processedDate + " ]";
    }
    
}
