package com.example.project2_dylan_walsh;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    EditText name,address,email,password;
    Button register_btn;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText)findViewById(R.id.fullName);
        address=(EditText)findViewById(R.id.age);
        email=(EditText)findViewById(R.id.getEmail);
        password=(EditText)findViewById(R.id.getPassword);
        register_btn=(Button)findViewById(R.id.registerUser);
        firebaseAuth=FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(TextUtils.isEmpty(name.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(address.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Please Enter address", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    CreateAccount(email.getText().toString(),password.getText().toString());
                }
            }
        });


    }

    private void CreateAccount(String email, String password)
    {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        String UID=firebaseAuth.getCurrentUser().getUid();
                        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(UID);
                        databaseReference.child("Name").setValue(name.getText().toString());
                        databaseReference.child("Address").setValue(address.getText().toString());
                        databaseReference.child("Email").setValue(email);
                        databaseReference.child("Password").setValue(password);
                        Toast.makeText(RegisterActivity.this, "signed up successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterActivity.this,loginActivity.class);
                        startActivity(intent);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(RegisterActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}