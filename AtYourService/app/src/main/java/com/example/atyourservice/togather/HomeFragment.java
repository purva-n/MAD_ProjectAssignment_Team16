package com.example.atyourservice.togather;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atyourservice.R;
import com.example.atyourservice.UserAdapter;
import com.example.atyourservice.UserList;
import com.example.atyourservice.models.HomePageActivities;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference database;
    ActivitiesListAdapter categoryListAdapter;
    List<HomePageActivities> list;
    SearchView searchBar;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        searchBar = view.findViewById(R.id.searchBar);

        searchBar.clearFocus();

        recyclerView = view.findViewById(R.id.activitiesRecyclerView);
        database = FirebaseDatabase.getInstance().getReference();

        populateCategoryList();

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        categoryListAdapter = new ActivitiesListAdapter(getActivity(), list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(categoryListAdapter);

        return view;
    }


    private void filterList(String filterStr) {
        List<HomePageActivities> filteredList = new ArrayList<>();

        for(HomePageActivities category: list) {
            if(category.getActivityName().toLowerCase().contains(filterStr.toLowerCase())) {
                filteredList.add(category);
            }
        }

        if(filteredList.isEmpty()) {
            Toast.makeText(getActivity(), "No category found!", Toast.LENGTH_SHORT).show();
        } else {
            categoryListAdapter.setFilteredList(filteredList);
        }
    }

    private void populateCategoryList() {
        list = new ArrayList<>();

        HomePageActivities a = new HomePageActivities();
        a.setActivityImage(R.drawable.hiking_icon);
        a.setActivityName("Hiking");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.sports_icon);
        a.setActivityName("Sports");
        list.add(a);


        a = new HomePageActivities();
        a.setActivityImage(R.drawable.art_icon);
        a.setActivityName("Art and Culture");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.outdoor_icon);
        a.setActivityName("Outdoor Activities");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.indoor_icon);
        a.setActivityName("Indoor Activities");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.seasonal_icon);
        a.setActivityName("Seasonal");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.music);
        a.setActivityName("Music");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.health_icon);
        a.setActivityName("Health and Wellness");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.career_icon);
        a.setActivityName("Career and Business");
        list.add(a);
    }

}