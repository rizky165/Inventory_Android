package com.rizkyfachrieza.inventoryapps.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.rizkyfachrieza.inventoryapps.R;
import com.rizkyfachrieza.inventoryapps.dbhelper.SqliteHelper;
import com.rizkyfachrieza.inventoryapps.model.CustomAdapter;
import com.rizkyfachrieza.inventoryapps.model.ModelBarang;

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
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class DataBarangActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView listView;
    TextView emptyState;
    Button btnTambah, btnEdit;
    private SqliteHelper sqliteHelper;
    private Dialog customDialog;
    EditText etHarga, etKodeBarang, etNamaBarang, etStok;
    Spinner KategoriSpinner, SatuanSpinner;
    ProgressDialog nDialog;
    ArrayList<ModelBarang> theList;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_barang);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.titletoolbar);
        textView.setText("Data Barang");
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
                Intent intent=new Intent(DataBarangActivity.this, TambahDataBarangActivity.class);
                startActivity(intent);
                finish();
            }
        });
        theList = new ArrayList<ModelBarang>();
        initData();

    }

    public void initData() {
        Cursor data = sqliteHelper.getAllBarang();
        if (data.getCount()==0){
            emptyState.setVisibility(View.VISIBLE);
            return;
        }else{
            emptyState.setVisibility(View.GONE);
            while(data.moveToNext()){
                theList.add(new ModelBarang(data.getString(0),data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getString(6)));
            }
            CustomAdapter listAdapter = new CustomAdapter(this, theList);
            listView.setAdapter(listAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    long viewId = view.getId();
                    if (viewId == R.id.btnEdit) {
                        initCustomDialog(theList.get(position));
                        customDialog.show();
                    } else if(viewId == R.id.btnDelete) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DataBarangActivity.this);
                        builder.setMessage("Hapus Data " + theList.get(position).getKodeBarang() + "?");
                        builder.setPositiveButton("Ya", (DialogInterface.OnClickListener) (dialog, which) -> {
                            SqliteHelper sqliteHelper = new SqliteHelper(getBaseContext());
                            sqliteHelper.deleteBarang(theList.get(position).getId());
                            theList.remove(position);
                            CustomAdapter listAdapterRmv = new CustomAdapter(DataBarangActivity.this, theList);
                            listView.setAdapter(listAdapterRmv);
                            listAdapter.notifyDataSetChanged();

                            Snackbar.make(listView,"Hapus Data Berhasil", Snackbar.LENGTH_LONG).show();
                            Cursor datas = sqliteHelper.getAllBarang();
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
        Cursor data = sqliteHelper.searchBarang(text);
        if(text.equals("")){
            theList.clear();
            initData();
        }else{
            if (data.getCount() > 0){
                emptyState.setVisibility(View.GONE);
                theList.clear();
                while(data.moveToNext()){
                    theList.add(new ModelBarang(data.getString(0),data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getString(6)));
                }
                CustomAdapter listAdapter = new CustomAdapter(this, theList);
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }else{
                emptyState.setVisibility(View.VISIBLE);
                theList.clear();
                CustomAdapter listAdapter = new CustomAdapter(this, theList);
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }
        }

        return false;
    }

    private void initCustomDialog(ModelBarang barang){
        customDialog = new Dialog(DataBarangActivity.this);
        customDialog.setContentView(R.layout.edit_dialog);
        customDialog.setCancelable(true);
        etKodeBarang = (EditText) customDialog.findViewById(R.id.editTextKodeBarang);
        etNamaBarang = (EditText) customDialog.findViewById(R.id.editTextNamaBarang);
        etHarga = (EditText) customDialog.findViewById(R.id.editTextHarga);
        etStok = (EditText) customDialog.findViewById(R.id.editTextStok);
        KategoriSpinner = customDialog.findViewById(R.id.kategori_spinner);
        SatuanSpinner = customDialog.findViewById(R.id.satuan_spinner);
        btnEdit = customDialog.findViewById(R.id.buttonEdit);

        etKodeBarang.setText(barang.getKodeBarang());
        etNamaBarang.setText(barang.getNamaBarang());
        etHarga.setText(barang.getHarga());
        etStok.setText(barang.getStokBarang());

        sqliteHelper = new SqliteHelper(DataBarangActivity.this);
        List<String> listKategori = sqliteHelper.readKatogori();

        ArrayAdapter<String> kategoriAdapter = new ArrayAdapter<String>(DataBarangActivity.this,
                android.R.layout.simple_spinner_item, listKategori);
        kategoriAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        KategoriSpinner.setAdapter(kategoriAdapter);

        KategoriSpinner.setSelection(kategoriAdapter.getPosition(barang.getKategori()));

        List<String> listSatuan = sqliteHelper.readSatuan();

        ArrayAdapter<String> satuanAdapter = new ArrayAdapter<String>(DataBarangActivity.this,
                android.R.layout.simple_spinner_item, listSatuan);
        satuanAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SatuanSpinner.setAdapter(satuanAdapter);

        SatuanSpinner.setSelection(satuanAdapter.getPosition(barang.getSatuan()));

        etHarga = customDialog.findViewById(R.id.editTextHarga);
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

        btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(etKodeBarang.getText().toString().equals("") || etNamaBarang.getText().toString().equals("") || KategoriSpinner.getSelectedItem().toString().equals("Pilih Kategori") || etHarga.getText().toString().equals("") || etStok.getText().toString().equals("") || SatuanSpinner.getSelectedItem().toString().equals("Pilih Satuan")){
                    Snackbar.make(btnEdit, "Masih ada data yang kosong!", Snackbar.LENGTH_LONG).show();
                }else{
                    nDialog = new ProgressDialog(DataBarangActivity.this);
                    nDialog.setMessage("Loading...");
                    nDialog.setTitle("Edit data barang");
                    nDialog.setIndeterminate(false);
                    nDialog.setCancelable(false);
                    nDialog.setCanceledOnTouchOutside(false);
                    nDialog.show();
                    boolean rowAffect = sqliteHelper.editBarang(new ModelBarang(barang.getId(), etKodeBarang.getText().toString(),etNamaBarang.getText().toString(),KategoriSpinner.getSelectedItem().toString(),etHarga.getText().toString(),etStok.getText().toString(),SatuanSpinner.getSelectedItem().toString()));
                    if(rowAffect){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                theList = new ArrayList<ModelBarang>();
                                Cursor datas = sqliteHelper.getAllBarang();
                                if (datas.getCount()==0){
                                }else{
                                    emptyState.setVisibility(View.INVISIBLE);
                                    while(datas.moveToNext()){
                                        theList.add(new ModelBarang(datas.getString(0),datas.getString(1),datas.getString(2),datas.getString(3),datas.getString(4),datas.getString(5),datas.getString(6)));
                                    }
                                    CustomAdapter listAdapters = new CustomAdapter(DataBarangActivity.this, theList);
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
        onBackPressed();
        return true;
    }
}