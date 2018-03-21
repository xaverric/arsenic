package cz.uhk.fim.arsenic.core.initialize;

import android.content.Context;
import android.location.Geocoder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import cz.uhk.fim.arsenic.core.rest.CurrencyRest;

public class GuiComponentsHolder {

    private SwipeRefreshLayout refreshLayout;
    private LinearLayout noConnectionLayout;
    private LinearLayout contentLayout;
    private Button reloadButton;
    private ListView cryptoList;
    private Spinner currencySpinner;
    private Spinner limitSpinner;
    private CurrencyRest currencyRest;

    private Context context;

    private Geocoder geocoder;

    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public void setRefreshLayout(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    public LinearLayout getNoConnectionLayout() {
        return noConnectionLayout;
    }

    public void setNoConnectionLayout(LinearLayout noConnectionLayout) {
        this.noConnectionLayout = noConnectionLayout;
    }

    public LinearLayout getContentLayout() {
        return contentLayout;
    }

    public void setContentLayout(LinearLayout contentLayout) {
        this.contentLayout = contentLayout;
    }

    public Button getReloadButton() {
        return reloadButton;
    }

    public void setReloadButton(Button reloadButton) {
        this.reloadButton = reloadButton;
    }

    public ListView getCryptoList() {
        return cryptoList;
    }

    public void setCryptoList(ListView cryptoList) {
        this.cryptoList = cryptoList;
    }

    public Spinner getCurrencySpinner() {
        return currencySpinner;
    }

    public void setCurrencySpinner(Spinner currencySpinner) {
        this.currencySpinner = currencySpinner;
    }

    public Spinner getLimitSpinner() {
        return limitSpinner;
    }

    public void setLimitSpinner(Spinner limitSpinner) {
        this.limitSpinner = limitSpinner;
    }

    public CurrencyRest getCurrencyRest() {
        return currencyRest;
    }

    public void setCurrencyRest(CurrencyRest currencyRest) {
        this.currencyRest = currencyRest;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Geocoder getGeocoder() {
        return geocoder;
    }

    public void setGeocoder(Geocoder geocoder) {
        this.geocoder = geocoder;
    }
}
