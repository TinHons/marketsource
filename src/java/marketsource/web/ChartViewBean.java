/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;
import marketsource.ejb.BusinessBean;
import marketsource.entity.Company;
import marketsource.entity.Position;
import marketsource.entity.Trade;
import marketsource.entity.Zsemarketindices;
import marketsource.util.PriceSheetTrade;
import static org.primefaces.component.chart.Chart.PropertyKeys.model;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

/**
 *
 * @author dell
 */
@Named(value = "chartViewBean")
@SessionScoped
public class ChartViewBean implements Serializable {

    @EJB
    private BusinessBean businessBean;

    @Inject
    private QueryManagedBean queryManagedBean;

    @Inject
    private QueryCompanyProfileBean queryCompanyProfileBean;

    private LineChartModel lineModel;

    private BigDecimal minSharePrice;

    private BigDecimal maxSharePrice;

    private Date maxDate;

    private Company company;

    private java.util.Date selectedDate;

    private BigDecimal closingPrice;
    private BigInteger totalSharesTraded;
    private BigDecimal totalValueTraded;
    private BigDecimal percentageChange;
    private BigDecimal absoluteChange;

    private PriceSheetTrade tradeData;

    //Combined chart variables
    private BarChartModel barModel;

    private BigInteger minNumberOfSharesTraded;
    private BigInteger maxNumberOfSharesTraded;

    private List<SelectItem> comparisonList;
    private String selectedComparisonItem;

    private List<Date> indicesDates;

    private List<Date> sharePriceDates;

    private double high52Week;

    private double low52Week;

    /**
     * Creates a new instance of ChartViewBean
     */
    public ChartViewBean() {
    }

