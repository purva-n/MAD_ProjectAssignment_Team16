package com.example.atyourservice.togather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Groups;
import com.example.atyourservice.models.Group;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateGroupFragment extends Fragment {
    DatabaseReference dbRef;
    EditText keyword;
    TextView keywordText;
    private EditText location;
    public CreateGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateGroupFragment newInstance() {
        CreateGroupFragment fragment = new CreateGroupFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbRef = FirebaseDatabase.getInstance().getReference().child("groups");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View li =  inflater.inflate(R.layout.activity_create_group, container, false);
        keyword = (EditText) li.findViewById(R.id.keyword);
        keywordText = (TextView) li.findViewById(R.id.keywordText);
        addKeywordConfiguration(li);
        setSpinnerConfiguration(li);
        addGroup(li);

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


    private void addKeywordConfiguration(View view) {
        view.findViewById(R.id.addKeyword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywordText.setText(keywordText.getText()
                        .toString().concat(",")
                        .concat(String.valueOf(keyword.getText())));
                keyword.setText("");
            }
        });
    }

    private void addGroup(View view) {
        Button addGroup = (Button) view.findViewById(R.id.createGroup);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String grpName = ((EditText) view.findViewById(R.id.grpName)).getText().toString();
                String category = ((Spinner) view.findViewById(R.id.category)).getSelectedItem().toString();
                String activity = ((Spinner) view.findViewById(R.id.activity)).getSelectedItem().toString();
                String agePref = ((Spinner) view.findViewById(R.id.ageC)).getSelectedItem().toString();
                String genderPref = ((Spinner) view.findViewById(R.id.genderC)).getSelectedItem().toString();
                String datePref = ((Spinner) view.findViewById(R.id.daterangeC)).getSelectedItem().toString();
                String location = ((EditText) view.findViewById(R.id.locationSearch)).getText().toString();

                List<String> keywords = null;
                if(!keywordText.getText().toString().isEmpty()) {
                    keywords = Arrays.asList(((TextView)
                            view.findViewById((R.id.keywordText)))
                            .getText().toString().split(","));
                }
                long date = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    date = getTimeStamp(datePref);
                }

                if(!grpName.isEmpty() && !location.isEmpty()) {
                    //create group
                    Group group = createGroup(grpName, category, activity, agePref, genderPref, date, location, keywords);

                    //Add to database
                    dbRef.push().setValue(group).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(view.getContext(), "Group Created",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    if(grpName.isEmpty()) {
                        Toast.makeText(view.getContext(), "Group Name mandatory!", Toast.LENGTH_LONG).show();
                    }

                    if(location.isEmpty()){
                        Toast.makeText(view.getContext(), "Location mandatory!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private Group createGroup(String grpName, String category, String activity, String agePref,
                              String genderPref, long date, String location, List<String> keywords) {
        Group group = new Group();
        group.setName(grpName);
        group.setCategory(category);
        group.setDescription("Group for people into" + category + ", "+ activity);
        group.setActivity(activity);
        group.setAgeRange(agePref);
        group.setGenderPref(genderPref);
        group.setDate(date);
        group.setKeywords(keywords);
        group.setLocation(location);

        return group;
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

        Spinner category = (Spinner) li.findViewById(R.id.category);
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

                Spinner activity = (Spinner) li.findViewById(R.id.activity);
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
