package cz.uhk.fim.arsenic.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import cz.uhk.fim.arsenic.R;
import cz.uhk.fim.arsenic.core.android.adapter.CurrencyListAdapter;
import cz.uhk.fim.arsenic.core.configuration.Configuration;
import cz.uhk.fim.arsenic.core.configuration.CurrencyType;
import cz.uhk.fim.arsenic.core.model.Currency;
import cz.uhk.fim.arsenic.core.rest.CurrencyRest;

@Fullscreen
@EActivity(R.layout.activity_arsenic)
public class ArsenicActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    //TODO Refresh list on pull down
    //TODO Move ranks more to left
    //TODO Aling spinners
    //TODO create detail of currency
    //TODO when no connection - hide all except button

    @ViewById
    TextView noConnectionNotification;

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

    @AfterViews
    public void init() {
        initSpinners();
        performAssyncTask(currencySpinner.getSelectedItem().toString(), Integer.parseInt((String) limitSpinner.getSelectedItem()));
    }

    @Click(R.id.reloadButton)
    public void reloadItems() {
        noConnectionNotification.setVisibility(View.GONE);
        reloadButton.setVisibility(View.GONE);
        init();
    }

    @ItemSelect(R.id.currencySpinner)
    public void currencySpinnerItemSelected(boolean selected, CurrencyType selectedItem) {
        if (selected) {
            Configuration.currencyType = selectedItem;
            performAssyncTask(selectedItem.toString(), Integer.parseInt((String) limitSpinner.getSelectedItem()));
        }
    }

    @ItemSelect(R.id.limitSpinner)
    public void limitSpinnerItemSelected(boolean selected, String selectedItem) {
        if (selected) {
            Configuration.limit = Integer.parseInt(selectedItem);
            performAssyncTask(currencySpinner.getSelectedItem().toString(), Integer.parseInt(selectedItem));
        }
    }

    @ItemClick(R.id.cryptoList)
    public void listItemClicked(Currency clickedItem) {
        Intent intent = new Intent(this, CryptoDetailActivity_.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currency", clickedItem);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void initSpinners() {
        final ArrayAdapter<CurrencyType> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, EnumSet.allOf(CurrencyType.class).toArray());
        currencySpinner.setAdapter(adapter);
    }

    private void performAssyncTask(final String currencyType, final Integer limit) {
        if (isOnline()) {
            createAsyncTaskLoadCurrencies(currencySpinner.getSelectedItem().toString(), Integer.parseInt((String) limitSpinner.getSelectedItem()));
        } else {
            noConnectionNotification.setVisibility(View.VISIBLE);
            reloadButton.setVisibility(View.VISIBLE);
        }
    }

    private void createAsyncTaskLoadCurrencies(final String currencyType, final Integer limit) {
        final ArrayList<Currency> cryptoCurrencyList = new ArrayList<>();
        final ArrayAdapter<Currency> adapter = new CurrencyListAdapter(this, cryptoCurrencyList);
        cryptoList.setAdapter(adapter);

        new AsyncTask<Void, Void, List<Currency>>() {
            @Override
            protected List<Currency> doInBackground(Void... voids) {
                return new ObjectMapper().convertValue(currencyRest.getConvertedCurrencies(currencyType, limit), new TypeReference<List<Currency>>() {
                });
            }

            @Override
            protected void onPostExecute(List<Currency> currencies) {
                cryptoCurrencyList.addAll(currencies);
                adapter.notifyDataSetChanged();
                Log.i("list", cryptoCurrencyList.toString());
            }
        }.execute();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
