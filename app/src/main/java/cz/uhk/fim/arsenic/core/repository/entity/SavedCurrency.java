package cz.uhk.fim.arsenic.core.repository.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "saved_currency")
public class SavedCurrency {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;

    private String symbol;

    private Integer rank;

    @ColumnInfo(name = "price_usd")
    private double priceUsd;

    @ColumnInfo(name = "price_btc")
    private double priceBtc;

    @ColumnInfo(name = "price_czk")
    private double priceCzk;

    @ColumnInfo(name = "price_eur")
    private double priceEur;

    @ColumnInfo(name ="price_gbp")
    private double priceGbp;

    @ColumnInfo(name ="price_pln")
    private double pricePln;

    @ColumnInfo(name ="price_rub")
    private double priceRub;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public double getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(double priceBtc) {
        this.priceBtc = priceBtc;
    }

    public double getPriceCzk() {
        return priceCzk;
    }

    public void setPriceCzk(double priceCzk) {
        this.priceCzk = priceCzk;
    }

    public double getPriceEur() {
        return priceEur;
    }

    public void setPriceEur(double priceEur) {
        this.priceEur = priceEur;
    }

    public double getPriceGbp() {
        return priceGbp;
    }

    public void setPriceGbp(double priceGbp) {
        this.priceGbp = priceGbp;
    }

    public double getPricePln() {
        return pricePln;
    }

    public void setPricePln(double pricePln) {
        this.pricePln = pricePln;
    }

    public double getPriceRub() {
        return priceRub;
    }

    public void setPriceRub(double priceRub) {
        this.priceRub = priceRub;
    }
}
