package cz.uhk.fim.arsenic.core.android.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import cz.uhk.fim.arsenic.core.model.Currency;
import cz.uhk.fim.arsenic.core.rest.CurrencyRest;
import cz.uhk.fim.arsenic.core.service.NotificationSchedulerService;

@EReceiver
public class CurrencyNotificationReceiver extends BroadcastReceiver {

    //vytvořit soubor s konstantami
    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";
    public static final String ACTION = "currency_notification_action";

    @RestService
    public CurrencyRest<Currency> currencyRest;

    public static Currency currentCurrency;
    private boolean priceGoesAbove;
    private boolean priceGoesBelow;
    private double priceAbove;
    private double priceBelow;
    private Notification notification;
    private int notificationId;

    private Context context;

    @ReceiverAction(actions = CurrencyNotificationReceiver.ACTION)
    public void onAction(Context context, Intent intent){
        this.context = context;

        notification = intent.getParcelableExtra(NOTIFICATION);
        notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        String currencyId = intent.getStringExtra(NotificationSchedulerService.CURRENCY_ID);
        priceGoesAbove = intent.getBooleanExtra(NotificationSchedulerService.PRICE_GOES_ABOVE, false);
        priceGoesBelow = intent.getBooleanExtra(NotificationSchedulerService.PRICE_GOES_BELOW, false);
        priceAbove = intent.getDoubleExtra(NotificationSchedulerService.PRICE_ABOVE, 0.0);
        priceBelow = intent.getDoubleExtra(NotificationSchedulerService.PRICE_BELOW, 0.0);
        createAsyncTaskLoadCurrency(currencyId);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

    }

    private boolean priceGoesAbove(){
        return priceGoesAbove && currentCurrency.getPriceUsd() > priceAbove;
    }

    private boolean priceGoesBelow(){
        return priceGoesBelow && currentCurrency.getPriceUsd() < priceBelow;
    }

    @SuppressLint("StaticFieldLeak")
    private void createAsyncTaskLoadCurrency(final String currencyId) {
        new AsyncTask<Void, Void, List<Currency>>() {

            @Override
            protected List<Currency> doInBackground(Void... voids) {
                return new ObjectMapper().convertValue(currencyRest.getCurrency(currencyId), new TypeReference<List<Currency>>() {});
            }

            @Override
            protected void onPostExecute(List<Currency> currencies) {
                CurrencyNotificationReceiver.currentCurrency = currencies.get(0);
                if (priceGoesBelow() || priceGoesAbove()){
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(notificationId, notification);
                }
            }
        }.execute();
    }
}