package com.example.atyourservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    static Toolbar toolbar;
    static TabLayout tabLayout;
    static ViewPager2 viewPager;
    static ViewPagerAdapter viewPagerAdapter;
    static RecyclerView.Adapter adapter;
    static FragmentManager fragmentManager;
    static Bundle bundle = new Bundle();

    String city, state;
    int zip;

    EditText cityInput, stateInput, zipInput;
    Button searchBut,clearBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Event Search");
        getSupportActionBar().hide();

        viewPager = findViewById(R.id.viewPager2);
        fragmentManager = getSupportFragmentManager();

        bundle.clear();
        viewPagerAdapter = new ViewPagerAdapter(MainActivity.fragmentManager, bundle);
        viewPagerAdapter.addFragment(new SearchTabFragment(), "SEARCH");
        viewPagerAdapter.addFragment(new FavoriteTabFragment(), "FAVORITE");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPagerAdapter.notifyDataSetChanged();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        cityInput = (EditText) findViewById(R.id.cityInput);
        stateInput = (EditText) findViewById(R.id.stateInput);
        zipInput = (EditText) findViewById(R.id.zipCodeInput);

        searchBut = (Button) findViewById(R.id.searchButton);
        clearBut = (Button)findViewById(R.id.clearButton);
        searchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = cityInput.getText().toString();
                state = stateInput.getText().toString();
                zip = Integer.parseInt(zipInput.getText().toString());
            }
        });

        clearBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllFields(view);
            }
        });

    }
    public void clearAllFields(View view){
        cityInput.setText("");
        stateInput.setText("");
        zipInput.setText("");
    }
}