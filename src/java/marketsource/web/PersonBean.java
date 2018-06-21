/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import marketsource.ejb.AdminBean;
import marketsource.ejb.BusinessBean;
import marketsource.entity.Person;

/**
 *
 * @author dell
 */
@Named(value = "personBean")
@SessionScoped
public class PersonBean implements Serializable {

    @EJB
    private AdminBean adminBean;
    
    @EJB
    private BusinessBean businessBean;

    private String firstName;

    private String secondName;

    private String surname;

    private Person person;

    private List<Person> personsList;
    
    private String result;

    /**
     * Creates a new instance of PersonBean
     */
    public PersonBean() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Person> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(List<Person> personsList) {
        this.personsList = personsList;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    

    public String createPerson() {

        System.out.println(surname);

        adminBean.persistPerson(firstName, secondName, surname);
result = "Person created successfully!";
        return "success";
    }

    public String updatePerson() {

        adminBean.updatePerson(person);
        return "success";
    }

    public String retrievePersonsList() {
        setPersonsList(adminBean.retrievePersons());
        personsList.sort(Comparator.comparing(Person::getName));
        return "editPersonsList";
    }
    
    public void retrievePersonsList_2() {
        setPersonsList(businessBean.retrievePersons());
       personsList.sort(Comparator.comparing(Person::getName));
        
    }

    public String retrievePersonInfo(Person person) {
        setPerson(person);
        System.out.println("retrieving person....");
        return "editperson";
    }

    public String deletePerson(Person person) {
        System.out.println("deleteing person....");
        return "success";
    }

}
