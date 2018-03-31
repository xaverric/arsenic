package cz.uhk.fim.arsenic.core.initialize;

import android.app.Notification;
import android.content.Context;
import android.location.Geocoder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import cz.uhk.fim.arsenic.core.model.Currency;
import cz.uhk.fim.arsenic.core.rest.CurrencyRest;
import cz.uhk.fim.arsenic.core.rest.GlobalDataRest;

public class GuiComponentsHolder {

    private LinearLayout noConnectionLayout;
    private LinearLayout contentLayout;
    private Button reloadButton;
    private ListView cryptoList;
    private Spinner currencySpinner;
    private CurrencyRest currencyRest;
    private GlobalDataRest globalDataRest;
    private ViewPager container;
    private TabLayout tabs;
    private Context context;
    private FragmentManager fragmentManager;
    private Geocoder geocoder;

    private Currency currentCurrency;
    private int notificationId;
    private boolean priceGoesAbove;
    private boolean priceGoesBelow;
    private double priceAbove;
    private double priceBelow;
    private Notification notification;
    private String currencyId;

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

    public CurrencyRest getCurrencyRest() {
        return currencyRest;
    }

    public void setCurrencyRest(CurrencyRest currencyRest) {
        this.currencyRest = currencyRest;
    }

    public GlobalDataRest getGlobalDataRest() {
        return globalDataRest;
    }

    public void setGlobalDataRest(GlobalDataRest globalDataRest) {
        this.globalDataRest = globalDataRest;
    }

    public ViewPager getContainer() {
        return container;
    }

    public void setContainer(ViewPager container) {
        this.container = container;
    }

    public TabLayout getTabs() {
        return tabs;
    }

    public void setTabs(TabLayout tabs) {
        this.tabs = tabs;
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

    public Currency getCurrentCurrency() {
        return currentCurrency;
    }

    public void setCurrentCurrency(Currency currentCurrency) {
        this.currentCurrency = currentCurrency;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public boolean isPriceGoesAbove() {
        return priceGoesAbove;
    }

    public void setPriceGoesAbove(boolean priceGoesAbove) {
        this.priceGoesAbove = priceGoesAbove;
    }

    public boolean isPriceGoesBelow() {
        return priceGoesBelow;
    }

    public void setPriceGoesBelow(boolean priceGoesBelow) {
        this.priceGoesBelow = priceGoesBelow;
    }

    public double getPriceAbove() {
        return priceAbove;
    }

    public void setPriceAbove(double priceAbove) {
        this.priceAbove = priceAbove;
    }

    public double getPriceBelow() {
        return priceBelow;
    }

    public void setPriceBelow(double priceBelow) {
        this.priceBelow = priceBelow;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
