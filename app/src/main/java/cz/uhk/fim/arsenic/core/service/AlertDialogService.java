package cz.uhk.fim.arsenic.core.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import cz.uhk.fim.arsenic.R;
import cz.uhk.fim.arsenic.core.model.Currency;

public class AlertDialogService {

    private static final int FIVE_MINUTES_IN_MS = 5000;//300000;
    private static final int HOUR_IN_MS = 100000;//3600000;

    private CheckBox checkBoxPriceAbove;
    private CheckBox checkBoxPriceBelow;
    private Button priceAboveBtnMinus ;
    private Button priceAboveBtnPlus;
    private Button priceBelowBtnMinus;
    private Button priceBelowBtnPlus;
    private TextView priceAbove;
    private TextView priceBelow;
    private RadioButton interval5m;
    private RadioButton intervalHour;

    @SuppressLint("RestrictedApi")
    public void createCurrencyAlert(View alertView, final Currency currency, final Context context){
        initGuiElements(alertView);
        initValue(currency);
        initListeners();

        new AlertDialog.Builder(context)
                .setView(alertView)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                scheduleNotification(currency, context);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .create()
                .show();
    }

    private void scheduleNotification(Currency currency, Context context){
        if (interval5m.isChecked()){
            Services.NOTIFICATION_SCHEDULER_SERVICE.scheduleCurrencyNotification(currency, context, FIVE_MINUTES_IN_MS, checkBoxPriceAbove.isChecked(), checkBoxPriceBelow.isChecked(), priceAbove.getText().toString(), priceBelow.getText().toString());
        } else if (intervalHour.isChecked()){
            Services.NOTIFICATION_SCHEDULER_SERVICE.scheduleCurrencyNotification(currency, context, HOUR_IN_MS, checkBoxPriceAbove.isChecked(), checkBoxPriceBelow.isChecked(), priceAbove.getText().toString(), priceBelow.getText().toString());
        }
    }

    private void initGuiElements(View alertView){
        checkBoxPriceAbove = alertView.findViewById(R.id.checkBoxPriceAbove);
        checkBoxPriceBelow = alertView.findViewById(R.id.checkBoxPriceBelow);
        priceAboveBtnMinus = alertView.findViewById(R.id.priceAboveBtnMinus);
        priceAboveBtnPlus = alertView.findViewById(R.id.priceAboveBtnPlus);
        priceBelowBtnMinus = alertView.findViewById(R.id.priceBelowBtnMinus);
        priceBelowBtnPlus = alertView.findViewById(R.id.priceBelowBtnPlus);
        priceAbove = alertView.findViewById(R.id.alertPriceAbove);
        priceBelow = alertView.findViewById(R.id.alertPriceBelow);
        interval5m = alertView.findViewById(R.id.interval5m);
        intervalHour = alertView.findViewById(R.id.intervalHour);
    }

    private void initValue(Currency currency){
        priceAbove.setText(Double.toString(currency.getPriceUsd()));
        priceBelow.setText(Double.toString(currency.getPriceUsd()));
    }

    private void initListeners(){
        priceAboveBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double price = Double.parseDouble(priceAbove.getText().toString());
                priceAbove.setText(Double.toString(price - 10.0));
            }
        });

        priceAboveBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double price = Double.parseDouble(priceAbove.getText().toString());
                priceAbove.setText(Double.toString(price + 10.0));
            }
        });

        priceBelowBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double price = Double.parseDouble(priceBelow.getText().toString());
                priceBelow.setText(Double.toString(price - 10.0));
            }
        });

        priceBelowBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double price = Double.parseDouble(priceBelow.getText().toString());
                priceBelow.setText(Double.toString(price + 10.0));
            }
        });
    }
}
