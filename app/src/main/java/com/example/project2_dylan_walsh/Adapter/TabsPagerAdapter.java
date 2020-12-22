package com.example.project2_dylan_walsh.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.project2_dylan_walsh.JacketFragment;
import com.example.project2_dylan_walsh.PantFragment;
import com.example.project2_dylan_walsh.ShoesFragment;

public  class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                JacketFragment jacketFragment=new JacketFragment();

                return jacketFragment;
            case 1:
                PantFragment pantFragment=new PantFragment();
                return pantFragment;
            case 2:
                ShoesFragment shoesFragment=new ShoesFragment();
                return shoesFragment;
            default:
                return null;

        }


    }

    @Override
    public int getCount() {
        return 3;
    }
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Jacket";
            case 1:
                return "Pants";
            case 2:
                return "Shoes";
            default:
                return null;

        }
    }
}
