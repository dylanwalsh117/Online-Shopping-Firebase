package com.example.project2_dylan_walsh;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_dylan_walsh.Adapter.BasketAdapter;
import com.example.project2_dylan_walsh.Model.BasketModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class BasketActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private BasketAdapter basketAdapter;
    private ArrayList<BasketModel> car=new ArrayList<>();
    private List<BasketModel> cartlist=new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public TextView  textView;
    private Button empty_btn,cont_btn,payment_btn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        init();
        FetchItems();
        cont_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(BasketActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BasketActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });
        empty_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String online_user=auth.getCurrentUser().getUid();
                Intent intent=new Intent(BasketActivity.this,MainActivity.class);
                startActivity(intent);
                databaseReference.child("Cart").child(online_user).removeValue();
                databaseReference.child("Total Price").child(online_user).removeValue();
                storageReference.child(online_user).delete();
                Toast.makeText(BasketActivity.this, "empty basket", Toast.LENGTH_SHORT).show();


            }
        });





    }



    private void FetchItems()
    {
        String online_user=auth.getCurrentUser().getUid();
        databaseReference.child("Cart").child(online_user).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
                BasketModel basketModel=snapshot.getValue(BasketModel.class);
                cartlist.add(basketModel);
                basketAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        showTotalPrice();

    }





    private void init()
    {
        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        recyclerView=findViewById(R.id.cart_list);
        basketAdapter=new BasketAdapter(cartlist);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        storageReference=FirebaseStorage.getInstance().getReference().child("Item Images");
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(basketAdapter);
        textView=(TextView)findViewById(R.id.totalPrice);
        empty_btn=(Button)findViewById(R.id.empty_btn);
        cont_btn=(Button)findViewById(R.id.cont_btn);
        payment_btn=(Button)findViewById(R.id.payment_btn);


    }

    private void showTotalPrice()
    {

        databaseReference.child("Total Price").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        if(snapshot.exists())
                        {
                            String total=snapshot.child("Total Price").getValue().toString();
                            textView.setText("Total Price: "+total);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}