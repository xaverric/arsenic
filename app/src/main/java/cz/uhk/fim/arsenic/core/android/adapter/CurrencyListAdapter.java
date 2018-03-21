package cz.uhk.fim.arsenic.core.android.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import cz.uhk.fim.arsenic.R;
import cz.uhk.fim.arsenic.core.configuration.Configuration;
import cz.uhk.fim.arsenic.core.configuration.CurrencyType;
import cz.uhk.fim.arsenic.core.model.Currency;

public class CurrencyListAdapter extends ArrayAdapter<Currency> {

    private class ViewHolder{
        TextView heading;
        TextView btcPriceDescription;
        TextView usdPriceDescription;
        TextView customPriceDescription;
        ImageView rankOneHourImage;
        ImageView rank24HoursImage;
        ImageView rank7DaysImage;
        ImageView cryptoImage;

        public TextView getHeading() {
            return heading;
        }

        public void setHeading(TextView heading) {
            this.heading = heading;
        }

        public TextView getBtcPriceDescription() {
            return btcPriceDescription;
        }

        public void setBtcPriceDescription(TextView btcPriceDescription) {
            this.btcPriceDescription = btcPriceDescription;
        }

        public TextView getUsdPriceDescription() {
            return usdPriceDescription;
        }

        public void setUsdPriceDescription(TextView usdPriceDescription) {
            this.usdPriceDescription = usdPriceDescription;
        }

        public TextView getCustomPriceDescription() {
            return customPriceDescription;
        }

        public void setCustomPriceDescription(TextView customPriceDescription) {
            this.customPriceDescription = customPriceDescription;
        }

        public ImageView getRankOneHourImage() {
            return rankOneHourImage;
        }

        public void setRankOneHourImage(ImageView rankOneHourImage) {
            this.rankOneHourImage = rankOneHourImage;
        }

        public ImageView getRank24HoursImage() {
            return rank24HoursImage;
        }

        public void setRank24HoursImage(ImageView rank24HoursImage) {
            this.rank24HoursImage = rank24HoursImage;
        }

        public ImageView getRank7DaysImage() {
            return rank7DaysImage;
        }

        public void setRank7DaysImage(ImageView rank7DaysImage) {
            this.rank7DaysImage = rank7DaysImage;
        }

        public ImageView getCryptoImage() {
            return cryptoImage;
        }

        public void setCryptoImage(ImageView cryptoImage) {
            this.cryptoImage = cryptoImage;
        }
    }

    private static final String COIN_FORMAT = ".######";
    private static final String CURRENCY_FORMAT = ".##";

    private List<Currency> currencies;
    private Resources resources;
    private ViewHolder viewHolder;

    public CurrencyListAdapter(@NonNull Context context, List<Currency> currencies) {
        super(context, 0, currencies);
        this.currencies = currencies;
        this.resources = getContext().getResources();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View listItem, @NonNull ViewGroup parent) {
        if (listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
            createViewHolder(listItem);
        } else {
            viewHolder = (ViewHolder) listItem.getTag();
        }
        setViewHolderValues(position);
        return listItem;
    }

    private void setViewHolderValues(int position){
        Currency currency = currencies.get(position);
        viewHolder.getHeading().setText(String.format(resources.getString(R.string.currency_name), currency.getName(), currency.getSymbol()));
        viewHolder.getBtcPriceDescription().setText(String.format(resources.getString(R.string.btc_currency), setPrecision(currency.getPriceBtc(), COIN_FORMAT)));
        viewHolder.getUsdPriceDescription().setText(String.format(resources.getString(R.string.usd_currency), setPrecision(currency.getPriceUsd(), CURRENCY_FORMAT)));
        setCustomCurrency(currency);
        viewHolder.getRankOneHourImage().setBackgroundResource(findImageByPercentChange(currency.getPercentChange1H()));
        viewHolder.getRank24HoursImage().setBackgroundResource(findImageByPercentChange(currency.getPercentChange24H()));
        viewHolder.getRank7DaysImage().setBackgroundResource(findImageByPercentChange(currency.getPercentChange7D()));
        viewHolder.getCryptoImage().setBackgroundResource(findImage(currency.getSymbol().toLowerCase()));
    }

    private void createViewHolder(View listItem){
        viewHolder = new ViewHolder();
        viewHolder.setHeading((TextView) listItem.findViewById(R.id.heading));
        viewHolder.setBtcPriceDescription((TextView) listItem.findViewById(R.id.btcPriceDescription));
        viewHolder.setUsdPriceDescription((TextView) listItem.findViewById(R.id.usdPriceDescription));
        viewHolder.setCustomPriceDescription((TextView) listItem.findViewById(R.id.customPriceDescription));
        viewHolder.setRankOneHourImage((ImageView) listItem.findViewById(R.id.rankOneHourImage));
        viewHolder.setRank24HoursImage((ImageView) listItem.findViewById(R.id.rank24HoursImage));
        viewHolder.setRank7DaysImage((ImageView) listItem.findViewById(R.id.rank7DaysImage));
        viewHolder.setCryptoImage((ImageView) listItem.findViewById(R.id.cryptoImage));
        listItem.setTag(viewHolder);
    }

    private String setPrecision(Double input, String pattern){
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(input);
    }

    private int findImageByPercentChange(Double percentChange){
        if (percentChange < 0.0){
            return findImage("arrow_down");
        }
        return findImage("arrow_up");
    }

    private int findImage(String imageId){
        Resources r = getContext().getResources();
        Integer reference = r.getIdentifier(imageId, "drawable", getContext().getPackageName());
        if (reference != 0){
            return reference;
        }
        return r.getIdentifier("no_icon", "drawable", getContext().getPackageName());
    }

    private void setCustomCurrency(Currency currency){
        String selectedValue = Configuration.currencyType.name();
        if (selectedValue != null){
            if (selectedValue.equals(CurrencyType.CZK.toString())){
                viewHolder.getCustomPriceDescription().setText(String.format(resources.getString(R.string.czk_currency), setPrecision(currency.getPriceCzk(), CURRENCY_FORMAT)));
            } else if (selectedValue.equals(CurrencyType.EUR.toString())){
                viewHolder.getCustomPriceDescription().setText(String.format(resources.getString(R.string.eur_currency), setPrecision(currency.getPriceEur(), CURRENCY_FORMAT)));
            }else if (selectedValue.equals(CurrencyType.GBP.toString())){
                viewHolder.getCustomPriceDescription().setText(String.format(resources.getString(R.string.gbp_currency), setPrecision(currency.getPriceGbp(), CURRENCY_FORMAT)));
            }else if (selectedValue.equals(CurrencyType.PLN.toString())){
                viewHolder.getCustomPriceDescription().setText(String.format(resources.getString(R.string.pln_currency), setPrecision(currency.getPricePln(), CURRENCY_FORMAT)));
            }else if (selectedValue.equals(CurrencyType.RUB.toString())){
                viewHolder.getCustomPriceDescription().setText(String.format(resources.getString(R.string.rub_currency), setPrecision(currency.getPriceRub(), CURRENCY_FORMAT)));
            }
        }
    }
}
