package cz.uhk.fim.arsenic.core.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import cz.uhk.fim.arsenic.core.initialize.ArsenicGuiInitializer;
import cz.uhk.fim.arsenic.core.initialize.GuiComponentsHolder;
import cz.uhk.fim.arsenic.core.model.Currency;

public class AssyncTaskService {

    public void loadAllCurrencies(GuiComponentsHolder holder) {
        if (Services.NETWORK_SERVICE.isOnline(holder.getContext())) {
            String currencyType = holder.getCurrencySpinner().getSelectedItem().toString();
            Integer limit = Integer.parseInt(holder.getLimitSpinner().getSelectedItem().toString());
            createAsyncTaskLoadCurrencies(currencyType, limit, holder);
        }
        Services.GUI_SERVICE.showOrHideOnNoConnection(holder);
    }

    @SuppressLint("StaticFieldLeak")
    private void createAsyncTaskLoadCurrencies(final String currencyType, final Integer limit, final GuiComponentsHolder holder) {
        new AsyncTask<Void, Void, List<Currency>>() {

            @Override
            protected List<Currency> doInBackground(Void... voids) {
                return new ObjectMapper().convertValue(holder.getCurrencyRest().getConvertedCurrencies(currencyType, limit), new TypeReference<List<Currency>>() {});
            }

            @Override
            protected void onPostExecute(List<Currency> currencies) {
                ArsenicGuiInitializer.cryptoCurrencyList.clear();
                ArsenicGuiInitializer.cryptoCurrencyList.addAll(currencies);
                ArsenicGuiInitializer.cryptoCurrencyListAdapter.notifyDataSetChanged();
                holder.getRefreshLayout().setRefreshing(false);
            }
        }.execute();
    }
}
