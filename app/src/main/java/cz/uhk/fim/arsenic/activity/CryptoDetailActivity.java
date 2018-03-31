package cz.uhk.fim.arsenic.activity;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.uhk.fim.arsenic.R;
import cz.uhk.fim.arsenic.core.configuration.Configuration;
import cz.uhk.fim.arsenic.core.configuration.CurrencyType;
import cz.uhk.fim.arsenic.core.model.Currency;
import cz.uhk.fim.arsenic.core.service.Services;

@Fullscreen
@EActivity(R.layout.activity_crypto_detail)
public class CryptoDetailActivity extends AppCompatActivity {

    @ViewById
    ImageView icon;

    @ViewById
    TextView name;

    @ViewById
    TextView symbol;

    @ViewById
    TextView rank;

    @ViewById
    TextView usdPriceValue;

    @ViewById
    TextView btcPriceValue;

    @ViewById
    TextView customPriceIdentifier;

    @ViewById
    TextView customPriceValue;

    @ViewById
    TextView percentChange1H;

    @ViewById
    TextView percentChange24H;

    @ViewById
    TextView percentChange7D;

    @ViewById
    TextView marketCapUsdValue;

    @ViewById
    TextView marketCapCustomIdentifier;

    @ViewById
    TextView marketCapCustomValue;

    @ViewById
    TextView usdDayVolumeValue;

    @ViewById
    TextView customDayVolumeIdentifier;

    @ViewById
    TextView customDayVolumeValue;

    @ViewById
    TextView availableSupply;

    @ViewById
    TextView totalSupply;

    @ViewById
    TextView maxSupply;

    @ViewById
    TextView lastUpdated;

    @ViewById
    Toolbar cryptoDetailToolbar;

    private Currency currency;
    private CurrencyType convertedCurrency;

    @AfterViews
    protected void init() {
        currency = (Currency) getIntent().getExtras().get("currency");
        convertedCurrency = (CurrencyType) getIntent().getExtras().get("convertedCurrency");
        loadAllData();

        setSupportActionBar(cryptoDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Click(R.id.reminderButton)
    public void showNotificationDialog() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.create_notification_dialog, null);
        Services.ALERT_DIALOG_SERVICE.createCurrencyAlert(promptsView, currency, this);
    }

    private void loadAllData() {
        icon.setBackgroundResource(findImage(currency.getSymbol().toLowerCase()));
        name.setText(currency.getName());
        symbol.setText(currency.getSymbol());
        rank.setText(currency.getRank().toString());
        usdPriceValue.setText(Double.toString(currency.getPriceUsd()));
        btcPriceValue.setText(Double.toString(currency.getPriceBtc()));
        customPriceIdentifier.setText(convertedCurrency.name() + ":");
        setCustomCurrency();
        percentChange1H.setText(Double.toString(currency.getPercentChange1H()));
        percentChange24H.setText(Double.toString(currency.getPercentChange24H()));
        percentChange7D.setText(Double.toString(currency.getPercentChange7D()));
        marketCapUsdValue.setText(Double.toString(currency.getMarketCapUsd()));
        marketCapCustomIdentifier.setText(String.format(getResources().getString(R.string.customMarketCap), convertedCurrency.name()));
        usdDayVolumeValue.setText(Double.toString(currency.getDayVolumeUsd()));
        customDayVolumeIdentifier.setText(String.format(getResources().getString(R.string.dayCustomVolume), convertedCurrency.name()));
        availableSupply.setText(Double.toString(currency.getAvailableSupply()));
        totalSupply.setText(Double.toString(currency.getTotalSupply()));
        maxSupply.setText(Double.toString(currency.getMaxSupply()));

        Date expiry = new Date((long) currency.getLastUpdated() * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String formattedDate = sdf.format(expiry);
        lastUpdated.setText(formattedDate);
    }

    private int findImage(String imageId) {
        Resources r = getResources();
        Integer reference = r.getIdentifier(imageId, "drawable", getPackageName());
        if (reference != 0) {
            return reference;
        }
        return r.getIdentifier("no_icon", "drawable", getPackageName());
    }

    private void setCustomCurrency() {
        String selectedValue = Configuration.currencyType.name();
        if (selectedValue != null) {
            if (selectedValue.equals(CurrencyType.CZK.toString())) {
                customPriceValue.setText(Double.toString(currency.getPriceCzk()));
                marketCapCustomValue.setText(Double.toString(currency.getMarketCapCzk()));
                customDayVolumeValue.setText(Double.toString(currency.getDayVolumeCzk()));
            } else if (selectedValue.equals(CurrencyType.EUR.toString())) {
                customPriceValue.setText(Double.toString(currency.getPriceEur()));
                marketCapCustomValue.setText(Double.toString(currency.getMarketCapEur()));
                customDayVolumeValue.setText(Double.toString(currency.getDayVolumeEurk()));
            } else if (selectedValue.equals(CurrencyType.GBP.toString())) {
                customPriceValue.setText(Double.toString(currency.getPriceGbp()));
                marketCapCustomValue.setText(Double.toString(currency.getMarketCapGbp()));
                customDayVolumeValue.setText(Double.toString(currency.getDayVolumeGbp()));
            } else if (selectedValue.equals(CurrencyType.PLN.toString())) {
                customPriceValue.setText(Double.toString(currency.getPricePln()));
                marketCapCustomValue.setText(Double.toString(currency.getMarketCapPln()));
                customDayVolumeValue.setText(Double.toString(currency.getDayVolumePln()));
            } else if (selectedValue.equals(CurrencyType.RUB.toString())) {
                customPriceValue.setText(Double.toString(currency.getPriceRub()));
                marketCapCustomValue.setText(Double.toString(currency.getMarketCapRub()));
                customDayVolumeValue.setText(Double.toString(currency.getDayVolumeRub()));
            }
        }
    }

}
