package com.rizkyfachrieza.inventoryapps.view;

import static com.rizkyfachrieza.inventoryapps.model.Preferences.getSharedPreference;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import com.rizkyfachrieza.inventoryapps.model.Preferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rizkyfachrieza.inventoryapps.R;
import com.rizkyfachrieza.inventoryapps.model.Preferences;

public class DashboardActivity extends AppCompatActivity {

    ImageView dataBarang, dataBarangMasuk,dataBarangKeluar, supplier;
    TextView welcome;
    Button buttonKeluar;
    ProgressDialog nDialog;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.titletoolbar);
        textView.setText("Dashboard");
        dataBarang = (ImageView) findViewById(R.id.imageView3);
        dataBarangMasuk = (ImageView) findViewById(R.id.imageView);
        dataBarangKeluar = (ImageView) findViewById(R.id.imageView2);
        supplier = (ImageView) findViewById(R.id.imageView4);

        buttonKeluar = (Button) findViewById(R.id.buttonKeluar);
        welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("Selamat datang, "+Preferences.getLoggedInUser(getBaseContext()));
        buttonKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nDialog = new ProgressDialog(DashboardActivity.this);
                nDialog.setMessage("Loading...");
                nDialog.setTitle("Keluar");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(false);
                nDialog.setCanceledOnTouchOutside(false);
                nDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Preferences.clearLoggedInUser(getBaseContext());
                        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        nDialog.dismiss();
                    }
                }, 3000);

            }
        });

        dataBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, DataBarangActivity.class);
                startActivity(intent);
            }
        });
        dataBarangMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, DataBarangMasukActivity.class);
                startActivity(intent);
            }
        });
        dataBarangKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, DataBarangKeluarActivity.class);
                startActivity(intent);
            }
        });

        supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, SupplierActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}