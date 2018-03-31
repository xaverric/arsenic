package cz.uhk.fim.arsenic.core.android.notification;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;
import org.androidannotations.rest.spring.annotations.RestService;

import cz.uhk.fim.arsenic.core.initialize.GuiComponentsHolder;
import cz.uhk.fim.arsenic.core.rest.CurrencyRest;
import cz.uhk.fim.arsenic.core.service.NotificationSchedulerService;
import cz.uhk.fim.arsenic.core.service.Services;

@EReceiver
public class CurrencyNotificationReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";
    public static final String ACTION = "currency_notification_action";

    @RestService
    public CurrencyRest currencyRest;

    @ReceiverAction(actions = CurrencyNotificationReceiver.ACTION)
    public void onAction(Context context, Intent intent){
        Services.ASSYNC_TASK_SERVICE.loadCurrencyOnNotificationReceived(prepateHolder(context, intent), currencyRest);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
    }

    private GuiComponentsHolder prepateHolder(Context context, Intent intent){
        GuiComponentsHolder holder = new GuiComponentsHolder();
        holder.setContext(context);
        holder.setNotification((Notification) intent.getParcelableExtra(NOTIFICATION));
        holder.setNotificationId(intent.getIntExtra(NOTIFICATION_ID, 0));
        holder.setPriceGoesAbove(intent.getBooleanExtra(NotificationSchedulerService.PRICE_GOES_ABOVE, false));
        holder.setPriceGoesBelow(intent.getBooleanExtra(NotificationSchedulerService.PRICE_GOES_BELOW, false));
        holder.setPriceAbove(intent.getDoubleExtra(NotificationSchedulerService.PRICE_ABOVE, 0.0));
        holder.setPriceBelow(intent.getDoubleExtra(NotificationSchedulerService.PRICE_BELOW, 0.0));
        holder.setCurrencyId(intent.getStringExtra(NotificationSchedulerService.CURRENCY_ID));
        return holder;
    }
}