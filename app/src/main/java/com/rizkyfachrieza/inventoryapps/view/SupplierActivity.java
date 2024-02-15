package com.rizkyfachrieza.inventoryapps.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.rizkyfachrieza.inventoryapps.R;
import com.rizkyfachrieza.inventoryapps.dbhelper.SqliteHelper;
import com.rizkyfachrieza.inventoryapps.model.CustomAdapter;
import com.rizkyfachrieza.inventoryapps.model.CustomAdapterSupplier;
import com.rizkyfachrieza.inventoryapps.model.ModelBarang;
import com.rizkyfachrieza.inventoryapps.model.ModelSupplier;
import com.rizkyfachrieza.inventoryapps.model.ModelSupplier;

import java.util.ArrayList;
import java.util.List;

public class SupplierActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ListView listView;
    TextView emptyState;
    Button btnTambah, btnEdit;
    private SqliteHelper sqliteHelper;
    private Dialog customDialog;
    EditText etSupplier;
    ProgressDialog nDialog;
    ArrayList<ModelSupplier> theList;
    SearchView searchView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.titletoolbar);
        textView.setText("Supplier");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sqliteHelper = new SqliteHelper(this);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);
        emptyState = (TextView) findViewById(R.id.emptyState);
        listView = (ListView) findViewById(R.id.supplierListView);
        listView.setClickable(true);
        btnTambah = (Button) findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SupplierActivity.this, TambahSupplierActivity.class);
                startActivity(intent);
                finish();
            }
        });
        theList = new ArrayList<ModelSupplier>();
        initData();
    }

    public void initData() {
        Cursor data = sqliteHelper.getAllSupllier();
//        Toast.makeText(getApplicationContext(), DatabaseUtils.dumpCursorToString(data), Toast.LENGTH_SHORT).show();
        if (data.getCount()==0){
            emptyState.setVisibility(View.VISIBLE);
            return;
        }else{
            emptyState.setVisibility(View.GONE);
            while(data.moveToNext()){
                theList.add(new ModelSupplier(data.getString(0), data.getString(1)));
            }
            CustomAdapterSupplier listAdapter = new CustomAdapterSupplier(this, theList);
            listView.setAdapter(listAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    long viewId = view.getId();
                    if (viewId == R.id.btnEdit) {
                        initCustomDialog(theList.get(position));
                        customDialog.show();
                    } else if(viewId == R.id.btnDelete) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SupplierActivity.this);
                        builder.setMessage("Hapus Data " + theList.get(position).getSupplier() + "?");
                        builder.setPositiveButton("Ya", (DialogInterface.OnClickListener) (dialog, which) -> {
                            SqliteHelper sqliteHelper = new SqliteHelper(getBaseContext());
                            sqliteHelper.deleteSupplier(theList.get(position).getId());
                            theList.remove(position);
                            CustomAdapterSupplier listAdapterRmv = new CustomAdapterSupplier(SupplierActivity.this, theList);
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
        Cursor data = sqliteHelper.searchSupplier(text);
        if(text.equals("")){
            theList.clear();
            initData();
        }else{
            if (data.getCount() > 0){
                emptyState.setVisibility(View.GONE);
                theList.clear();
                while(data.moveToNext()){
                    theList.add(new ModelSupplier(data.getString(0),data.getString(1)));
                }
                CustomAdapterSupplier listAdapter = new CustomAdapterSupplier(this, theList);
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }else{
                emptyState.setVisibility(View.VISIBLE);
                theList.clear();
                CustomAdapterSupplier listAdapter = new CustomAdapterSupplier(this, theList);
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }
        }

        return false;
    }

    private void initCustomDialog(ModelSupplier supplier){
        customDialog = new Dialog(SupplierActivity.this);
        customDialog.setContentView(R.layout.edit_dialog_supplier);
        customDialog.setCancelable(true);
        etSupplier = (EditText) customDialog.findViewById(R.id.editTextSupplier);
        btnEdit = customDialog.findViewById(R.id.buttonEdit);
        etSupplier.setText(supplier.getSupplier());

        sqliteHelper = new SqliteHelper(SupplierActivity.this);

        btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(etSupplier.getText().toString().equals("")){
                    Snackbar.make(btnEdit, "Masih ada data yang kosong!", Snackbar.LENGTH_LONG).show();
                }else{
                    nDialog = new ProgressDialog(SupplierActivity.this);
                    nDialog.setMessage("Loading...");
                    nDialog.setTitle("Edit data Supplier");
                    nDialog.setIndeterminate(false);
                    nDialog.setCancelable(false);
                    nDialog.setCanceledOnTouchOutside(false);
                    nDialog.show();
                    boolean rowAffect = sqliteHelper.editSupplier(new ModelSupplier(supplier.getId(), etSupplier.getText().toString()));
                    if(rowAffect){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                theList = new ArrayList<ModelSupplier>();
                                Cursor datas = sqliteHelper.getAllSupllier();
                                if (datas.getCount()==0){
                                }else{
                                    emptyState.setVisibility(View.INVISIBLE);
                                    while(datas.moveToNext()){
                                        theList.add(new ModelSupplier(datas.getString(0),datas.getString(1)));
                                    }
                                    CustomAdapterSupplier listAdapters = new CustomAdapterSupplier(SupplierActivity.this, theList);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}