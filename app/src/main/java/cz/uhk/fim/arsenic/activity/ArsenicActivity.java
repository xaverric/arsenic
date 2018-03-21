package cz.uhk.fim.arsenic.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ItemLongClick;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import cz.uhk.fim.arsenic.R;
import cz.uhk.fim.arsenic.core.configuration.Configuration;
import cz.uhk.fim.arsenic.core.configuration.CurrencyType;
import cz.uhk.fim.arsenic.core.initialize.ArsenicGuiInitializer;
import cz.uhk.fim.arsenic.core.initialize.GuiComponentsHolder;
import cz.uhk.fim.arsenic.core.model.Currency;
import cz.uhk.fim.arsenic.core.rest.CurrencyRest;
import cz.uhk.fim.arsenic.core.service.Services;

@EActivity(R.layout.activity_arsenic)
public class ArsenicActivity extends AppCompatActivity {

    @ViewById
    SwipeRefreshLayout refreshLayout;

    @ViewById
    LinearLayout noConnectionLayout;

    @ViewById
    LinearLayout contentLayout;

    @ViewById
    Button reloadButton;

    @ViewById
    ListView cryptoList;

    @ViewById
    Spinner currencySpinner;

    @ViewById
    Spinner limitSpinner;

    @RestService
    CurrencyRest<Currency> currencyRest;

    @ViewById
    Toolbar mainToolbar;

    private GuiComponentsHolder holder;

    private FusedLocationProviderClient client;
    private LocationCallback locationCallback;

    @AfterViews
    public void init() {
        if (holder == null) {
            holder = createGuiComponentsHolder();
        }
        new ArsenicGuiInitializer(holder, this).initAll();
        Services.ASSYNC_TASK_SERVICE.loadAllCurrencies(holder);

        locationCallback = Services.GEO_LOCATION_SERVICE.creaeLocationCallback(holder);

        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Click(R.id.reloadButton)
    public void reloadItems() {
        Services.ASSYNC_TASK_SERVICE.loadAllCurrencies(holder);
    }

    @ItemSelect(R.id.currencySpinner)
    public void currencySpinnerItemSelected(boolean selected, CurrencyType selectedItem) {
        if (selected) {
            Configuration.currencyType = selectedItem;
            Services.ASSYNC_TASK_SERVICE.loadAllCurrencies(holder);
        }
    }

    @ItemSelect(R.id.limitSpinner)
    public void limitSpinnerItemSelected(boolean selected, String selectedItem) {
        if (selected) {
            Configuration.limit = Integer.parseInt(selectedItem);
            Services.ASSYNC_TASK_SERVICE.loadAllCurrencies(holder);
        }
    }

    @ItemClick(R.id.cryptoList)
    public void listItemClicked(Currency clickedItem) {
        Intent intent = new Intent(this, CryptoDetailActivity_.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currency", clickedItem);
        bundle.putSerializable("convertedCurrency", (CurrencyType) currencySpinner.getSelectedItem());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @ItemLongClick(R.id.cryptoList)
    public void listItemLongClicked(final Currency clickedItem) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.create_notification_dialog, null);
        Services.ALERT_DIALOG_SERVICE.createCurrencyAlert(promptsView, clickedItem, this);
    }

    @Click(R.id.geoLocationBtn)
    public void startStopLocationTracking(View sender) {
        if (Configuration.isLocationTracking){
            FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
            client.removeLocationUpdates(locationCallback);
            Toast.makeText(this, "Location turned off", Toast.LENGTH_LONG).show();
            Configuration.isLocationTracking = false;
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(500);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                client = LocationServices.getFusedLocationProviderClient(this);
                client.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            Toast.makeText(this, "Location turned on", Toast.LENGTH_LONG).show();
            Configuration.isLocationTracking = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            startStopLocationTracking(null);
        }
    }

    private GuiComponentsHolder createGuiComponentsHolder() {
        GuiComponentsHolder holder = new GuiComponentsHolder();
        holder.setContentLayout(contentLayout);
        holder.setCryptoList(cryptoList);
        holder.setCurrencySpinner(currencySpinner);
        holder.setLimitSpinner(limitSpinner);
        holder.setNoConnectionLayout(noConnectionLayout);
        holder.setReloadButton(reloadButton);
        holder.setRefreshLayout(refreshLayout);
        holder.setCurrencyRest(currencyRest);
        holder.setContext(this);
        holder.setGeocoder(new Geocoder(this));
        return holder;
    }
}
