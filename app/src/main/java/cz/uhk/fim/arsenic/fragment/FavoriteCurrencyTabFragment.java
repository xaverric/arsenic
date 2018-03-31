package cz.uhk.fim.arsenic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cz.uhk.fim.arsenic.R;
import cz.uhk.fim.arsenic.activity.ArsenicActivity;
import cz.uhk.fim.arsenic.activity.CryptoDetailActivity_;
import cz.uhk.fim.arsenic.core.android.adapter.CurrencyListAdapter;
import cz.uhk.fim.arsenic.core.configuration.Configuration;
import cz.uhk.fim.arsenic.core.initialize.ArsenicGuiInitializer;
import cz.uhk.fim.arsenic.core.model.Currency;
import cz.uhk.fim.arsenic.core.service.Services;

public class FavoriteCurrencyTabFragment extends Fragment {

    private View view;
    private ListView favouriteCryptoList;

    private Currency currency;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favorite_fragment, container, false);
        initList();
        return view;
    }

    private void initList() {
        favouriteCryptoList = view.findViewById(R.id.favouriteCryptoList);
        initListAdapter();
        createListeners();
    }

    private void initListAdapter(){
        ArsenicGuiInitializer.favouriteCryptoCurrencyList = new ArrayList<>();
        ArsenicGuiInitializer.favouriteCryptoCurrencyListAdapter = new CurrencyListAdapter(getContext(), ArsenicGuiInitializer.favouriteCryptoCurrencyList);
        favouriteCryptoList.setAdapter(ArsenicGuiInitializer.favouriteCryptoCurrencyListAdapter);
    }

    private void createListeners() {
        favouriteCryptoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                createDatiledView((Currency) adapterView.getItemAtPosition(i));
            }
        });
        favouriteCryptoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                currency = (Currency) adapterView.getItemAtPosition(i);
                showMenu(view);
                return true;
            }
        });
    }

    private void createAlert(Currency currency) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.create_notification_dialog, null);
        Services.ALERT_DIALOG_SERVICE.createCurrencyAlert(promptsView, currency, getContext());
    }

    private void createDatiledView(Currency currency) {
        Intent intent = new Intent(getContext(), CryptoDetailActivity_.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currency", currency);
        bundle.putSerializable("convertedCurrency", Configuration.currencyType);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void removeCurrencyFromFavourite(Currency currency) {
        Services.ASSYNC_TASK_SERVICE.deleteCurrencyFromDatabase(currency.getId(), getContext());
        ArsenicActivity.refresh();
    }

    private void showMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addAlertItemFV:
                        createAlert(currency);
                        return true;
                    case R.id.removeFromFavouriteItemFV:
                        removeCurrencyFromFavourite(currency);
                        return true;
                    case R.id.moreInformationItemFV:
                        createDatiledView(currency);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.remove_currency_menu);
        popup.show();
    }
}
