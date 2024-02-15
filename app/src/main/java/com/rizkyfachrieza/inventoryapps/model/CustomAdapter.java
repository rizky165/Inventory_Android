package com.rizkyfachrieza.inventoryapps.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.rizkyfachrieza.inventoryapps.R;
import com.rizkyfachrieza.inventoryapps.dbhelper.SqliteHelper;
import com.rizkyfachrieza.inventoryapps.view.DataBarangActivity;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ModelBarang> {
    public CustomAdapter(@NonNull Context context, ArrayList<ModelBarang> arrayList) {
        super(context, 0, arrayList);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
        }

        ModelBarang currentNumberPosition = getItem(position);
        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        TextView textView2 = currentItemView.findViewById(R.id.textView2);
        TextView textView3 = currentItemView.findViewById(R.id.textView3);
        TextView textView4 = currentItemView.findViewById(R.id.textView4);
        textView1.setText(currentNumberPosition.getKodeBarang() + " - " +currentNumberPosition.getNamaBarang());
        textView2.setText("Kategori : "+ currentNumberPosition.getKategori());
        textView3.setText(currentNumberPosition.getHarga());
        textView4.setText(currentNumberPosition.getStokBarang() +" " +currentNumberPosition.getSatuan());

        final ImageView btnEdit = (ImageView) currentItemView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, 0); // Let the event be handled in onItemClick()
            }
        });

        final ImageView btnDelete = (ImageView) currentItemView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, 0); // Let the event be handled in onItemClick()
            }
        });

        return currentItemView;
    }
}
