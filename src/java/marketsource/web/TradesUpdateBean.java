/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.sql.Date;
import javax.ejb.EJB;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import marketsource.ejb.AdminBean;

/**
 *
 * @author dell
 */
@Named(value = "tradesUpdateBean")
@ConversationScoped
public class TradesUpdateBean implements Serializable {

    @EJB
    private AdminBean adminBean;

    @NotNull
    private java.util.Date date;

    @Digits(integer = 30, fraction = 30)
    private double industrialIndex;

    @Digits(integer = 30, fraction = 30)
    private double miningIndex;

    @Digits(integer = 30, fraction = 30)
    private double allShare;

    @Digits(integer = 30, fraction = 30)
    private double top10;

    /**
     * Creates a new instance of TradesUpdateBean
     */
    public TradesUpdateBean() {
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public double getIndustrialIndex() {
        return industrialIndex;
    }

    public void setIndustrialIndex(double industrialIndex) {
        this.industrialIndex = industrialIndex;
    }

    public double getMiningIndex() {
        return miningIndex;
    }

    public void setMiningIndex(double miningIndex) {
        this.miningIndex = miningIndex;
    }

    public double getAllShare() {
        return allShare;
    }

    public void setAllShare(double allShare) {
        this.allShare = allShare;
    }

    public double getTop10() {
        return top10;
    }

    public void setTop10(double top10) {
        this.top10 = top10;
    }

    public void updateZSEIndicesData() {

        Date date1 = new Date(this.date.getTime());

        //System.out.println(date + " " + industrialIndex + " " + miningIndex);
        adminBean.updateZSEIndices(date1, this.industrialIndex, this.miningIndex, this.allShare, this.top10);

    }

}
