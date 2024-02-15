package com.rizkyfachrieza.inventoryapps.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

public class TambahDataBarangKeluarActivity extends AppCompatActivity {

    Spinner BarangSpinner, SupplierSpinner;
    SqliteHelper sqliteHelper;
    EditText etNamaBarang, etStok, etSatuan;
    Button buttonSimpan, btnDate;
    ProgressDialog nDialog;
    String tglMasuk = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_barang_keluar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.titletoolbar);
        textView.setText("Tambah Barang Keluar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

        sqliteHelper = new SqliteHelper(this);
        ArrayList<ModelBarang> responseBarang = sqliteHelper.readBarang();

        List<String> listBarang = new ArrayList<>();
        listBarang.add("Pilih Kode Barang");

        for(int i = 0; i < responseBarang.size(); i++){
            listBarang.add(responseBarang.get(i).getKodeBarang());
        }

        ArrayAdapter<String> barangAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listBarang);
        barangAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BarangSpinner.setAdapter(barangAdapter);

        ArrayList<ModelSupplier> responseSupplier = sqliteHelper.readSupplier();

        List<String> listSupplier = new ArrayList<>();
        listSupplier.add("Pilih Supplier");

        for(int i = 0; i < responseSupplier.size(); i++){
            listSupplier.add(responseSupplier.get(i).getSupplier());
        }

        ArrayAdapter<String> supplierAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listSupplier);
        supplierAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SupplierSpinner.setAdapter(supplierAdapter);

        BarangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(position > 0){
                    etNamaBarang.setText(responseBarang.get(position-1).getNamaBarang());
                    etStok.setText(responseBarang.get(position-1).getStokBarang());
                    etSatuan.setText(responseBarang.get(position-1).getSatuan());
                }else{
                    etNamaBarang.setText("");
                    etStok.setText("");
                    etSatuan.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        buttonSimpan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(BarangSpinner.getSelectedItem().toString().equals("Pilih Kode Barang") || SupplierSpinner.getSelectedItem().toString().equals("Pilih Supplier") || tglMasuk.equals("")){
                    Snackbar.make(buttonSimpan, "Masih ada data yang kosong!", Snackbar.LENGTH_LONG).show();

                }else{
                    nDialog = new ProgressDialog(TambahDataBarangKeluarActivity.this);
                    nDialog.setMessage("Loading...");
                    nDialog.setTitle("Tambah barang keluar");
                    nDialog.setIndeterminate(false);
                    nDialog.setCancelable(false);
                    nDialog.setCanceledOnTouchOutside(false);
                    nDialog.show();
                    sqliteHelper.addBarangKeluar(responseBarang.get(BarangSpinner.getSelectedItemPosition()-1).getId(), responseSupplier.get(SupplierSpinner.getSelectedItemPosition()-1).getId(),tglMasuk);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Intent intent=new Intent(TambahDataBarangKeluarActivity.this, DataBarangKeluarActivity.class);
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
        BarangSpinner = findViewById(R.id.barang_spinner);
        SupplierSpinner = findViewById(R.id.supplier_spinner);
        etNamaBarang = (EditText) findViewById(R.id.editTextNamaBarang);
        etStok = (EditText) findViewById(R.id.editTextStok);
        etSatuan = (EditText) findViewById(R.id.editTextSatuan);
        buttonSimpan = (Button) findViewById(R.id.buttonSimpan);
        btnDate = (Button) findViewById(R.id.btnDate);

    }

    public void showDate(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "data");
        datePickerFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String tahun = ""+datePicker.getYear();
                String bulan = ""+(datePicker.getMonth()+1);
                String hari = ""+datePicker.getDayOfMonth();
                String text = hari+"-"+bulan+"-"+tahun;
                tglMasuk = text;
                btnDate.setText(text);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent(TambahDataBarangKeluarActivity.this, DataBarangKeluarActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed(){
        Intent intent=new Intent(TambahDataBarangKeluarActivity.this, DataBarangKeluarActivity.class);
        startActivity(intent);
        finish();
    }

}