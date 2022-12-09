package com.example.atyourservice.togather;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Groups;
import com.example.atyourservice.api.response.pojo.Messages;
import com.example.atyourservice.models.Group;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference dbRef;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
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
        View li =  inflater.inflate(R.layout.fragment_explore, container, false);
        this.dbRef = FirebaseDatabase.getInstance().getReference();

        setSpinnerConfiguration(li);
        findGroups(li);

        return li;
    }

    private void findGroups(View view) {
        Button findGroups = (Button) view.findViewById(R.id.findGroups);
        findGroups.setOnClickListener(v -> {
            String category = ((Spinner) view.findViewById(R.id.categories)).getSelectedItem().toString();
            String activity = ((Spinner) view.findViewById(R.id.activities)).getSelectedItem().toString();
            String agePref = ((Spinner) view.findViewById(R.id.age)).getSelectedItem().toString();
            String genderPref = ((Spinner) view.findViewById(R.id.gender)).getSelectedItem().toString();
            String datePref = ((Spinner) view.findViewById(R.id.daterange)).getSelectedItem().toString();

            Groups groups = new Groups();

            this.dbRef.child("groups").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        for (DataSnapshot grpSnap : snapshot.getChildren()) {
                            if (grpSnap.exists()) {
                                Group g = grpSnap.getValue(Group.class);
                                if (g != null) {
                                    groups.getGroups().add(g);
                                }

                            } else {
                                Toast.makeText(view.getContext(), "There was a glitch", Toast.LENGTH_SHORT).show();
                            }
                        }

                        FragmentTransaction ft = ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        GroupResultFindPageRecyclerView rv = GroupResultFindPageRecyclerView.newInstance();

                        if(groups.getGroups().size() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("GroupResult", groups);
                            rv.setArguments(bundle);
                            ft.setReorderingAllowed(true)
                                    .replace(R.id.fragmentContainer,
                                            rv,
                                            null)
                                    .commit();
                        }

                    } else {
                        ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer,
                                        com.example.atyourservice.togather.GroupResultNotFoundPage.class, null)
                                .commit();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    private void setSpinnerConfiguration(View li) {
        String[] categories = getResources().getStringArray(R.array.categories);

        Spinner category = (Spinner) li.findViewById(R.id.categories);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCategory = categories[position];
                String[] activities = null;

                switch (selectedCategory) {
                    case "Hiking":
                        activities = getResources().getStringArray(R.array.hiking_activities);
                        break;
                    case "Sports":
                        activities = getResources().getStringArray(R.array.sport_activities);
                        break;
                    case "Art and Culture":
                        activities = getResources().getStringArray(R.array.art_activities);
                        break;
                    //TODO: continue rest categories
                }

                Spinner activity = (Spinner) li.findViewById(R.id.activities);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(li.getContext(), android.R.layout.simple_spinner_item, activities);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                activity.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }
}