    @PostConstruct
    public void setInitialDate() {
        LocalDate initialDate = null;

        //initialDate = businessBean.setInitialDate();
        initialDate = LocalDate.of(2017, 11, 1);

        setSelectedDate(Date.valueOf(initialDate));

    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineMode) {
        this.lineModel = lineMode;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {

        this.company = company;
    }

    public java.util.Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(java.util.Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public BigInteger getTotalSharesTraded() {
        return totalSharesTraded;
    }

    public void setTotalSharesTraded(BigInteger totalSharesTraded) {
        this.totalSharesTraded = totalSharesTraded;
    }

    public BigDecimal getTotalValueTraded() {
        return totalValueTraded;
    }

    public void setTotalValueTraded(BigDecimal totalValueTraded) {
        this.totalValueTraded = totalValueTraded;
    }

    public BigDecimal getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(BigDecimal percentageChange) {
        this.percentageChange = percentageChange;
    }

    public PriceSheetTrade getTradeData() {
        return tradeData;
    }

    public void setTradeData(PriceSheetTrade tradeData) {
        this.tradeData = tradeData;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public BigInteger getMinNumberOfSharesTraded() {
        return minNumberOfSharesTraded;
    }

    public void setMinNumberOfSharesTraded(BigInteger minNumberOfSharesTraded) {
        this.minNumberOfSharesTraded = minNumberOfSharesTraded;
    }

    public BigInteger getMaxNumberOfSharesTraded() {
        return maxNumberOfSharesTraded;
    }

    public void setMaxNumberOfSharesTraded(BigInteger maxNumberOfSharesTraded) {
        this.maxNumberOfSharesTraded = maxNumberOfSharesTraded;
    }

    public List<SelectItem> getComparisonList() {
        return comparisonList;
    }

    public void setComparisonList(List<SelectItem> comparisonList) {
        this.comparisonList = comparisonList;
    }

    public String getSelectedComparisonItem() {
        return selectedComparisonItem;
    }

    public void setSelectedComparisonItem(String selectedComparisonItem) {
        this.selectedComparisonItem = selectedComparisonItem;
    }

    public List<Date> getIndicesDates() {
        return indicesDates;
    }

    public void setIndicesDates(List<Date> indicesDates) {
        this.indicesDates = indicesDates;
    }

    public double getHigh52Week() {
        return high52Week;
    }

    public void setHigh52Week(double high52Week) {
        this.high52Week = high52Week;
    }

    public double getLow52Week() {
        return low52Week;
    }

    public void setLow52Week(double low52Week) {
        this.low52Week = low52Week;
    }

    public BigDecimal getAbsoluteChange() {
        return absoluteChange;
    }

    public void setAbsoluteChange(BigDecimal absoluteChange) {
        this.absoluteChange = absoluteChange;
    }

    public List<Date> getSharePriceDates() {
        return sharePriceDates;
    }

    public void setSharePriceDates(List<Date> sharePriceDates) {
        this.sharePriceDates = sharePriceDates;
    }

    public void createLineModels() {

        //set company property for purpose of retrieving company profile not plotting. Just an opportunistic place to do this.
        //setCompany(company);
        //The current this.company value only has a symbol propertty since the converter CompanyConverter only sets the sumbol. So to get other properties for the company object we have to retrieve from the database.
        Company company = businessBean.retrieveCompany(this.company.getSymbol());
        //set this.company to same value as local variable company
        setCompany(company);
        System.out.println("The value of company is: " + this.company + " and the type is: " + company.getClass());
        System.out.println("Company Name: " + company.getCompanyName());
        lineModel = initLinearModel(company);
        lineModel.setTitle(company.getCompanyName() + " Share Price(USD)");
        //lineModel.setTitle("Linear Chart");
        lineModel.setLegendPosition("e");
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setMin(minSharePrice);
        yAxis.setMax(maxSharePrice);
        // yAxis.setLabel("Share Price(USD)");

        //Get X-axis for the model
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);

        //shift maxDate by 1 day in order to avoid last plot value being on graph edge
        java.sql.Date newMaxDate = (java.sql.Date) maxDate;
        LocalDate newMaxDate_1 = newMaxDate.toLocalDate();
        LocalDate newMaxDate_2 = newMaxDate_1.plusDays(1);

        maxDate = java.sql.Date.valueOf(newMaxDate_2);
        axis.setMax(maxDate.toString());
        axis.setTickFormat("%b %#d, %y");

        lineModel.getAxes().put(AxisType.X, axis);

//        Axis y2Axis = new LinearAxis("Volume");
//        y2Axis.setMin(0);
//        y2Axis.setMax(25000);
        //lineModel.getAxes().put(AxisType.Y2, y2Axis);
        // return "chartView";
    }

    public void createBarModel() {
        System.out.println("Selected comparison: " + selectedComparisonItem);
        //set company property for purpose of retrieving company profile not plotting. Just an opportunistic place to do this.
        //setCompany(company);
        //The current this.company value only has a symbol propertty since the converter CompanyConverter only sets the sumbol. So to get other properties for the company object we have to retrieve from the database.
        Company company = businessBean.retrieveCompany(this.company.getSymbol());
        //set this.company to same value as local variable company
        setCompany(company);
        System.out.println("The value of company is: " + this.company + " and the type is: " + company.getClass());
        System.out.println("Company Name: " + company.getCompanyName());
        barModel = initBarModel(company);
        barModel.setExtender("chartExtender");
        //barModel.setTitle(company.getCompanyName() + " Chart");
        //lineModel.setTitle("Linear Chart");
        barModel.setLegendPosition("e");
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setMin(minNumberOfSharesTraded);
        yAxis.setMax(maxNumberOfSharesTraded);
        yAxis.setLabel("Volume");

        Axis y2Axis = new LinearAxis("Share Price(USD)");
Axis y3Axis = new LinearAxis("Index Units");
        // y2Axis.setMin(minSharePrice);
        // y2Axis.setMax(maxSharePrice);
        barModel.getAxes().put(AxisType.Y2, y2Axis);
        barModel.getAxes().put(AxisType.Y3, y3Axis);
        // yAxis.setLabel("Share Price(USD)");

        //Get X-axis for the model
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);

        //shift maxDate by 1 day in order to avoid last plot value being on graph edge
        java.sql.Date newMaxDate = (java.sql.Date) maxDate;
        LocalDate newMaxDate_1 = newMaxDate.toLocalDate();
        LocalDate newMaxDate_2 = newMaxDate_1.plusDays(1);

        maxDate = java.sql.Date.valueOf(newMaxDate_2);
        axis.setMax(maxDate.toString());
        axis.setTickFormat("%b %#d, %y");

        //put x-axis
        barModel.getAxes().put(AxisType.X, axis);

        //barModel.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        barModel.setMouseoverHighlight(true);

        barModel.setBarWidth(3);

        //barModel.setMouseoverHighlight(false);
    }

    /**
     * Note that the setting of the maxDate uses the Date(long date) constructor
     * whose parameter so if the method is applied to dates before January 1,
     * 1970, 00:00:00 GMT problems may arise. To get the minSharePrice the
     * method sets an initial value of 2 000 000 for the minSharePrice assuming
     * that this value is out of the order of magnitude of the expected share
     * prices.
     */
    private LineChartModel initLinearModel(Company company) {
        LineChartModel model = new LineChartModel();

        LineChartSeries series = new LineChartSeries();

        List<Trade> list = businessBean.retrieveChartData(company, this.selectedDate);
        Logger.getLogger("marketsource").fine(list.toString());

        series.setLabel("Share Price");

        Date date = null;
        BigDecimal sharePrice;
        minSharePrice = BigDecimal.valueOf(2000000);
        maxSharePrice = BigDecimal.valueOf(0);
        maxDate = new Date(0);
        for (Trade trade : list) {
            date = trade.getDateTime();
            Logger.getLogger("marketsource").fine(date.toString());
            sharePrice = trade.getClosingPrice();
            series.set(date.toString(), sharePrice);

            minSharePrice = sharePrice.min(minSharePrice);
            maxSharePrice = sharePrice.max(maxSharePrice);

            if (date.after(maxDate)) {
                maxDate = date;
            }

        }

        BigDecimal range = maxSharePrice.subtract(minSharePrice);

        BigDecimal margin = new BigDecimal(0.05);

        maxSharePrice = maxSharePrice.add(range.multiply(margin));
        minSharePrice = minSharePrice.subtract(range.multiply(margin));

        Logger.getLogger("marketsource").fine("Minimum Share Price=" + minSharePrice + "Max Share Price=" + maxSharePrice);
//        series.set(1, 2);
//        series.set(2, 1);
//        series.set(3, 3);
//        series.set(4, 6);
//        series.set(5, 8);
        model.addSeries(series);

        //addd bar series
        return model;
    }

    public BarChartModel initBarModel(Company company) {
        BarChartModel model = new BarChartModel();

        LineChartSeries series = new LineChartSeries();

        LineChartSeries comparisonSeries = new LineChartSeries();

        List<Trade> list = businessBean.retrieveChartData(company, this.selectedDate);

        sharePriceDates = new ArrayList<Date>();

        Date shareDate = null;

        for (Trade shareTradeObject : list) {
            shareDate = shareTradeObject.getDateTime();

            sharePriceDates.add(shareDate);
        }

        System.out.println("selectedComparison: " + selectedComparisonItem);

        if (selectedComparisonItem != null && !selectedComparisonItem.equals("none")) {
            System.out.println("In case evaluation....");
            Axis y3Axis;
            switch (selectedComparisonItem) {
                case "allShareIndex":
                    comparisonSeries = constructSeries(selectedComparisonItem, indexSeries);
                    comparisonSeries.setLabel("All Share Index");
                    y3Axis = new LinearAxis("Index Units");

                    //model.getAxes().put(AxisType.Y3, y3Axis);
                    break;
                case "top10Index":
                    comparisonSeries = constructSeries(selectedComparisonItem, indexSeries);
                    comparisonSeries.setLabel("Top 10 Index");
                    y3Axis = new LinearAxis("Index Units");

                    //model.getAxes().put(AxisType.Y3, y3Axis);
                    break;
                case "industrialIndex":
                    comparisonSeries = constructSeries(selectedComparisonItem, indexSeries);
                    comparisonSeries.setLabel("Industrial Index");
                    y3Axis = new LinearAxis("Index Units");

                    //model.getAxes().put(AxisType.Y3, y3Axis);
                    break;
                case "miningIndex":
                    comparisonSeries = constructSeries(selectedComparisonItem, indexSeries);
                    comparisonSeries.setLabel("Mining Index");
                    y3Axis = new LinearAxis("Index Units");

                    //model.getAxes().put(AxisType.Y3, y3Axis);
                    break;
                default:
                    comparisonSeries = constructSeries(selectedComparisonItem, peerSeries);
                    break;
            }

            //model.addSeries(comparisonSeries);
        }

        List<Trade> filteredTradesList = list;
        //filter trades list to remove data points for which there is no indices data
        if (selectedComparisonItem != null && !selectedComparisonItem.equals("none")) {

            switch (selectedComparisonItem) {
                case "allShareIndex":
                    filteredTradesList = list.stream().filter(t -> indicesDates.contains(t.getDateTime()))
                            .collect(Collectors.toList());

                    //model.getAxes().put(AxisType.Y3, y3Axis);
                    break;
                case "top10Index":
                    filteredTradesList = list.stream().filter(t -> indicesDates.contains(t.getDateTime()))
                            .collect(Collectors.toList());

                    //model.getAxes().put(AxisType.Y3, y3Axis);
                    break;
                case "industrialIndex":
                    filteredTradesList = list.stream().filter(t -> indicesDates.contains(t.getDateTime()))
                            .collect(Collectors.toList());

                    //model.getAxes().put(AxisType.Y3, y3Axis);
                    break;
                case "miningIndex":
                    filteredTradesList = list.stream().filter(t -> indicesDates.contains(t.getDateTime()))
                            .collect(Collectors.toList());

                    //model.getAxes().put(AxisType.Y3, y3Axis);
                    break;
                default:
                    filteredTradesList = list;
            }

        }

        //System.out.println("NUMBER OF DATES IN INDICES DATES : " + indicesDates.size());
        System.out.println("NUMBER OF TRADES POINTS : " + list.size());
        System.out.println("NUMBER OF TRADES POINTS in FILTEREDLIST : " + filteredTradesList.size());
        Logger.getLogger("marketsource").fine(list.toString());

        series.setLabel(this.company.getCompanyName() + " Price");

        Date date = null;
        BigDecimal sharePrice;
        minSharePrice = BigDecimal.valueOf(2000000);
        maxSharePrice = BigDecimal.valueOf(0);
        maxDate = new Date(0);
        for (Trade trade : filteredTradesList) {
            date = trade.getDateTime();
            Logger.getLogger("marketsource").fine(date.toString());
            sharePrice = trade.getClosingPrice();
            series.set(date.toString(), sharePrice);
            System.out.println("MAIN SERIES: " + date.toString() + " " + sharePrice);
            minSharePrice = sharePrice.min(minSharePrice);
            maxSharePrice = sharePrice.max(maxSharePrice);

            if (date.after(maxDate)) {
                maxDate = date;
            }

        }

        BigDecimal range = maxSharePrice.subtract(minSharePrice);
        if (maxSharePrice.equals(minSharePrice)) {
            System.out.println("Zero range territory.....");

            range = new BigDecimal(0.3);
        }
        BigDecimal margin = new BigDecimal(0.05);

        maxSharePrice = maxSharePrice.add(range.multiply(margin));
        minSharePrice = minSharePrice.subtract(range.multiply(margin));

        Logger.getLogger("marketsource").fine("Minimum Share Price=" + minSharePrice + "Max Share Price=" + maxSharePrice);
//        series.set(1, 2);
//        series.set(2, 1);
//        series.set(3, 3);
//        series.set(4, 6);
//        series.set(5, 8);
        //model.addSeries(series);

        //addd bar series
        BarChartSeries barSeries = new BarChartSeries();
        barSeries.setLabel(this.company.getCompanyName() + " Volume");
        BigInteger shareVolume;
        minNumberOfSharesTraded = BigInteger.valueOf(1000000000);
        maxNumberOfSharesTraded = BigInteger.valueOf(0);
        for (Trade trade : filteredTradesList) {
            date = trade.getDateTime();
            Logger.getLogger("marketsource").fine(date.toString());
            shareVolume = trade.getTotalSharesTraded();
            barSeries.set(date.toString(), shareVolume);

            minNumberOfSharesTraded = shareVolume.min(minNumberOfSharesTraded);
            maxNumberOfSharesTraded = shareVolume.max(maxNumberOfSharesTraded);
//            if (date.after(maxDate)) {
//                maxDate = date;
//            }
        }

        maxNumberOfSharesTraded = maxNumberOfSharesTraded.multiply(BigInteger.valueOf(4));
        //  maxNumberOfSharesTraded = maxSharePrice.add(range.multiply(margin));
        // minNumberOfSharesTraded = minSharePrice.subtract(range.multiply(margin));
        //barSeries.setXaxis(AxisType.X2);
        series.setYaxis(AxisType.Y2);
        //barSeries.setYaxis(AxisType.Y2);

        series.setSmoothLine(true);
        series.setShowMarker(false);
        if (selectedComparisonItem == null || selectedComparisonItem.equals("none")) {
            series.setFill(true);

        }
        model.addSeries(series);
        if (selectedComparisonItem != null && !selectedComparisonItem.equals("none")) {
            model.addSeries(comparisonSeries);
        }
//model.setSeriesColors("#ff0000,#000000,#ff4d4d");
        model.addSeries(barSeries);
        model.setAnimate(true);
        return model;
    }

    public void retrieveCompanyProfile() {
        System.out.println("Company: " + this.company);
        queryCompanyProfileBean.setProfile(this.company.getProfile());

        if (this.company.getProfile() != null) {
            Integer id = this.company.getProfile().getRecordId();
            List<Position> positionsList = businessBean.retrieveProfilePositions(id);
            System.out.println(positionsList);

            queryCompanyProfileBean.setPositionsList(positionsList);
        } else {
            queryCompanyProfileBean.setPositionsList(null);
        }

        System.out.println("Profile: " + (this.company).getProfile());
        //return "companyprofile";

    }

    public void retrieveTradeData() {
        PriceSheetTrade tradeData
                = queryManagedBean.retrieveBoardData(company);
        setTradeData(tradeData);
        setClosingPrice(tradeData.getClosingPrice());
        setTotalSharesTraded(tradeData.getTotalSharesTraded());
        setTotalValueTraded(tradeData.getTotalValueTraded());
        setPercentageChange(tradeData.getChange());
        setAbsoluteChange(tradeData.getAbsoluteChange());

        retrieveHighAndLow();

        System.out.println("Price " + tradeData.getClosingPrice() + "Volume " + tradeData.getTotalSharesTraded() + "Value " + tradeData.getTotalValueTraded());
    }

    public void retrieveHighAndLow() {

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusWeeks(52);

        java.sql.Date startDate_2 = Date.valueOf(startDate);

        List<Trade> tradeData = businessBean.retrieveChartData(company, startDate_2);

        OptionalDouble maxPrice = tradeData.stream().mapToDouble(p -> p.getClosingPrice().doubleValue()).max();

        OptionalDouble minPrice = tradeData.stream().mapToDouble(p -> p.getClosingPrice().doubleValue()).min();

        setHigh52Week(maxPrice.getAsDouble());
        setLow52Week(minPrice.getAsDouble());
    }

    public void retrieveComparisonSelectItems() {
        System.out.println("Company: " + company);
        Collection<Company> companyPeers = company.getPeers();
        ArrayList<Company> companyPeersList = new ArrayList<Company>(companyPeers);

        SelectItem[] peersArray = new SelectItem[companyPeersList.size()];
        for (int i = 0; i < companyPeersList.size(); i++) {

            Company peer = companyPeersList.get(i);

            peersArray[i] = new SelectItem(peer.getCompanyName(), peer.getCompanyName());
        }
        comparisonList = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem("none", "None");
        comparisonList.add(item);
        SelectItemGroup peerGroup = new SelectItemGroup("Peers");
        peerGroup.setSelectItems(peersArray);

        SelectItemGroup indicesGroup = new SelectItemGroup("Indices");
        indicesGroup.setSelectItems(new SelectItem[]{new SelectItem("allShareIndex", "All Share Index"), new SelectItem("top10Index", "Top 10 Index"), new SelectItem("industrialIndex", "Industrial Index"), new SelectItem("miningIndex", "Mining Index")});

        comparisonList.add(peerGroup);
        comparisonList.add(indicesGroup);
    }

    public LineChartSeries constructSeries(String comparisonString, Function<String, LineChartSeries> f) {
        return f.apply(comparisonString);
    }

//        peerSeries = (String s) -> {
//            
//            List<Trade> list = businessBean.retrieveChartData_2(selectedComparisonItem, this.selectedDate);
//            return new LineChartSeries();
//        } 
    private Function<String, LineChartSeries> indexSeries = (String s) -> {
        LineChartSeries series = new LineChartSeries();

        List<Zsemarketindices> list = businessBean.retrieveIndicesChartData(selectedDate);
        
       List<Zsemarketindices> filteredTradesList = list.stream().filter(t -> sharePriceDates.contains(t.getDateOfTrade()))
                            .collect(Collectors.toList());
        
        
        
        
        
        System.out.println("NUMBER OF INDICES POINTS: " + list.size());

        indicesDates = new ArrayList<Date>();

        Date date = null;
        double value = 0;
        for (Zsemarketindices indicesObject : filteredTradesList) {
            date = indicesObject.getDateOfTrade();

            indicesDates.add(date);

            switch (s) {
                case "industrialIndex":
                    try {
                        value = indicesObject.getIndustrialIndex();
                    } catch (NullPointerException e) {
                        value = 0;
                    }
                    break;
                case "miningIndex":
                    try {
                        value = indicesObject.getMiningIndex();
                    } catch (NullPointerException e) {
                        value = 0;
                    }
                    break;

                case "allShareIndex":
                    try {
                        value = indicesObject.getAllShareIndex();
                    } catch (NullPointerException e) {
                        value = 0;
                    }
                    break;

                case "top10Index":
                    try {
                        value = indicesObject.getTop10Index();
                    } catch (NullPointerException e) {
                        value = 0;
                    }
                    break;

            }

            series.set(date.toString(), value);
            System.out.println("Indices comp series: " + date.toString() + " " + value);
        }
        //series.setLabel(s);
        series.setYaxis(AxisType.Y3);
        series.setShowMarker(false);
        return series;
    };

    private Function<String, LineChartSeries> peerSeries = (String s) -> {

        LineChartSeries series = new LineChartSeries();
        List<Trade> list = businessBean.retrieveChartData_2(selectedComparisonItem, this.selectedDate);
//Trade sampleObject = list.get(0);
//String labelSymbol = sampleObject.getCompany().getSymbol();
        //print values
        System.out.println("COMPARISON DATA SIZE: " + list.size());
        for (Trade t : list) {

            System.out.println(t.getDateTime() + " " + t.getClosingPrice() + ": comparison data point");

        }

        Date date = null;
        BigDecimal sharePrice;

        for (Trade trade : list) {
            date = trade.getDateTime();
            Logger.getLogger("marketsource").fine(date.toString());
            sharePrice = trade.getClosingPrice();
            series.set(date.toString(), sharePrice);

        }

        series.setYaxis(AxisType.Y2);
        //barSeries.setYaxis(AxisType.Y2);
        series.setSmoothLine(true);
        series.setShowMarker(false);
        series.setLabel(selectedComparisonItem + " Price");
        // series.setLabel(labelSymbol);
        return series;
    };

}
