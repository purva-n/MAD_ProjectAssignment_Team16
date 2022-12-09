package com.example.atyourservice.togather;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.atyourservice.R;
import com.example.atyourservice.UserAdapter;
import com.example.atyourservice.UserList;
import com.example.atyourservice.models.HomePageActivities;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference database;
    ActivitiesListAdapter categoryListAdapter;
    ArrayList<HomePageActivities> list;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        TextView searchBar = view.findViewById(R.id.searchBar);

        recyclerView = view.findViewById(R.id.activitiesRecyclerView);
        database = FirebaseDatabase.getInstance().getReference();

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
        a.setActivityName("Art & Culture");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.outdoor_icon);
        a.setActivityName("Outdoor Activity");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.indoor_icon);
        a.setActivityName("Indoor Activity");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.seasonal_icon);
        a.setActivityName("Seasonal Activity");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.music);
        a.setActivityName("Music");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.health_icon);
        a.setActivityName("Health & Wellness");
        list.add(a);

        a = new HomePageActivities();
        a.setActivityImage(R.drawable.career_icon);
        a.setActivityName("Career & Business");
        list.add(a);

        System.out.println("LIST SIZE ::: " + list.size());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ActivitiesListAdapter(getActivity(), list));

        return view;
    }
}