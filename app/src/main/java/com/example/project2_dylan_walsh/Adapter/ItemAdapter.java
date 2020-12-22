package com.example.project2_dylan_walsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project2_dylan_walsh.Model.Model;
import com.example.project2_dylan_walsh.R;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Model> {
    public ItemAdapter(@NonNull Context context, ArrayList<Model> item) {
        super(context, 0,item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Model model=getItem(position);
        if(view==null)
        {
            view= LayoutInflater.from(getContext()).inflate(R.layout.item_layout,parent,false);

        }
        ImageView imageView=view.findViewById(R.id.item_image);
        imageView.setImageBitmap(model.getImage());
        TextView name=view.findViewById(R.id.item_name);
        name.setText(model.getName());
        TextView price=view.findViewById(R.id.item_price);
        price.setText(model.getPrice());
        TextView code=view.findViewById(R.id.item_code);
        code.setText(model.getCode());

        return  view;

    }
}