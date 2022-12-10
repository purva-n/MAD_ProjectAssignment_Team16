package com.example.atyourservice.togather;

import android.annotation.SuppressLint;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Groups;
import com.example.atyourservice.models.Group;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    public CreateGroupFragment() {
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
    public static CreateGroupFragment newInstance(String param1, String param2) {
        CreateGroupFragment fragment = new CreateGroupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        return li;
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

                if(!grpName.isEmpty()) {
                    //create group
                    Group group = new Group();
                    group.setName(grpName);
                    group.setCategory(category);
                    group.setDescription("Group for people into" + category + ", "+ activity);
                    group.setActivity(activity);
                    group.setAgeRange(agePref);
                    group.setGenderPref(genderPref);
                    group.setDate(date);
                    group.setKeywords(keywords);

                    //Add to database
                    dbRef.push().setValue(group).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(view.getContext(), "Group Created",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(view.getContext(), "Group Name mandatory!", Toast.LENGTH_LONG).show();
                }

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
                    //TODO: continue rest categories
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
