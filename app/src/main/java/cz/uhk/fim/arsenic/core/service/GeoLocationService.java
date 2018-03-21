package cz.uhk.fim.arsenic.core.service;

import android.content.res.Resources;
import android.location.Address;
import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.io.IOException;
import java.util.List;

import cz.uhk.fim.arsenic.R;
import cz.uhk.fim.arsenic.core.initialize.GuiComponentsHolder;

public class GeoLocationService {

    private List<Address> addresses;

    public LocationCallback creaeLocationCallback(final GuiComponentsHolder holder){
        return new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                List<Location> locationList = locationResult.getLocations();
                if (!locationList.isEmpty()){
                    Location location = locationList.get(0);
                    try {
                        addresses = holder.getGeocoder().getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        holder.getCurrencySpinner().setSelection(resolveCurrencyPosition(holder, addresses.get(0).getCountryCode()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private int resolveCurrencyPosition(GuiComponentsHolder holder, String countryCodeInput){
        Resources resources = holder.getContext().getResources();
        String[] countryCodes = resources.getStringArray(R.array.countryCodes);
        for (String countryCode : countryCodes){
            String[] currencyCountryCode = countryCode.split(",");
            if (currencyCountryCode[1].equals(countryCodeInput)){
                return Integer.parseInt(currencyCountryCode[0]);
            }
        }
        return 4;
    }

}
