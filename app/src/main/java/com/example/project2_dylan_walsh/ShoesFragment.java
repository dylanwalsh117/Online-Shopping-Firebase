package com.example.project2_dylan_walsh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project2_dylan_walsh.Model.Model;
import com.example.project2_dylan_walsh.Adapter.ItemAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoesFragment extends Fragment
{
    private ListView listView;
    private ArrayList<Model> Shoes=new ArrayList<>();
    private ItemAdapter itemAdapter;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoesFragment newInstance(String param1, String param2) {
        ShoesFragment fragment = new ShoesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_shoes, container, false);
        listView=view.findViewById(R.id.shoes_listView);
        itemAdapter=new ItemAdapter(getContext(),Shoes);
        listView.setAdapter(itemAdapter);
        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference().child("Item Images");
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.shoes1);
        Model add_item=new Model(bitmap,"Shox","77","Sho12");
        itemAdapter.add(add_item);
        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(), R.drawable.shoes2);
        Model add_item2=new Model(bitmap2,"Yeezy","110","Sho02");
        itemAdapter.add(add_item2);
        Bitmap bitmap3= BitmapFactory.decodeResource(getResources(), R.drawable.shoes3);
        Model add_item3=new Model(bitmap3,"Nike","90","Sho88");
        itemAdapter.add(add_item3);
        Bitmap bitmap4= BitmapFactory.decodeResource(getResources(), R.drawable.shoes4);
        Model add_item4=new Model(bitmap4,"Asics","60","Sho102");
        itemAdapter.add(add_item4);
        Bitmap bitmap5= BitmapFactory.decodeResource(getResources(), R.drawable.shoes5);
        Model add_item5=new Model(bitmap5,"Fancy","240","Sho23");
        itemAdapter.add(add_item5);
        itemAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                CharSequence[] option=new CharSequence[]
                        {
                                "Yes",
                                "No"
                        };
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Do you want to Add to Cart");
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position)
                    {
                        if(position==0)
                        {
                            String online_user=auth.getCurrentUser().getUid();
                            String name=Shoes.get(i).getName();
                            String price=Shoes.get(i).getPrice();
                            String code=Shoes.get(i).getCode();
                            Bitmap image=Shoes.get(i).getImage();
                            DatabaseReference id= databaseReference.child(online_user).push();
                            String key=id.getKey();
                            databaseReference.child("Total Price").child(online_user).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot)
                                {
                                    if(snapshot.exists())
                                    {
                                        String pre=snapshot.child("Total Price").getValue().toString();
                                        int fina_price=Integer.parseInt(pre)+Integer.parseInt(price);
                                        databaseReference.child("Total Price").child(online_user).child("Total Price").setValue(fina_price);
                                    }
                                    else
                                    {
                                        databaseReference.child("Total Price").child(online_user).child("Total Price").setValue(price);
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            databaseReference.child("Cart").child(online_user).child(key).child("Name").setValue(name);
                            databaseReference.child("Cart").child(online_user).child(key).child("Price").setValue(price);
                            databaseReference.child("Cart").child(online_user).child(key).child("Code").setValue(code);
                            databaseReference.child("Cart").child(online_user).child(key).child("Key").setValue(key);
                            StorageReference filePath=  storageReference.child(online_user).child(key);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            image.recycle();
                            filePath.putBytes(byteArray) .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        String downloadURL=filePath.getDownloadUrl().toString();
                                        databaseReference.child("Cart").child(online_user).child(key).child("Image").setValue(downloadURL);
                                        Toast.makeText(getContext(), "Successfully Added", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });




                        }
                        if(position==1)
                        {

                        }


                    }
                }).show();



            }
        });


        return  view;
    }
}