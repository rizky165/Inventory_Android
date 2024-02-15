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
import com.rizkyfachrieza.inventoryapps.model.ModelUser;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TambahDataBarangActivity extends AppCompatActivity {

    Spinner KategoriSpinner, SatuanSpinner;
    SqliteHelper sqliteHelper;
    EditText etHarga, etKodeBarang, etNamaBarang, etStok;
    Button buttonSimpan;
    ProgressDialog nDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_barang);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.titletoolbar);
        textView.setText("Tambah Data Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

        sqliteHelper = new SqliteHelper(this);
        List<String> listKategori = sqliteHelper.readKatogori();

        ArrayAdapter<String> kategoriAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listKategori);
        kategoriAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        KategoriSpinner.setAdapter(kategoriAdapter);

        List<String> listSatuan = sqliteHelper.readSatuan();

        ArrayAdapter<String> satuanAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listSatuan);
        satuanAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SatuanSpinner.setAdapter(satuanAdapter);

        buttonSimpan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(etKodeBarang.getText().toString().equals("") || etNamaBarang.getText().toString().equals("") || KategoriSpinner.getSelectedItem().toString().equals("Pilih Kategori") || etHarga.getText().toString().equals("") || etStok.getText().toString().equals("") || SatuanSpinner.getSelectedItem().toString().equals("Pilih Satuan")){
                    Snackbar.make(buttonSimpan, "Masih ada data yang kosong!", Snackbar.LENGTH_LONG).show();

                }else{
                    nDialog = new ProgressDialog(TambahDataBarangActivity.this);
                    nDialog.setMessage("Loading...");
                    nDialog.setTitle("Tambah data barang");
                    nDialog.setIndeterminate(false);
                    nDialog.setCancelable(false);
                    nDialog.setCanceledOnTouchOutside(false);
                    nDialog.show();
                    sqliteHelper.addBarang(new ModelBarang(null, etKodeBarang.getText().toString(),etNamaBarang.getText().toString(),KategoriSpinner.getSelectedItem().toString(),etHarga.getText().toString(),etStok.getText().toString(),SatuanSpinner.getSelectedItem().toString()));
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Intent intent=new Intent(TambahDataBarangActivity.this, DataBarangActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Tambah data berhasil!", Toast.LENGTH_LONG).show();
                            nDialog.dismiss();
                        }
                    }, 3000);
                }

            };
        });

        etHarga.addTextChangedListener(new TextWatcher() {
            private String setEditText = etHarga.getText().toString().trim();
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(setEditText)){
                    etHarga.removeTextChangedListener(this);
                    String replace = charSequence.toString().replaceAll("[Rp .]", "");
                    if(!replace.isEmpty()){
                        setEditText = formatRupiah(Double.parseDouble(replace));
                    }else{
                        setEditText = "";
                    }
                    etHarga.setText(setEditText);
                    etHarga.setSelection(setEditText.length());
                    etHarga.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initViews(){
        KategoriSpinner = findViewById(R.id.kategori_spinner);
        SatuanSpinner = findViewById(R.id.satuan_spinner);
        etKodeBarang = (EditText) findViewById(R.id.editTextKodeBarang);
        etNamaBarang = (EditText) findViewById(R.id.editTextNamaBarang);
        etHarga = (EditText) findViewById(R.id.editTextHarga);
        etStok = (EditText) findViewById(R.id.editTextStok);
        buttonSimpan = (Button) findViewById(R.id.buttonSimpan);
    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("IND","ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        String rupiah = numberFormat.format(number);
        String[] split = rupiah.split(",");
        int length = split[0].length();
        return split[0].substring(0,2)+". "+split[0].substring(2,length);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent(TambahDataBarangActivity.this, DataBarangActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed(){
        Intent intent=new Intent(TambahDataBarangActivity.this, DataBarangActivity.class);
        startActivity(intent);
        finish();
    }
}