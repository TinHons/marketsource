/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import marketsource.ejb.BusinessBean;
import marketsource.entity.CompanyProfile;
import marketsource.entity.Person;

/**
 *
 * @author dell
 */
@FacesConverter(forClass = CompanyProfile.class)
public class CompanyProfileConverter implements Converter{

    @EJB
    private BusinessBean businessBean;

    @Override
    public Object getAsObject(FacesContext context,
            UIComponent component, String newValue)
            throws ConverterException {

        if (newValue.isEmpty()) {
            return null;
        }
        // Since this is only a String to String conversion,
        // this conversion does not throw ConverterException.

        String convertedValue = newValue.trim();
        System.out.println("From converter: convertedValue=" + convertedValue);
        String[] idArray = convertedValue.split("=");
        String[] idArray2 = idArray[1].split(" ");
        String idString = idArray2[0];
        Integer id = Integer.valueOf(idString).intValue();
        //String symbol = newValue.trim();
        System.out.println("Symbol from converter: " + id);
        //new code
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("marketsourcePU");
        EntityManager em = entityManagerFactory.createEntityManager();
        //EntityTransaction userTransaction = em.getTransaction();

//userTransaction.begin();
        CompanyProfile profile = (CompanyProfile) em.createNamedQuery("CompanyProfile.findByRecordId")
                .setParameter("recordId", id)
                .getSingleResult();
        System.out.println(profile);
        em.close();
        entityManagerFactory.close();
        // Company company = new Company();

        //company.setSymbol(symbol);
        return profile;

    }

    @Override
    public String getAsString(FacesContext context,
            UIComponent component, Object value)
            throws ConverterException {

        String inputVal = null;
        if (value == null) {
            return "";
        }

        if (value instanceof CompanyProfile) {
            return "'marketsource.entity..CompanyProfile[ recordId=" + ((CompanyProfile) value).getRecordId() + " ]";
        } else {
            throw new ConverterException(new FacesMessage(value + " is not a valid instance of Company"));
        }

    }
    
}
