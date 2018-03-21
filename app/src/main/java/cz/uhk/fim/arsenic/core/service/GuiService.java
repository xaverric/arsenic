package cz.uhk.fim.arsenic.core.service;

import android.view.View;
import android.widget.Toast;

import cz.uhk.fim.arsenic.core.initialize.GuiComponentsHolder;

public class GuiService {

    public void showOrHideOnNoConnection(GuiComponentsHolder holder) {
        if (Services.NETWORK_SERVICE.isOnline(holder.getContext()) && holder.getNoConnectionLayout().getVisibility() == View.VISIBLE) {
            holder.getNoConnectionLayout().setVisibility(View.GONE);
            holder.getContentLayout().setVisibility(View.VISIBLE);
            Toast toast = Toast.makeText(holder.getContext(), "Reloading!", Toast.LENGTH_SHORT);
            toast.show();
        } else if (!Services.NETWORK_SERVICE.isOnline(holder.getContext()) &&  holder.getNoConnectionLayout().getVisibility() == View.GONE) {
            holder.getNoConnectionLayout().setVisibility(View.VISIBLE);
            holder.getContentLayout().setVisibility(View.GONE);
        } else if (!Services.NETWORK_SERVICE.isOnline(holder.getContext()) &&  holder.getNoConnectionLayout().getVisibility() == View.VISIBLE) {
            Toast toast = Toast.makeText(holder.getContext(), "Still no connection. Try again...", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
