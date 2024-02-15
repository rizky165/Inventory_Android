package com.rizkyfachrieza.inventoryapps.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rizkyfachrieza.inventoryapps.R;
import com.rizkyfachrieza.inventoryapps.model.ModelKategori;
import com.rizkyfachrieza.inventoryapps.model.Preferences;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin;
    Button buttonDaftar;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.titletoolbar);
        textView.setText("Inventory Apps");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().hide();

        buttonDaftar = (Button) findViewById(R.id.buttonDaftar);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonDaftar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,DaftarActivity.class);
                startActivity(intent);
            };
        });

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            };
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getLoggedInStatus(getBaseContext())){
            startActivity(new Intent(getBaseContext(),DashboardActivity.class));
            finish();
        }
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