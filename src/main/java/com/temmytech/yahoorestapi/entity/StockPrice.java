package com.temmytech.yahoorestapi.entity;

import java.math.BigDecimal;

public class StockPrice {

    private String name;
    private String symbol;
    private BigDecimal price;
    private BigDecimal percentageChange;

    public StockPrice() {
    }

    public StockPrice(String symbol, String name, BigDecimal price) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
    }



    public StockPrice(String symbol, BigDecimal price) {
        this.symbol = symbol;
        this.price = price;
    }

    public BigDecimal getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(BigDecimal percentageChange) {
        this.percentageChange = percentageChange;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
