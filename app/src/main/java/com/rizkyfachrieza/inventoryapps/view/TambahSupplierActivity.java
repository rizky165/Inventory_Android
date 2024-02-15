package com.rizkyfachrieza.inventoryapps.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.rizkyfachrieza.inventoryapps.R;
import com.rizkyfachrieza.inventoryapps.dbhelper.SqliteHelper;
import com.rizkyfachrieza.inventoryapps.model.ModelBarang;
import com.rizkyfachrieza.inventoryapps.model.ModelKategori;
import com.rizkyfachrieza.inventoryapps.model.ModelSupplier;
import com.rizkyfachrieza.inventoryapps.model.ModelUser;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TambahSupplierActivity extends AppCompatActivity {

    SqliteHelper sqliteHelper;
    EditText etSupplier;
    Button buttonSimpan;
    ProgressDialog nDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_supplier);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.titletoolbar);
        textView.setText("Tambah Supplier");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

        sqliteHelper = new SqliteHelper(this);

        buttonSimpan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(etSupplier.getText().toString().equals("")){
                    Snackbar.make(buttonSimpan, "Masih ada data yang kosong!", Snackbar.LENGTH_LONG).show();

                }else{
                    nDialog = new ProgressDialog(TambahSupplierActivity.this);
                    nDialog.setMessage("Loading...");
                    nDialog.setTitle("Tambah data supplier");
                    nDialog.setIndeterminate(false);
                    nDialog.setCancelable(false);
                    nDialog.setCanceledOnTouchOutside(false);
                    nDialog.show();
                    sqliteHelper.addSupplier(new ModelSupplier(null, etSupplier.getText().toString()));
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Intent intent=new Intent(TambahSupplierActivity.this, SupplierActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Tambah data berhasil!", Toast.LENGTH_LONG).show();
                            nDialog.dismiss();
                        }
                    }, 3000);
                }

            };
        });
    }

    private void initViews(){
        etSupplier = (EditText) findViewById(R.id.editTextSupplier);
        buttonSimpan = (Button) findViewById(R.id.buttonSimpan);
    }


    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent(TambahSupplierActivity.this, SupplierActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed(){
        Intent intent=new Intent(TambahSupplierActivity.this, SupplierActivity.class);
        startActivity(intent);
        finish();
    }
}