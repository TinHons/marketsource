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
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import marketsource.ejb.BusinessBean;
import marketsource.entity.Trade;
import marketsource.entity.Zsemarketindices;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Titanium
 */
@Named(value = "indicesChartViewBean")
@SessionScoped
public class IndicesChartViewBean implements Serializable {

    @EJB
    private BusinessBean businessBean;

    private LineChartModel lineModel;

    private double minIndexValue;

    private double maxIndexValue;

    private Date maxDate;

    private String INDUSTRIAL_TYPE = "industrialIndex";

    private String MINING_TYPE = "miningIndex";

    private String index;
    
    private java.util.Date selectedDate;

    /**
     * Creates a new instance of IndicesChartViewBean
     */
    public IndicesChartViewBean() {
    }
    
    @PostConstruct
    public void setInitialDate() {
        LocalDate initialDate = null;

        //initialDate = businessBean.setInitialDate();
        
        initialDate = LocalDate.of(2018,1,1);

        setSelectedDate(Date.valueOf(initialDate));
    }

    public BusinessBean getBusinessBean() {
        return businessBean;
    }

    public void setBusinessBean(BusinessBean businessBean) {
        this.businessBean = businessBean;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public double getMinIndexValue() {
        return minIndexValue;
    }

    public void setMinIndexValue(double minIndexValue) {
        this.minIndexValue = minIndexValue;
    }

    public double getMaxIndexValue() {
        return maxIndexValue;
    }

    public void setMaxIndexValue(double maxIndexValue) {
        this.maxIndexValue = maxIndexValue;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public String getINDUSTRIAL_TYPE() {
        return INDUSTRIAL_TYPE;
    }

    public void setINDUSTRIAL_TYPE(String INDUSTRIAL_TYPE) {
        this.INDUSTRIAL_TYPE = INDUSTRIAL_TYPE;
    }

    public String getMINING_TYPE() {
        return MINING_TYPE;
    }

    public void setMINING_TYPE(String MINING_TYPE) {
        this.MINING_TYPE = MINING_TYPE;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public java.util.Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(java.util.Date selectedDate) {
        this.selectedDate = selectedDate;
    }
    
    

    public String createLineModels() {
        lineModel = initLinearModel(this.index);
         lineModel.setExtender("ext");
        lineModel.setTitle(this.index);
        //lineModel.setTitle("Linear Chart");
        lineModel.setLegendPosition("e");
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setMin(minIndexValue);
        yAxis.setMax(maxIndexValue);
        yAxis.setLabel(this.index);

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

        return "indicesChart";

    }

    private LineChartModel initLinearModel(String index) {
        LineChartModel model = new LineChartModel();

        LineChartSeries series = new LineChartSeries();

        List<Zsemarketindices> list = businessBean.retrieveIndicesChartData(selectedDate);
        Logger.getLogger("marketsource").fine(list.toString());

        series.setLabel("Series 1");

        Date date = null;
        double value = 0;
        minIndexValue = 2000000000;
        maxIndexValue = 0;
        maxDate = new Date(0);
        for (Zsemarketindices indicesObject : list) {
            date = indicesObject.getDateOfTrade();
            Logger.getLogger("marketsource").fine(date.toString());

            switch (index) {
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

//            if (index.equals("industrialIndex")) {
//
//                value = indicesObject.getIndustrialIndex();
//
//            } else {
//
//                value = indicesObject.getMiningIndex();
//
//            }
            series.set(date.toString(), value);

            // minIndexValue = value.min(minIndexValue);
            if (value < minIndexValue) {
                minIndexValue = value;
            }

            if (value > maxIndexValue) {
                maxIndexValue = value;
            }
            // maxIndexValue = value.max(maxIndexValue);

            if (date.after(maxDate)) {
                maxDate = date;
            }

        }

        //BigDecimal range = maxSharePrice.subtract(minSharePrice);
        double range = maxIndexValue - minIndexValue;

        //BigDecimal margin = new BigDecimal(0.05);
        double margin = 0.05;

        //maxSharePrice = maxSharePrice.add(range.multiply(margin));
        maxIndexValue = maxIndexValue + (range * margin);
        minIndexValue = minIndexValue - (range * margin);

        //minSharePrice = minSharePrice.subtract(range.multiply(margin));
        Logger.getLogger("marketsource").fine("Minimum Index Value=" + minIndexValue + "Max Index Value=" + maxIndexValue);
//        series.set(1, 2);
//        series.set(2, 1);
//        series.set(3, 3);
//        series.set(4, 6);
//        series.set(5, 8);
series.setShowMarker(false);

        model.addSeries(series);
model.setAnimate(true);
        return model;
    }
    
    public String onDateButtonClick() {
        System.out.println("Executed!");
        return null;

    }

}
