package cz.uhk.fim.arsenic.core.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by jilek on 30.03.2018.
 */

@JsonDeserialize
public class GlobalData {
    @JsonAlias("total_market_cap_usd")
    private Long totalMarketCapUsd;

    @JsonAlias("total_24h_volume_usd")
    private Long totalDayVolumeUsd;

    @JsonAlias("bitcoin_percentage_of_market_cap")
    private Double btcPercentageOfMarketCap;

    @JsonAlias("active_currencies")
    private Long activeCurrencies;

    @JsonAlias("active_assets")
    private Long activeAssets;

    @JsonAlias("active_markets")
    private Long activeMarkets;

    @JsonAlias("last_updated")
    private Long lastUpdated;

    public Long getTotalMarketCapUsd() {
        return totalMarketCapUsd;
    }

    public void setTotalMarketCapUsd(Long totalMarketCapUsd) {
        this.totalMarketCapUsd = totalMarketCapUsd;
    }

    public Long getTotalDayVolumeUsd() {
        return totalDayVolumeUsd;
    }

    public void setTotalDayVolumeUsd(Long totalDayVolumeUsd) {
        this.totalDayVolumeUsd = totalDayVolumeUsd;
    }

    public Double getBtcPercentageOfMarketCap() {
        return btcPercentageOfMarketCap;
    }

    public void setBtcPercentageOfMarketCap(Double btcPercentageOfMarketCap) {
        this.btcPercentageOfMarketCap = btcPercentageOfMarketCap;
    }

    public Long getActiveCurrencies() {
        return activeCurrencies;
    }

    public void setActiveCurrencies(Long activeCurrencies) {
        this.activeCurrencies = activeCurrencies;
    }

    public Long getActiveAssets() {
        return activeAssets;
    }

    public void setActiveAssets(Long activeAssets) {
        this.activeAssets = activeAssets;
    }

    public Long getActiveMarkets() {
        return activeMarkets;
    }

    public void setActiveMarkets(Long activeMarkets) {
        this.activeMarkets = activeMarkets;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
