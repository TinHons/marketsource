/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import marketsource.ejb.BusinessBean;
import marketsource.entity.Company;
import marketsource.entity.Zsemarketindices;
import marketsource.util.IndicesSheet;
import marketsource.util.PriceSheetTrade;

/**
 *
 * @author dell
 */
@Named(value = "queryManagedBean")
@SessionScoped
public class QueryManagedBean implements Serializable {

    @EJB
    private BusinessBean businessBean;

    private List<PriceSheetTrade> priceSheetTradeList;

    private List<Zsemarketindices> dailyIndicesList;

    private List<PriceSheetTrade> topGainersList;

    //This will be a list of one item
    private IndicesSheet dailyIndices;

    private java.util.Date selectedDate;

    /**
     * Creates a new instance of QueryManagedBean
     */
    public QueryManagedBean() {

        //selectedDate = Date.valueOf(businessBean.setInitialDate());
    }

    @PostConstruct
    public void setInitialDate() {
        LocalDate initialDate = null;

        initialDate = businessBean.setInitialDate();

        setSelectedDate(Date.valueOf(initialDate));
    }

    public java.util.Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(java.util.Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public List<Zsemarketindices> getDailyIndicesList() {
        return dailyIndicesList;
    }

    public void setDailyIndicesList(List<Zsemarketindices> dailyIndicesList) {
        this.dailyIndicesList = dailyIndicesList;
    }

    public IndicesSheet getDailyIndices() {
        return dailyIndices;
    }

    public void setDailyIndices(IndicesSheet dailyIndices) {
        this.dailyIndices = dailyIndices;
    }

    public List<PriceSheetTrade> getPriceSheetTradeList() {
        return priceSheetTradeList;
    }

    public void setPriceSheetTradeList(List<PriceSheetTrade> priceSheetTradeList) {
        this.priceSheetTradeList = priceSheetTradeList;
    }

    public List<PriceSheetTrade> getTopGainersList() {
        return topGainersList;
    }

    public void setTopGainersList(List<PriceSheetTrade> topGainersList) {
        this.topGainersList = topGainersList;
    }

    public void retrieveDailyTrades(java.util.Date selectedDate) {
        Logger.getLogger("marketsource").fine(selectedDate.toString());
        //Date date = Date.valueOf(selectedDate);
        //Date date = (Date)selectedDate;
        Date date = new Date(selectedDate.getTime());
        Logger.getLogger("marketsource").fine(date.toString());
        // setDailyTradesList(businessBean.retrieveDailyTradeStatistics(date));
        setPriceSheetTradeList(businessBean.retrieveDailyTradeStatistics(date));

        //Get top gainers
        List<PriceSheetTrade> topGainers = (ArrayList<PriceSheetTrade>) ((ArrayList<PriceSheetTrade>) priceSheetTradeList).clone();

        topGainers.sort(Comparator.comparing(PriceSheetTrade::getChange).reversed());
        List<PriceSheetTrade> topGainers2 = topGainers.subList(0, 5);
        setTopGainersList(topGainers2);
    }

    public String onDateButtonClick() {
        System.out.println("Executed!");
        return null;

    }

    public String viewCompanyInfo(String symbol) {
        return "";
    }

    public void retrieveDailyIndices(java.util.Date selectedDate) {

        Date date = new Date(selectedDate.getTime());

        // setDailyIndicesList(businessBean.retrieveDailyIndicesStatistics(date));
        setDailyIndices(businessBean.retrieveDailyIndicesStatistics(date));

    }

    public PriceSheetTrade retrieveBoardData(Company company) {

        PriceSheetTrade requiredTrade = null;

        for (PriceSheetTrade trade : priceSheetTradeList) {
            if (trade.getCompany().equals(company)) {

                requiredTrade = trade;
                break;

            }

        }

        return requiredTrade;
    }

}
