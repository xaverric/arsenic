package cz.uhk.fim.arsenic.core.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.LinkedHashMap;

@JsonDeserialize
public class Currency implements Serializable {

    private String id;

    private String name;

    private String symbol;

    private Integer rank;

    @JsonAlias("price_usd")
    private double priceUsd;

    @JsonAlias("price_btc")
    private double priceBtc;

    @JsonAlias("24h_volume_usd")
    private double dayVolumeUsd;

    @JsonAlias("market_cap_usd")
    private double marketCapUsd;

    @JsonAlias("available_supply")
    private double availableSupply;

    @JsonAlias("total_supply")
    private double totalSupply;

    @JsonAlias("max_supply")
    private double maxSupply;

    @JsonAlias("percent_change_1h")
    private double percentChange1H;

    @JsonAlias("percent_change_24h")
    private double percentChange24H;

    @JsonAlias("percent_change_7d")
    private double percentChange7D;

    @JsonAlias("last_updated")
    private double lastUpdated;

    @JsonAlias("price_czk")
    private double priceCzk;

    @JsonAlias("24h_volume_czk")
    private double dayVolumeCzk;

    @JsonAlias("market_cap_czk")
    private double marketCapCzk;

    @JsonAlias("price_eur")
    private double priceEur;

    @JsonAlias("24h_volume_eur")
    private double dayVolumeEurk;

    @JsonAlias("market_cap_eur")
    private double marketCapEur;

    @JsonAlias("price_gbp")
    private double priceGbp;

    @JsonAlias("24h_volume_gbp")
    private double dayVolumeGbp;

    @JsonAlias("market_cap_gbp")
    private double marketCapGbp;

    @JsonAlias("price_pln")
    private double pricePln;

    @JsonAlias("24h_volume_pln")
    private double dayVolumePln;

    @JsonAlias("market_cap_pln")
    private double marketCapPln;

    @JsonAlias("price_rub")
    private double priceRub;

    @JsonAlias("24h_volume_rub")
    private double dayVolumeRub;

    @JsonAlias("market_cap_rub")
    private double marketCapRub;

    public Currency() {
    }

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

    public double getDayVolumeUsd() {
        return dayVolumeUsd;
    }

    public void setDayVolumeUsd(double dayVolumeUsd) {
        this.dayVolumeUsd = dayVolumeUsd;
    }

    public double getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(double marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public double getAvailableSupply() {
        return availableSupply;
    }

    public void setAvailableSupply(double availableSupply) {
        this.availableSupply = availableSupply;
    }

    public double getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(double totalSupply) {
        this.totalSupply = totalSupply;
    }

    public double getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(double maxSupply) {
        this.maxSupply = maxSupply;
    }

    public double getPercentChange1H() {
        return percentChange1H;
    }

    public void setPercentChange1H(double percentChange1H) {
        this.percentChange1H = percentChange1H;
    }

    public double getPercentChange24H() {
        return percentChange24H;
    }

    public void setPercentChange24H(double percentChange24H) {
        this.percentChange24H = percentChange24H;
    }

    public double getPercentChange7D() {
        return percentChange7D;
    }

    public void setPercentChange7D(double percentChange7D) {
        this.percentChange7D = percentChange7D;
    }

    public double getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(double lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public double getPriceCzk() {
        return priceCzk;
    }

    public void setPriceCzk(double priceCzk) {
        this.priceCzk = priceCzk;
    }

    public double getDayVolumeCzk() {
        return dayVolumeCzk;
    }

    public void setDayVolumeCzk(double dayVolumeCzk) {
        this.dayVolumeCzk = dayVolumeCzk;
    }

    public double getMarketCapCzk() {
        return marketCapCzk;
    }

    public void setMarketCapCzk(double marketCapCzk) {
        this.marketCapCzk = marketCapCzk;
    }

    public double getPriceEur() {
        return priceEur;
    }

    public void setPriceEur(double priceEur) {
        this.priceEur = priceEur;
    }

    public double getDayVolumeEurk() {
        return dayVolumeEurk;
    }

    public void setDayVolumeEurk(double dayVolumeEurk) {
        this.dayVolumeEurk = dayVolumeEurk;
    }

    public double getMarketCapEur() {
        return marketCapEur;
    }

    public void setMarketCapEur(double marketCapEur) {
        this.marketCapEur = marketCapEur;
    }

    public double getPriceGbp() {
        return priceGbp;
    }

    public void setPriceGbp(double priceGbp) {
        this.priceGbp = priceGbp;
    }

    public double getDayVolumeGbp() {
        return dayVolumeGbp;
    }

    public void setDayVolumeGbp(double dayVolumeGbp) {
        this.dayVolumeGbp = dayVolumeGbp;
    }

    public double getMarketCapGbp() {
        return marketCapGbp;
    }

    public void setMarketCapGbp(double marketCapGbp) {
        this.marketCapGbp = marketCapGbp;
    }

    public double getPricePln() {
        return pricePln;
    }

    public void setPricePln(double pricePln) {
        this.pricePln = pricePln;
    }

    public double getDayVolumePln() {
        return dayVolumePln;
    }

    public void setDayVolumePln(double dayVolumePln) {
        this.dayVolumePln = dayVolumePln;
    }

    public double getMarketCapPln() {
        return marketCapPln;
    }

    public void setMarketCapPln(double marketCapPln) {
        this.marketCapPln = marketCapPln;
    }

    public double getPriceRub() {
        return priceRub;
    }

    public void setPriceRub(double priceRub) {
        this.priceRub = priceRub;
    }

    public double getDayVolumeRub() {
        return dayVolumeRub;
    }

    public void setDayVolumeRub(double dayVolumeRub) {
        this.dayVolumeRub = dayVolumeRub;
    }

    public double getMarketCapRub() {
        return marketCapRub;
    }

    public void setMarketCapRub(double marketCapRub) {
        this.marketCapRub = marketCapRub;
    }

    @Override
    public String toString() {
        return symbol + ": " + name;
    }
}
