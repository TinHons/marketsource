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
@Table(name = "dataproperty")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dataproperty.findAll", query = "SELECT d FROM Dataproperty d"),
    @NamedQuery(name = "Dataproperty.findByProperty", query = "SELECT d FROM Dataproperty d WHERE d.property = :property"),
    @NamedQuery(name = "Dataproperty.findByPropertyValue", query = "SELECT d FROM Dataproperty d WHERE d.propertyValue = :propertyValue")})
public class Dataproperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "property")
    private String property;
    @Size(max = 30)
    @Column(name = "propertyValue")
    private String propertyValue;

    public Dataproperty() {
    }

    public Dataproperty(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (property != null ? property.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dataproperty)) {
            return false;
        }
        Dataproperty other = (Dataproperty) object;
        if ((this.property == null && other.property != null) || (this.property != null && !this.property.equals(other.property))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "marketsource.entity.Dataproperty[ property=" + property + " ]";
    }
    
}
