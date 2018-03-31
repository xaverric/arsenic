package cz.uhk.fim.arsenic.core.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import cz.uhk.fim.arsenic.R;
import cz.uhk.fim.arsenic.activity.ArsenicActivity;
import cz.uhk.fim.arsenic.core.android.notification.CurrencyNotificationReceiver;
import cz.uhk.fim.arsenic.core.android.notification.CurrencyNotificationReceiver_;
import cz.uhk.fim.arsenic.core.model.Currency;

public class NotificationSchedulerService {

    public static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_0";
    public static final String PRICE_GOES_ABOVE = "PRICE_GOES_ABOVE";
    public static final String PRICE_GOES_BELOW = "PRICE_GOES_BELOW";
    public static final String CURRENCY_ID = "CURRENCY_ID";
    public static final String PRICE_ABOVE = "PRICE_ABOVE";
    public static final String PRICE_BELOW = "PRICE_BELOW";

    public void scheduleCurrencyNotification(Currency currency, Context context, long delay, boolean priceGoesAbove, boolean priceGoesBelow, String priceAbove, String priceBelow) {
        int notificationId = generateRandomId();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Arsenic (" + currency.getName() + ")")
                .setContentText("Price (USD): " + currency.getPriceUsd() + " | Price (BTC): " + currency.getPriceBtc())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.btc)
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(createPendingIntent(context, notificationId));

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, CurrencyNotificationReceiver_.class);
        notificationIntent.putExtra(CurrencyNotificationReceiver.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(CurrencyNotificationReceiver.NOTIFICATION, notification);
        notificationIntent.putExtra(PRICE_GOES_ABOVE, priceGoesAbove);
        notificationIntent.putExtra(PRICE_GOES_BELOW, priceGoesBelow);
        notificationIntent.putExtra(CURRENCY_ID, currency.getId());
        notificationIntent.putExtra(PRICE_ABOVE, Double.parseDouble(priceAbove));
        notificationIntent.putExtra(PRICE_BELOW, Double.parseDouble(priceBelow));
        notificationIntent.setAction(CurrencyNotificationReceiver.ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private PendingIntent createPendingIntent(Context context, int notificationId){
        Intent intent = new Intent(context, ArsenicActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return activity;
    }

    private int generateRandomId() {
        return (int) Math.random()*99999;
    }
}