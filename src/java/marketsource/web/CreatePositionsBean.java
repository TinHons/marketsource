/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import marketsource.ejb.AdminBean;
import marketsource.entity.CompanyProfile;
import marketsource.entity.Person;
import marketsource.entity.Position;

/**
 *
 * @author dell
 */
@Named(value = "createPositionsBean")
@SessionScoped
public class CreatePositionsBean implements Serializable {

    @EJB
    private AdminBean adminBean;

    private String title;

    private Person person;

    private CompanyProfile profile;

    private List<Person> personsList;

    private List<CompanyProfile> profileList;

    private String result;

    /**
     * Creates a new instance of CreatePositionsBean
     */
    public CreatePositionsBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public CompanyProfile getProfile() {
        return profile;
    }

    public void setProfile(CompanyProfile profile) {
        this.profile = profile;
    }

    public List<Person> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(List<Person> personsList) {
        this.personsList = personsList;
    }

    public List<CompanyProfile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<CompanyProfile> profileList) {
        this.profileList = profileList;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    

    public String createPosition() {
        System.out.println(profile + " " + person);
        Position position = new Position();
        position.setProfile(profile);
        position.setPerson(person);
        if (title != null) {
            position.setTitle(title);
        }
        boolean createResult = adminBean.persistPosition(position);
        
        if (createResult) {
            result = "Position successfully created!";
        } else {
            result = "Person already has a role in this company!";
        
        }
        return "success";
    }

    public String retrievePositionsData() {

        setProfileList(adminBean.retrieveCompanies());

        setPersonsList(adminBean.retrievePersons());
        personsList.sort(Comparator.comparing(Person::getName));
        return "createpositions";
    }

}
