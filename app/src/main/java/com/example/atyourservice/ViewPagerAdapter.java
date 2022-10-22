package com.example.atyourservice;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.LinkedList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments = new LinkedList<>();
    List<String> tabTitles = new LinkedList<>();
    Bundle bundle;

    public void addFragment(Fragment fragment, String title) {
        this.fragments.add(fragment);
        this.tabTitles.add(title);
    }


    public ViewPagerAdapter(FragmentManager fm, Bundle bundle) {

        super(fm);
        this.bundle = bundle;
    }


    @Override
    public Fragment getItem(int positions) {
        Fragment fragment = fragments.get(positions);
        fragment.setArguments(this.bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.tabTitles.get(position);
    }
}
