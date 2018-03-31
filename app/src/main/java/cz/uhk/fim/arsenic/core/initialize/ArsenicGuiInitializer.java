package cz.uhk.fim.arsenic.core.initialize;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.EnumSet;

import cz.uhk.fim.arsenic.R;
import cz.uhk.fim.arsenic.core.android.adapter.SectionsPageAdapter;
import cz.uhk.fim.arsenic.core.configuration.CurrencyType;
import cz.uhk.fim.arsenic.core.model.Currency;
import cz.uhk.fim.arsenic.fragment.AllCurrencyTabFragment;
import cz.uhk.fim.arsenic.fragment.FavoriteCurrencyTabFragment;

public class ArsenicGuiInitializer {

    public static ArrayList<Currency> cryptoCurrencyList;
    public static ArrayAdapter<Currency> cryptoCurrencyListAdapter;

    public static ArrayList<Currency> favouriteCryptoCurrencyList;
    public static ArrayAdapter<Currency> favouriteCryptoCurrencyListAdapter;

    private GuiComponentsHolder holder;
    private Context context;

    public ArsenicGuiInitializer(GuiComponentsHolder holder, Context context) {
        this.holder = holder;
        this.context = context;
    }

    public void initAll() {
        initSpinners();
        initTabLayout();
    }

    private void initSpinners() {
        final ArrayAdapter<CurrencyType> currencyAdapter = new ArrayAdapter(context, R.layout.spinner_item, EnumSet.allOf(CurrencyType.class).toArray());
        holder.getCurrencySpinner().setAdapter(currencyAdapter);
        holder.getCurrencySpinner().setSelection(0);
        holder.getCurrencySpinner().setSelected(true);
    }

    private void initTabLayout (){
        setupViewPager(holder.getContainer());
        holder.getTabs().setupWithViewPager(holder.getContainer());
        holder.getTabs().setSelectedTabIndicatorColor(ContextCompat.getColor(holder.getContext(), R.color.colorWhite));
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(holder.getFragmentManager());
        adapter.addFragment(new AllCurrencyTabFragment(), "ALL");
        adapter.addFragment(new FavoriteCurrencyTabFragment(), "FAVOURITE");
        viewPager.setAdapter(adapter);
    }
}
