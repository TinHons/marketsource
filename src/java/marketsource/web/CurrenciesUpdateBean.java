/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import marketsource.ejb.AdminBean;
import marketsource.ejb.BusinessBean;
import marketsource.util.CurrencySheet;

/**
 *
 * @author dell
 */
@Named(value = "currenciesUpdateBean")
@SessionScoped
public class CurrenciesUpdateBean implements Serializable {

    @EJB
    private AdminBean adminBean;

    @EJB
    private BusinessBean businessBean;

    private List<CurrencySheet> currencyList;

    private List<CurrencySheet> majorCurrencyList;

    /**
     * Creates a new instance of CurrenciesUpdateBean
     */
    public CurrenciesUpdateBean() {
    }

    public List<CurrencySheet> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<CurrencySheet> currencyList) {
        this.currencyList = currencyList;
    }

    public List<CurrencySheet> getMajorCurrencyList() {
        return majorCurrencyList;
    }

    public void setMajorCurrencyList(List<CurrencySheet> majorCurrencyList) {
        this.majorCurrencyList = majorCurrencyList;
    }

    public void downloadLatestRates() {

        //Get currency data from the ECB
        Client client = ClientBuilder.newClient();
        String data = client.target("http://api.fixer.io/latest?base=USD")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);

        System.out.println(data);
        adminBean.updateCurrenciesData(data);

    }

    public void retrieveLatestRates() {

        setCurrencyList(businessBean.retrieveCurrencyRates());
        System.out.println(getCurrencyList());

        //set major currencies list
        String[] currencyCodes = {"AUD", "CAD","EUR", "GBP","JPY","NZD", "ZAR"};
        List<String> majorCurrencyCodes = Arrays.asList(currencyCodes);
this.majorCurrencyList = new ArrayList<CurrencySheet>();
        for (CurrencySheet c : this.currencyList) {
            if (majorCurrencyCodes.contains(c.getCode())) {

                this.majorCurrencyList.add(c);
                System.out.println(this.majorCurrencyList.size() + "This is the size");
            } else {

            }

        }
    }

    public Date getCurrencyDate() {

        return Date.valueOf(currencyList.get(0).getDatey());
    }

}
