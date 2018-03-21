package cz.uhk.fim.arsenic.core.initialize;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.EnumSet;

import cz.uhk.fim.arsenic.R;
import cz.uhk.fim.arsenic.core.android.adapter.CurrencyListAdapter;
import cz.uhk.fim.arsenic.core.configuration.CurrencyType;
import cz.uhk.fim.arsenic.core.model.Currency;
import cz.uhk.fim.arsenic.core.service.Services;

public class ArsenicGuiInitializer {

    public static ArrayList<Currency> cryptoCurrencyList;
    public static ArrayAdapter<Currency> cryptoCurrencyListAdapter;

    private GuiComponentsHolder holder;
    private Context context;

    public ArsenicGuiInitializer(GuiComponentsHolder holder, Context context) {
        this.holder = holder;
        this.context = context;
    }

    public void initAll() {
        initSpinners();
        initRefreshLayout();
        initList();
    }

    private void initSpinners() {
        final ArrayAdapter<CurrencyType> currencyAdapter = new ArrayAdapter(context, R.layout.spinner_item, EnumSet.allOf(CurrencyType.class).toArray());
        holder.getCurrencySpinner().setAdapter(currencyAdapter);
        holder.getCurrencySpinner().setSelection(0);
        holder.getCurrencySpinner().setSelected(true);

        final ArrayAdapter<CharSequence> limitAdapter = ArrayAdapter.createFromResource(context, R.array.limit, R.layout.spinner_item);
        holder.getLimitSpinner().setAdapter(limitAdapter);
        holder.getLimitSpinner().setSelection(0);
        holder.getLimitSpinner().setSelected(true);
    }

    private void initRefreshLayout() {
        holder.getRefreshLayout().setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Services.ASSYNC_TASK_SERVICE.loadAllCurrencies(holder);
                    }
                }
        );
    }

    private void initList() {
        cryptoCurrencyList = new ArrayList<>();
        cryptoCurrencyListAdapter = new CurrencyListAdapter(context, cryptoCurrencyList);
        holder.getCryptoList().setAdapter(cryptoCurrencyListAdapter);
    }
}
