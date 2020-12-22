package com.example.project2_dylan_walsh.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.example.project2_dylan_walsh.Model.BasketModel;

import com.example.project2_dylan_walsh.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ItemViewHoler> {
    private List<BasketModel> cartlist;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    public static int totalPrice=0;
    public BasketAdapter(List<BasketModel> cartlist)
    {
        this.cartlist = cartlist;
    }

    @NonNull
    @Override
    public ItemViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        mAuth = FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference().child("Item Images");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Cart");


        return new ItemViewHoler(V);

    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHoler holder, final int position)
    {
        String online_user=mAuth.getCurrentUser().getUid();
        BasketModel basketModel=cartlist.get(position);
        String name=basketModel.getName();
        String price=basketModel.getPrice();
        String code=basketModel.getCode();
        holder.code.setText(code);
        holder.price.setText(price);
        holder.name.setText(name);
        String key=basketModel.getKey();

        storageReference.child(online_user).child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri)
            {
                Picasso.get().load(uri.toString()).placeholder(R.drawable.ic_baseline_image_24).into(holder.imageView);

            }
        });
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {





            }
        });










    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }


    public class ItemViewHoler extends RecyclerView.ViewHolder
    {
        public TextView name,price,code;
        public ImageView imageView;
        View mview;

        public ItemViewHoler(@NonNull View view)
        {
            super(view);
            mview=view;

            price=(TextView) view.findViewById(R.id.item_price);
            name=(TextView)view.findViewById(R.id.item_name);
            code=(TextView)view.findViewById(R.id.item_code);
            imageView=(ImageView)view.findViewById(R.id.item_image);


        }
    }
}
