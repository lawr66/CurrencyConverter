package com.example.currencycconverter.model;


public class Currency {
    private int id;
    private String name;
    private String abbreviation;
    private String buy;
    private String sell;

    public Currency(int id, String name, String abbreviation, String buy, String sell) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.buy = buy;
        this.sell = sell;
    }

    public Currency() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getAbr(String string) {
        return abbreviation;
    }


    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    @Override
    public String toString() {
        return this.abbreviation;
    }
}