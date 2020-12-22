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

import com.example.project2_dylan_walsh.Adapter.ItemAdapter;
import com.example.project2_dylan_walsh.Model.Model;
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
 * Use the {@link PantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PantFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    private ArrayList<Model> item=new ArrayList<>();
    ItemAdapter itemAdapter;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;


    public PantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PantFragment newInstance(String param1, String param2) {
        PantFragment fragment = new PantFragment();
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
                             Bundle savedInstanceState)
    {

        View view= inflater.inflate(R.layout.fragment_pant, container, false);
        listView=view.findViewById(R.id.pant_listview);
        itemAdapter=new ItemAdapter(getContext(),item);
        listView.setAdapter(itemAdapter);
        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference().child("Item Images");
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.pants1);
        Model add_item=new Model(bitmap,"Levi Pants","250","Pan25");
        itemAdapter.add(add_item);
        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(), R.drawable.pants2);
        Model add_item2=new Model(bitmap2,"Burberry Pants","350","Pan100");
        itemAdapter.add(add_item2);
        Bitmap bitmap3= BitmapFactory.decodeResource(getResources(), R.drawable.pants3);
        Model add_item3=new Model(bitmap3,"Penneys Pants","20","Pan22");
        itemAdapter.add(add_item3);
        Bitmap bitmap4= BitmapFactory.decodeResource(getResources(), R.drawable.pants4);
        Model add_item4=new Model(bitmap4,"River Island Pants","40","Pan89");
        itemAdapter.add(add_item4);
        Bitmap bitmap5= BitmapFactory.decodeResource(getResources(), R.drawable.pants5);
        Model add_item5=new Model(bitmap5,"New Look Pants","45","Pan38");
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
                            String name=item.get(i).getName();
                            String price=item.get(i).getPrice();
                            String code=item.get(i).getCode();
                            Bitmap image=item.get(i).getImage();
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
                            image.compress(Bitmap.CompressFormat.PNG, 50, stream);
                            byte[] byteArray = stream.toByteArray();

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