package cz.uhk.fim.arsenic.core.service;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.uhk.fim.arsenic.core.initialize.ArsenicGuiInitializer;
import cz.uhk.fim.arsenic.core.initialize.GuiComponentsHolder;
import cz.uhk.fim.arsenic.core.model.Currency;
import cz.uhk.fim.arsenic.core.model.GlobalData;
import cz.uhk.fim.arsenic.core.repository.ApplicationDatabase;
import cz.uhk.fim.arsenic.core.repository.entity.SavedCurrency;
import cz.uhk.fim.arsenic.core.rest.CurrencyRest;

@SuppressLint("StaticFieldLeak")
public class AssyncTaskService {

    public void loadCurrencyOnNotificationReceived(final GuiComponentsHolder holder, final CurrencyRest currencyRest) {
        new AsyncTask<Void, Void, List<Currency>>() {

            @Override
            protected List<Currency> doInBackground(Void... voids) {
                return new ObjectMapper().convertValue(currencyRest.getCurrency(holder.getCurrencyId(), "USD"), new TypeReference<List<Currency>>() {});
            }

            @Override
            protected void onPostExecute(List<Currency> currencies) {
                holder.setCurrentCurrency(currencies.get(0));
                if (priceGoesBelow() || priceGoesAbove()){
                    NotificationManager notificationManager = (NotificationManager) holder.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(holder.getNotificationId(), holder.getNotification());
                }
            }

            private boolean priceGoesAbove(){
                return holder.isPriceGoesAbove() && holder.getCurrentCurrency().getPriceUsd() > holder.getPriceAbove();
            }

            private boolean priceGoesBelow(){
                return holder.isPriceGoesBelow() && holder.getCurrentCurrency().getPriceUsd() < holder.getPriceBelow();
            }

        }.execute();
    }

    public void saveCurrencyToDatabase(final SavedCurrency savedCurrency, final Context context)  {
        new AsyncTask<Void, Void, SavedCurrency>() {

            @Override
            protected SavedCurrency doInBackground(Void... voids) {
                SavedCurrency currency = ApplicationDatabase.getArsenicDatabase().savedCurrencyDao().findCurrencyById(savedCurrency.getId());
                if (currency == null){
                    ApplicationDatabase.getArsenicDatabase().savedCurrencyDao().saveCurrency(savedCurrency);
                }
                return currency;
            }

            @Override
            protected void onPostExecute(SavedCurrency currency) {
                if (currency != null){
                    Toast.makeText(context, String.format("Currency %s already in favourite", currency.getName()), Toast.LENGTH_LONG).show();
                }
            }

        }.execute();
    }

    public void deleteCurrencyFromDatabase(final String id, final Context context) {
        new AsyncTask<Void, Void, SavedCurrency>() {

            @Override
            protected SavedCurrency doInBackground(Void... voids) {
                SavedCurrency savedCurrency = ApplicationDatabase.getArsenicDatabase().savedCurrencyDao().findCurrencyById(id);
                if (savedCurrency != null){
                    ApplicationDatabase.getArsenicDatabase().savedCurrencyDao().deleteCurrency(savedCurrency);
                }
                return savedCurrency;
            }

            @Override
            protected void onPostExecute(SavedCurrency currencies) {
                if (currencies == null){
                    Toast.makeText(context, String.format("Currency already deleted"), Toast.LENGTH_LONG).show();
                }
            }

        }.execute();
    }

    public SavedCurrency checkSavedCurrencyRecordExistence(final String id, final ImageView starImage) throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, SavedCurrency>() {

            @Override
            protected SavedCurrency doInBackground(Void... voids) {
                SavedCurrency savedCurrency = ApplicationDatabase.getArsenicDatabase().savedCurrencyDao().findCurrencyById(id);
                return savedCurrency;
            }

            @Override
            protected void onPostExecute(SavedCurrency currencies) {
                if (currencies != null){
                    starImage.setVisibility(View.VISIBLE);
                } else {
                    starImage.setVisibility(View.GONE);
                }
            }

        }.execute().get();
    }

    public void loadAllCurrencies(GuiComponentsHolder holder) {
        if (Services.NETWORK_SERVICE.isOnline(holder.getContext())) {
            String currencyType = holder.getCurrencySpinner().getSelectedItem().toString();
            createAsyncTaskLoadCurrencies(currencyType, holder);
            loadFavouriteCurrencies(holder);
        }
        Services.GUI_SERVICE.showOrHideOnNoConnection(holder);
    }

    private void createAsyncTaskLoadCurrencies(final String currencyType, final GuiComponentsHolder holder) {
        new AsyncTask<Void, Void, List<Currency>>() {

            @Override
            protected List<Currency> doInBackground(Void... voids) {
                GlobalData globalData = new ObjectMapper().convertValue(holder.getGlobalDataRest().getGlobalData(), new TypeReference<GlobalData>() {});
                return new ObjectMapper().convertValue(holder.getCurrencyRest().getConvertedCurrencies(currencyType, globalData.getActiveCurrencies()), new TypeReference<List<Currency>>() {});
            }

            @Override
            protected void onPostExecute(List<Currency> currencies) {
                ArsenicGuiInitializer.cryptoCurrencyList.clear();
                ArsenicGuiInitializer.cryptoCurrencyList.addAll(currencies);
                ArsenicGuiInitializer.cryptoCurrencyListAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private void loadFavouriteCurrencies(final GuiComponentsHolder holder) {
        new AsyncTask<Void, Void, List<Currency>>() {

            @Override
            protected List<Currency> doInBackground(Void... voids) {
                List<SavedCurrency> savedCurrencies = ApplicationDatabase.getArsenicDatabase().savedCurrencyDao().findAll();
                List<Currency> currencies = new ArrayList<>();
                for (SavedCurrency savedCurrency : savedCurrencies){
                    List<Currency> currency = new ObjectMapper().convertValue(holder.getCurrencyRest().getCurrency(savedCurrency.getId(), holder.getCurrencySpinner().getSelectedItem().toString()), new TypeReference<List<Currency>>() {});
                    currencies.add(currency.get(0));
                }
                return currencies;
            }

            @Override
            protected void onPostExecute(List<Currency> currencies) {
                ArsenicGuiInitializer.favouriteCryptoCurrencyList.clear();
                ArsenicGuiInitializer.favouriteCryptoCurrencyList.addAll(currencies);
                ArsenicGuiInitializer.favouriteCryptoCurrencyListAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
