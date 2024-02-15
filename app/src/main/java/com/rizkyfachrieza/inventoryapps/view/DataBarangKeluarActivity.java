package com.rizkyfachrieza.inventoryapps.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.rizkyfachrieza.inventoryapps.R;
import com.rizkyfachrieza.inventoryapps.dbhelper.SqliteHelper;
import com.rizkyfachrieza.inventoryapps.model.CustomAdapter;
import com.rizkyfachrieza.inventoryapps.model.CustomAdapterMasuk;
import com.rizkyfachrieza.inventoryapps.model.ModelBarang;
import com.rizkyfachrieza.inventoryapps.model.ModelBarangMasuk;
import com.rizkyfachrieza.inventoryapps.model.ModelSupplier;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataBarangKeluarActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView listView;
    TextView emptyState;
    Button btnTambah, btnEdit;
    private SqliteHelper sqliteHelper;
    private Dialog customDialog;
    EditText etNamaBarang, etStok, etSatuan;
    Button buttonSimpan, btnDate;
    ProgressDialog nDialog;
    ArrayList<ModelBarangMasuk> theList;
    SearchView searchView;
    Spinner BarangSpinner, SupplierSpinner;
    String tglMasuk = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_barang_keluar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.titletoolbar);
        textView.setText("Data Barang Keluar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sqliteHelper = new SqliteHelper(this);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);
        emptyState = (TextView) findViewById(R.id.emptyState);
        listView = (ListView) findViewById(R.id.barangListView);
        listView.setClickable(true);
        btnTambah = (Button) findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DataBarangKeluarActivity.this, TambahDataBarangKeluarActivity.class);
                startActivity(intent);
                finish();
            }
        });
        theList = new ArrayList<ModelBarangMasuk>();
        initData();

    }

    public void initData() {
        Cursor data = sqliteHelper.getAllBarangKeluar();
//        Toast.makeText(getApplicationContext(), DatabaseUtils.dumpCursorToString(data), Toast.LENGTH_LONG).show();
        if (data.getCount()==0){
            emptyState.setVisibility(View.VISIBLE);
            return;
        }else{
            emptyState.setVisibility(View.GONE);
            while(data.moveToNext()){
                theList.add(new ModelBarangMasuk(data.getString(0), data.getString(5), data.getString(6), data.getString(3),data.getString(12),data.getString(9),data.getString(10)));
            }
            CustomAdapterMasuk listAdapter = new CustomAdapterMasuk(this, theList);
            listView.setAdapter(listAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    long viewId = view.getId();
                    if (viewId == R.id.btnEdit) {
                        initCustomDialog(theList.get(position));
                        customDialog.show();
                    } else if(viewId == R.id.btnDelete) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DataBarangKeluarActivity.this);
                        builder.setMessage("Hapus Data " + theList.get(position).getKodeBarang() + "?");
                        builder.setPositiveButton("Ya", (DialogInterface.OnClickListener) (dialog, which) -> {
                            SqliteHelper sqliteHelper = new SqliteHelper(getBaseContext());
                            sqliteHelper.deleteBarangKeluar(theList.get(position).getId());
                            theList.remove(position);
                            CustomAdapterMasuk listAdapterRmv = new CustomAdapterMasuk(DataBarangKeluarActivity.this, theList);
                            listView.setAdapter(listAdapterRmv);
                            listAdapter.notifyDataSetChanged();

                            Snackbar.make(listView,"Hapus Data Berhasil", Snackbar.LENGTH_LONG).show();
                            Cursor datas = sqliteHelper.getAllBarangKeluar();
                            if (datas.getCount()==0){
                                emptyState.setVisibility(View.VISIBLE);
                                return;
                            }else {
                                emptyState.setVisibility(View.GONE);
                                return;
                            }
                        });

                        builder.setNegativeButton("Batal", (DialogInterface.OnClickListener) (dialog, which) -> {
                            dialog.cancel();
                        });

                        // Create the Alert dialog
                        AlertDialog alertDialog = builder.create();
                        // Show the Alert Dialog box
                        alertDialog.show();

                    }
                }
            });
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        SqliteHelper sqliteHelper = new SqliteHelper(getBaseContext());
        Cursor data = sqliteHelper.searchBarangKeluar(text);
        if(text.equals("")){
            theList.clear();
            initData();
        }else{
            if (data.getCount() > 0){
                emptyState.setVisibility(View.GONE);
                theList.clear();
                while(data.moveToNext()){
                    theList.add(new ModelBarangMasuk(data.getString(0), data.getString(5), data.getString(6), data.getString(3),data.getString(12),data.getString(9),data.getString(10)));
                }
                CustomAdapterMasuk listAdapter = new CustomAdapterMasuk(this, theList);
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }else{
                emptyState.setVisibility(View.VISIBLE);
                theList.clear();
                CustomAdapterMasuk listAdapter = new CustomAdapterMasuk(this, theList);
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }
        }

        return false;
    }

    private void initCustomDialog(ModelBarangMasuk barang){
        customDialog = new Dialog(DataBarangKeluarActivity.this);
        customDialog.setContentView(R.layout.edit_masuk_dialog);
        customDialog.setCancelable(true);

        BarangSpinner = customDialog.findViewById(R.id.barang_spinner);
        SupplierSpinner = customDialog.findViewById(R.id.supplier_spinner);
        etNamaBarang = (EditText) customDialog.findViewById(R.id.editTextNamaBarang);
        etStok = (EditText) customDialog.findViewById(R.id.editTextStok);
        etSatuan = (EditText) customDialog.findViewById(R.id.editTextSatuan);
        btnEdit = (Button) customDialog.findViewById(R.id.buttonSimpan);
        btnDate = (Button) customDialog.findViewById(R.id.btnDate);

        etNamaBarang.setText(barang.getNamaBarang());
        etStok.setText(barang.getStokBarang());
        etSatuan.setText(barang.getSatuan());
        tglMasuk = barang.getTglMasuk();
        btnDate.setText(barang.getTglMasuk());
        sqliteHelper = new SqliteHelper(DataBarangKeluarActivity.this);

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

        BarangSpinner.setSelection(barangAdapter.getPosition(barang.getKodeBarang()));

        ArrayList<ModelSupplier> responseSupplier = sqliteHelper.readSupplier();
        List<String> listSupplier = new ArrayList<>();
        listSupplier.add("Pilih Supplier");

        for(int i = 0; i < responseSupplier.size(); i++){
            listSupplier.add(responseSupplier.get(i).getSupplier());
        }
        ArrayAdapter<String> suplierAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listSupplier);
        suplierAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SupplierSpinner.setAdapter(suplierAdapter);

        SupplierSpinner.setSelection(suplierAdapter.getPosition(barang.getSupplier()));


        btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(BarangSpinner.getSelectedItem().toString().equals("Pilih Kode Barang") || SupplierSpinner.getSelectedItem().toString().equals("Pilih Supplier") || tglMasuk.equals("")){
                    Snackbar.make(btnEdit, "Masih ada data yang kosong!", Snackbar.LENGTH_LONG).show();
                }else{
                    nDialog = new ProgressDialog(DataBarangKeluarActivity.this);
                    nDialog.setMessage("Loading...");
                    nDialog.setTitle("Edit data barang keluar");
                    nDialog.setIndeterminate(false);
                    nDialog.setCancelable(false);
                    nDialog.setCanceledOnTouchOutside(false);
                    nDialog.show();
                    boolean rowAffect = sqliteHelper.editBarangKeluar(barang.getId(),responseBarang.get(BarangSpinner.getSelectedItemPosition()-1).getId(), tglMasuk,responseSupplier.get(SupplierSpinner.getSelectedItemPosition()-1).getId());
                    if(rowAffect){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                theList = new ArrayList<ModelBarangMasuk>();
                                Cursor datas = sqliteHelper.getAllBarangKeluar();
                                if (datas.getCount()==0){
                                }else{
                                    emptyState.setVisibility(View.INVISIBLE);
                                    while(datas.moveToNext()){
                                        theList.add(new ModelBarangMasuk(datas.getString(0), datas.getString(5), datas.getString(6), datas.getString(3),datas.getString(12),datas.getString(9),datas.getString(10)));
                                    }
                                    CustomAdapterMasuk listAdapters = new CustomAdapterMasuk(DataBarangKeluarActivity.this, theList);
                                    listView.setAdapter(listAdapters);
                                    listAdapters.notifyDataSetChanged();
                                }
                                nDialog.dismiss();
                                customDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Edit data berhasil!", Toast.LENGTH_LONG).show();
                            }
                        }, 3000);
                    }else{
                        nDialog.dismiss();
                        Snackbar.make(btnEdit, "Gagal Update Data!", Snackbar.LENGTH_LONG).show();

                    }

                }

            };
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

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
        onBackPressed();
        return true;
    }
}