/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.util;

import java.sql.Date;

/**
 *
 * @author dell
 */
public class IndicesSheet {

    private Date dateOfTrade;
   
    private Double industrialIndex;
    private Double miningIndex;
    private Double allShareIndex;
    private Double top10Index;
    
    private Double industrialIndexChange;
    private Double miningIndexChange;
    private Double allShareIndexChange;
    private Double top10IndexChange; 
    
    private int industrialDirectionIndicator;
    private int miningDirectionIndicator;
    private int allShareDirectionIndicator;
    private int top10DirectionIndicator;

    public Date getDateOfTrade() {
        return dateOfTrade;
    }

    public void setDateOfTrade(Date dateOfTrade) {
        this.dateOfTrade = dateOfTrade;
    }

    public Double getIndustrialIndex() {
        return industrialIndex;
    }

    public void setIndustrialIndex(Double industrialIndex) {
        this.industrialIndex = industrialIndex;
    }

    public Double getMiningIndex() {
        return miningIndex;
    }

    public void setMiningIndex(Double miningIndex) {
        this.miningIndex = miningIndex;
    }

    public Double getIndustrialIndexChange() {
        return industrialIndexChange;
    }

    public void setIndustrialIndexChange(Double industrialIndexChange) {
        this.industrialIndexChange = industrialIndexChange;
    }

    public Double getMiningIndexChange() {
        return miningIndexChange;
    }

    public void setMiningIndexChange(Double miningIndexChange) {
        this.miningIndexChange = miningIndexChange;
    }

    public int getIndustrialDirectionIndicator() {
        return industrialDirectionIndicator;
    }

    public void setIndustrialDirectionIndicator(int industrialDirectionIndicator) {
        this.industrialDirectionIndicator = industrialDirectionIndicator;
    }

    public int getMiningDirectionIndicator() {
        return miningDirectionIndicator;
    }

    public void setMiningDirectionIndicator(int miningDirectionIndicator) {
        this.miningDirectionIndicator = miningDirectionIndicator;
    }

    public Double getAllShareIndex() {
        return allShareIndex;
    }

    public void setAllShareIndex(Double allShareIndex) {
        this.allShareIndex = allShareIndex;
    }

    public Double getTop10Index() {
        return top10Index;
    }

    public void setTop10Index(Double top10Index) {
        this.top10Index = top10Index;
    }

    public Double getAllShareIndexChange() {
        return allShareIndexChange;
    }

    public void setAllShareIndexChange(Double allShareIndexChange) {
        this.allShareIndexChange = allShareIndexChange;
    }

    public Double getTop10IndexChange() {
        return top10IndexChange;
    }

    public void setTop10IndexChange(Double top10IndexChange) {
        this.top10IndexChange = top10IndexChange;
    }

    public int getAllShareDirectionIndicator() {
        return allShareDirectionIndicator;
    }

    public void setAllShareDirectionIndicator(int allShareDirectionIndicator) {
        this.allShareDirectionIndicator = allShareDirectionIndicator;
    }

    public int getTop10DirectionIndicator() {
        return top10DirectionIndicator;
    }

    public void setTop10DirectionIndicator(int top10DirectionIndicator) {
        this.top10DirectionIndicator = top10DirectionIndicator;
    }
    
    

    @Override
    public String toString() {

        return "IndicesSheet: " + this.getDateOfTrade()
                + " " + "industrialIndex: " + industrialIndex
                + " " + "miningIndex: " + miningIndex
                + " " + "allShareIndex: " + allShareIndex
                + " " + "top10: " + top10Index
                
                + " " + "industrialIndexChange: " + industrialIndexChange
                + " " + "miningIndexChange: " + miningIndexChange
                + " " + "allShareChange: " + allShareIndexChange
                + " " + "top10IndexChange: " + top10IndexChange
                
                + " " + "industrialDirectionIndicator: " + industrialDirectionIndicator
                + " " + "miningDirectionIndicator: " + miningDirectionIndicator
                + " " + "allShareDirectionIndicator: " + allShareDirectionIndicator
                + " " + "top10DirectionIndicator: " + top10DirectionIndicator;
                
                
                

    }

}
