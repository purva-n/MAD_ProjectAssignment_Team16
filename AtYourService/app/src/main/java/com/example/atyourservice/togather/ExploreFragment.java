package com.example.atyourservice.togather;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Groups;
import com.example.atyourservice.api.response.pojo.Messages;
import com.example.atyourservice.models.Group;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {

    private EditText location;
    private Button searchGroup;
    private DatabaseReference dbRef;


    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance() {
        ExploreFragment fragment = new ExploreFragment();
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
        View li =  inflater.inflate(R.layout.fragment_explore, container, false);
        this.dbRef = FirebaseDatabase.getInstance().getReference();

        setSpinnerConfiguration(li);
        findGroups(li);

        location = li.findViewById(R.id.locationSearch);

        Places.initialize(((AppCompatActivity) getActivity()).getApplicationContext(), "AIzaSyCeUHVf4a46VpARdx0mTUiQBEE13BnMNws");

        location.setFocusable(false);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(getActivity());

                startActivityForResult(intent, 100);
            }
        });

        return li;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == AutocompleteActivity.RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            location.setText(place.getAddress());
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR ) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(((AppCompatActivity) getActivity()).getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void findGroups(View view) {
        Button findGroups = (Button) view.findViewById(R.id.findGroups);
        findGroups.setOnClickListener(v -> {
            String category = ((Spinner) view.findViewById(R.id.categories)).getSelectedItem().toString();
            String activity = ((Spinner) view.findViewById(R.id.activities)).getSelectedItem().toString();
            String agePref = ((Spinner) view.findViewById(R.id.age)).getSelectedItem().toString();
            String genderPref = ((Spinner) view.findViewById(R.id.gender)).getSelectedItem().toString();
            String datePref = ((Spinner) view.findViewById(R.id.daterange)).getSelectedItem().toString();
            String location = ((EditText) view.findViewById(R.id.locationSearch)).getText().toString();

            if(!location.isEmpty()) {
            Groups groups = new Groups();

            this.dbRef.child("groups").orderByChild("category").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {

                        long date = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            date = getTimeStamp(datePref);
                        }

                        for (DataSnapshot grpSnap : snapshot.getChildren()) {
                            if (grpSnap.exists()) {
                                Group g = grpSnap.getValue(Group.class);
                                if (g != null) {
                                    g.setId(grpSnap.getKey());
                                    if(g.getActivity().equalsIgnoreCase(activity) && g.getAgeRange().equalsIgnoreCase(agePref)
                                    && g.getGenderPref().equalsIgnoreCase(genderPref) && date <= g.getDate() && location.equalsIgnoreCase(g.getLocation()))
                                    groups.getGroups().add(g);
                                }

                            } else {
                                Toast.makeText(view.getContext(), "There was a glitch", Toast.LENGTH_SHORT).show();
                            }
                        }

                        FragmentTransaction ft = ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        GroupResultMatchedPage rv = GroupResultMatchedPage.newInstance();

                        if(groups.getGroups().size() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("GroupResult", groups);
                            rv.setArguments(bundle);
                            ft.setReorderingAllowed(true)
                                    .addToBackStack("ExploreFragment")
                                    .replace(R.id.fragmentContainer,
                                            rv,
                                            null)
                                    .commit();
                        } else {
                            ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                                    .setReorderingAllowed(true)
                                    .addToBackStack("ExploreFragment")
                                    .replace(R.id.fragmentContainer,
                                            com.example.atyourservice.togather.GroupNotMatchedPage.class, null)
                                    .commit();
                        }

                    } else {
                        ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .addToBackStack("ExploreFragment")
                                .replace(R.id.fragmentContainer,
                                        com.example.atyourservice.togather.GroupNotMatchedPage.class, null)
                                .commit();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
                Toast.makeText(getContext(), "Location needed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private long getTimeStamp(String datePref) {
        Calendar today = Calendar.getInstance();
        switch (datePref) {
            case "Today":
                return today.getTime().getTime();
            case "Tomorrow":
                today.add(Calendar.DAY_OF_YEAR, 1);
                return today.getTime().getTime();
            case "This Week":
                today.add(Calendar.DAY_OF_YEAR, 7);
                return today.getTime().getTime();
            case "Next Week":
                today.add(Calendar.DAY_OF_YEAR, 14);
                return today.getTime().getTime();
            case "This Month":
                today.add(Calendar.DAY_OF_YEAR, 31);
                return today.getTime().getTime();
            case "Next Month":
                today.add(Calendar.DAY_OF_YEAR, 62);
                return today.getTime().getTime();
        }
        return today.getTime().getTime();
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
                    case "Outdoor Activities":
                        activities = getResources().getStringArray(R.array.outdoor_activities);
                        break;
                    case "Indoor Activities":
                        activities = getResources().getStringArray(R.array.indoor_activities);
                        break;
                    case "Seasonal":
                        activities = getResources().getStringArray(R.array.seasonal_activities);
                        break;
                    case "Music":
                        activities = getResources().getStringArray(R.array.music_activities);
                        break;
                    case "Health and Wellness":
                        activities = getResources().getStringArray(R.array.health_activities);
                        break;
                    case "Career and  Business":
                        activities = getResources().getStringArray(R.array.career_activities);
                        break;
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