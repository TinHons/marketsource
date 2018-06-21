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
import javax.faces.convert.ConverterException;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import marketsource.ejb.BusinessBean;
import marketsource.entity.Company;

/**
 *
 * @author dell
 */
@FacesConverter(forClass = Company.class)
public class CompanyConverter implements Converter {

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

//        String convertedValue = newValue.trim();
//        System.out.println("From converter: convertedValue=" + convertedValue);
//        String[] symbolArray = convertedValue.split("=");
//        String[] symbolArray2 = symbolArray[1].split(" ");
//        String symbol = symbolArray2[0];
        String symbol = newValue.trim();
        System.out.println("Symbol from converter: " + symbol);
        //new code
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("marketsourcePU");
        EntityManager em = entityManagerFactory.createEntityManager();
        //EntityTransaction userTransaction = em.getTransaction();

//userTransaction.begin();
        Company company = (Company) em.createNamedQuery("Company.findBySymbol")
                .setParameter("symbol", symbol)
                .getSingleResult();
        em.close();
        entityManagerFactory.close();
        // Company company = new Company();

        //company.setSymbol(symbol);
        return company;

    }

    @Override
    public String getAsString(FacesContext context,
            UIComponent component, Object value)
            throws ConverterException {

        String inputVal = null;
        if (value == null) {
            return "";
        }

        if (value instanceof Company) {
            return ((Company) value).getSymbol();
        } else {
            throw new ConverterException(new FacesMessage(value + " is not a valid instance of Company"));
        }

    }

}
