package cz.uhk.fim.arsenic.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import cz.uhk.fim.arsenic.R;
import cz.uhk.fim.arsenic.core.configuration.Configuration;
import cz.uhk.fim.arsenic.core.configuration.CurrencyType;
import cz.uhk.fim.arsenic.core.initialize.ArsenicGuiInitializer;
import cz.uhk.fim.arsenic.core.initialize.GuiComponentsHolder;
import cz.uhk.fim.arsenic.core.repository.ApplicationDatabase;
import cz.uhk.fim.arsenic.core.rest.CurrencyRest;
import cz.uhk.fim.arsenic.core.rest.GlobalDataRest;
import cz.uhk.fim.arsenic.core.service.Services;

@EActivity(R.layout.activity_arsenic)
public class ArsenicActivity extends AppCompatActivity {

    @ViewById
    LinearLayout noConnectionLayout;

    @ViewById
    LinearLayout contentLayout;

    @ViewById
    Button reloadButton;

    @ViewById
    Spinner currencySpinner;

    @RestService
    CurrencyRest currencyRest;

    @RestService
    GlobalDataRest globalDataRest;

    @ViewById
    Toolbar mainToolbar;

    @ViewById
    ViewPager container;

    @ViewById
    TabLayout tabs;

    private static GuiComponentsHolder holder;

    private FusedLocationProviderClient client;
    private LocationCallback locationCallback;

    public static void refresh(){
        Services.ASSYNC_TASK_SERVICE.loadAllCurrencies(holder);
    }

    @AfterViews
    public void init() {
        ApplicationDatabase.initDatabase(getApplicationContext());

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
        holder.setCurrencySpinner(currencySpinner);
        holder.setNoConnectionLayout(noConnectionLayout);
        holder.setReloadButton(reloadButton);
        holder.setCurrencyRest(currencyRest);
        holder.setGlobalDataRest(globalDataRest);
        holder.setContext(this);
        holder.setGeocoder(new Geocoder(this));
        holder.setContainer(container);
        holder.setTabs(tabs);
        holder.setFragmentManager(getSupportFragmentManager());
        return holder;
    }
}
