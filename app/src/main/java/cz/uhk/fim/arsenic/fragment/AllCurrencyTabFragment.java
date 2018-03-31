package cz.uhk.fim.arsenic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;

import cz.uhk.fim.arsenic.R;
import cz.uhk.fim.arsenic.activity.ArsenicActivity;
import cz.uhk.fim.arsenic.activity.CryptoDetailActivity_;
import cz.uhk.fim.arsenic.core.android.adapter.CurrencyListAdapter;
import cz.uhk.fim.arsenic.core.configuration.Configuration;
import cz.uhk.fim.arsenic.core.configuration.CurrencyType;
import cz.uhk.fim.arsenic.core.initialize.ArsenicGuiInitializer;
import cz.uhk.fim.arsenic.core.model.Currency;
import cz.uhk.fim.arsenic.core.repository.entity.SavedCurrency;
import cz.uhk.fim.arsenic.core.service.Services;

@EFragment
public class AllCurrencyTabFragment extends Fragment {

    private View view;
    private ListView cryptoList;
    private FloatingActionButton scrollUpButton;
    private SwipeRefreshLayout allFragment;

    private Currency currency;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.all_fragment, container, false);
        initRefreshLayout();
        initList();
        initScrollButton();
        return view;
    }

    private void initList() {
        cryptoList = view.findViewById(R.id.cryptoList);
        initListAdapter();
        registerForContextMenu(cryptoList);
        createListeners();
    }

    private void initScrollButton() {
        scrollUpButton = view.findViewById(R.id.scrollUpButtonAllCurrencies);
        scrollUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cryptoList.smoothScrollBy(0, 0);
                cryptoList.setSelection(0);
                scrollUpButton.hide();
            }
        });
        scrollUpButton.hide();
    }

    private void initRefreshLayout(){
        allFragment = view.findViewById(R.id.allFragment);
        allFragment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArsenicActivity.refresh();
                allFragment.setRefreshing(false);
            }
        });
    }

    private void initListAdapter() {
        ArsenicGuiInitializer.cryptoCurrencyList = new ArrayList<>();
        ArsenicGuiInitializer.cryptoCurrencyListAdapter = new CurrencyListAdapter(getContext(), ArsenicGuiInitializer.cryptoCurrencyList);
        cryptoList.setAdapter(ArsenicGuiInitializer.cryptoCurrencyListAdapter);
    }

    private void createListeners() {
        cryptoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                createDatiledView((Currency) adapterView.getItemAtPosition(i));
            }
        });
        cryptoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                currency = (Currency) adapterView.getItemAtPosition(i);
                showMenu(view);
                return true;
            }
        });
        cryptoList.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //disable swipe refresh layout when not on top
                allFragment.setEnabled(firstVisibleItem == 0);
                if (mLastFirstVisibleItem < firstVisibleItem) {
                    scrollUpButton.show();
                }
                if (mLastFirstVisibleItem > firstVisibleItem) {
                    scrollUpButton.hide();
                }
                mLastFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    private void createAlert(Currency currency) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.create_notification_dialog, null);
        Services.ALERT_DIALOG_SERVICE.createCurrencyAlert(promptsView, currency, getContext());
    }

    private void addToFavourite(Currency currency) {
        SavedCurrency savedCurrency = new SavedCurrency();
        savedCurrency.setId(currency.getId());
        savedCurrency.setName(currency.getName());
        savedCurrency.setPriceBtc(currency.getPriceBtc());
        savedCurrency.setPriceUsd(currency.getPriceUsd());
        savedCurrency.setRank(currency.getRank());
        savedCurrency.setSymbol(currency.getSymbol());
        setCustomCurrency(savedCurrency, currency);
        Services.ASSYNC_TASK_SERVICE.saveCurrencyToDatabase(savedCurrency, getContext());
        ArsenicActivity.refresh();
    }

    private void createDatiledView(Currency currency) {
        Intent intent = new Intent(getContext(), CryptoDetailActivity_.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currency", currency);
        bundle.putSerializable("convertedCurrency", Configuration.currencyType);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    private void showMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addAlertItem:
                        createAlert(currency);
                        return true;
                    case R.id.addToFavouriteItem:
                        addToFavourite(currency);
                        return true;
                    case R.id.moreInformationItem:
                        createDatiledView(currency);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.add_currency_menu);
        popup.show();
    }

    private void setCustomCurrency(SavedCurrency savedCurrency, Currency currency) {
        String selectedValue = Configuration.currencyType.name();
        if (selectedValue != null) {
            if (selectedValue.equals(CurrencyType.CZK.toString())) {
                savedCurrency.setPriceCzk(currency.getPriceCzk());
            } else if (selectedValue.equals(CurrencyType.EUR.toString())) {
                savedCurrency.setPriceEur(currency.getPriceEur());
            } else if (selectedValue.equals(CurrencyType.GBP.toString())) {
                savedCurrency.setPriceGbp(currency.getPriceGbp());
            } else if (selectedValue.equals(CurrencyType.PLN.toString())) {
                savedCurrency.setPricePln(currency.getPricePln());
            } else if (selectedValue.equals(CurrencyType.RUB.toString())) {
                savedCurrency.setPriceRub(currency.getPriceRub());
            }
        }
    }
}
