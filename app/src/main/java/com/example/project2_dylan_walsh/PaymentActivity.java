package com.example.project2_dylan_walsh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends AppCompatActivity {
    EditText amount,address;
    TextView textView;
    Button button;
    DatabaseReference databaseReference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String amou=amount.getText().toString();
                String add=address.getText().toString();
                if(TextUtils.isEmpty(amou))
                {
                    Toast.makeText(PaymentActivity.this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(add))
                {
                    Toast.makeText(PaymentActivity.this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    textView.setVisibility(View.VISIBLE);
                    amount.setVisibility(View.GONE);
                    address.setVisibility(View.GONE);
                    String online=auth.getCurrentUser().getUid();
                    databaseReference.child("Cart").child(online).removeValue();
                    databaseReference.child("Total Price").child(online).removeValue();
                }

            }
        });
    }

    private void init()
    {
        amount=(EditText)findViewById(R.id.amount);
        address=(EditText)findViewById(R.id.address);
        textView=(TextView)findViewById(R.id.text_confirmed);
        button=(Button)findViewById(R.id.submit_btn);
        textView.setVisibility(View.GONE);
        amount.setVisibility(View.VISIBLE);
        address.setVisibility(View.VISIBLE);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
    }
}