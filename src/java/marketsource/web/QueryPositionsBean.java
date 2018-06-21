/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import marketsource.ejb.BusinessBean;
import marketsource.entity.Person;
import marketsource.entity.Position;

/**
 *
 * @author dell
 */
@Named(value = "queryPositionsBean")
@SessionScoped
public class QueryPositionsBean implements Serializable {

    @EJB
    private BusinessBean businessBean;

    private List<Position> positionsList;

    private Person person;

    /**
     * Creates a new instance of QueryPositionsBean
     */
    public QueryPositionsBean() {
    }

    public List<Position> getPositionsList() {
        return positionsList;
    }

    public void setPositionsList(List<Position> positionsList) {
        this.positionsList = positionsList;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void retrievePositions() {
        setPositionsList(businessBean.retrievePersonPositions(person));

    }

}
