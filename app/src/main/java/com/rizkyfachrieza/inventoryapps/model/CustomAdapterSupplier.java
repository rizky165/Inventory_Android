package com.rizkyfachrieza.inventoryapps.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rizkyfachrieza.inventoryapps.R;

import java.util.ArrayList;

public class CustomAdapterSupplier extends ArrayAdapter<ModelSupplier> {
    public CustomAdapterSupplier(@NonNull Context context, ArrayList<ModelSupplier> arrayList) {
        super(context, 0, arrayList);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview_supplier, parent, false);
        }

        ModelSupplier currentNumberPosition = getItem(position);
        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(currentNumberPosition.getId()+" - "+currentNumberPosition.getSupplier());


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
