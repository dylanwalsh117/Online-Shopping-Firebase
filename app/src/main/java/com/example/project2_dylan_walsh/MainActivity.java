package com.example.project2_dylan_walsh;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.project2_dylan_walsh.Adapter.TabsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private ViewPager myviewpager;
    private TabLayout mytablayout;
    private TabsPagerAdapter myTabsPageAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myviewpager = (ViewPager) findViewById(R.id.main_tabs_pager);
        myTabsPageAdaptor = new TabsPagerAdapter(getSupportFragmentManager());
        myviewpager.setAdapter(myTabsPageAdaptor);
        mytablayout = (TabLayout) findViewById(R.id.main_tabs);
        mytablayout.setupWithViewPager(myviewpager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() ==R.id.Signing_in)
        {
            Intent intent=new Intent(MainActivity.this,loginActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.Open_basket)
        {
            Intent intent=new Intent(MainActivity.this,BasketActivity.class);
            startActivity(intent);

        }
        if(item.getItemId()==R.id.Go_to_home_page)
        {

        }


        return  true;
    }
}