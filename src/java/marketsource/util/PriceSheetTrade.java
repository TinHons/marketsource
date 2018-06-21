/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import marketsource.entity.Company;

/**
 *
 * @author dell
 */
public class PriceSheetTrade {
    
    private Company company;
    
    private BigDecimal  closingPrice;
    private BigInteger totalSharesTraded;
    private BigDecimal totalValueTraded;
    private BigDecimal absoluteChange;
    private BigDecimal change;
    private int directionIndicator;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    

    public int getDirectionIndicator() {
        return directionIndicator;
    }

    public void setDirectionIndicator(int directionIndicator) {
        this.directionIndicator = directionIndicator;
    }

    public BigDecimal getAbsoluteChange() {
        return absoluteChange;
    }

    public void setAbsoluteChange(BigDecimal absoluteChange) {
        this.absoluteChange = absoluteChange;
    }
    
    
    
    @Override
    public String toString(){
    
    return "Company: " + company.getIsin()
            + " " + "Closing Price: " + closingPrice
            + " "  + "totalSharesTraded: " + totalSharesTraded
            + " " + "totalValueTraded: " + totalValueTraded
            + " " + "change: " + change
            + " " + "directionIndicator: " + directionIndicator;
            
    }
    
    
    
    
}
