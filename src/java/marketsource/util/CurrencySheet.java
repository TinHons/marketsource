/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.util;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author dell
 */
public class CurrencySheet {
    
    private LocalDate datey;
    private String code;
    private String name;
    private BigDecimal rate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDate getDatey() {
        return datey;
    }

    public void setDatey(LocalDate datey) {
        this.datey = datey;
    }
    
    
    
    
    
}
