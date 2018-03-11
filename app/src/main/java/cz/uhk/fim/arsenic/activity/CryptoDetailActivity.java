package cz.uhk.fim.arsenic.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

import cz.uhk.fim.arsenic.R;

@Fullscreen
@EActivity(R.layout.activity_crypto_detail)
public class CryptoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_detail);
    }

}
