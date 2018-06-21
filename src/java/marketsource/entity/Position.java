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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "position")
public class Position implements Serializable {
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "record_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer record_id;
    
    @ManyToOne(optional=false)
    @JoinColumn(name = "personId", referencedColumnName="personId")
    private Person person;
    

    @ManyToOne(optional=false)
    @JoinColumn(name = "profile_id", referencedColumnName="record_id")
    private CompanyProfile profile;  
    
    private String title;

    public CompanyProfile getProfile() {
        return profile;
    }

    public void setProfile(CompanyProfile profile) {
        this.profile = profile;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Integer getRecord_id() {
        return record_id;
    }

    public void setRecord_id(Integer record_id) {
        this.record_id = record_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    
    
    

}
