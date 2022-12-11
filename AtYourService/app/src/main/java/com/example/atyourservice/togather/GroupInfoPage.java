package com.example.atyourservice.togather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Groups;
import com.example.atyourservice.models.Group;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupInfoPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupInfoPage extends Fragment {
    TextView groupName;
    TextView category;
    TextView ageGroup;
    TextView description;
    TextView genderPreference;
    TextView activityDate;
    Group group;


    public GroupInfoPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment GroupInfoPage.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupInfoPage newInstance() {
        GroupInfoPage fragment = new GroupInfoPage();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            group = (Group) bundle.getSerializable("GroupSelected");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_info_page, container, false);
        groupName = view.findViewById(R.id.groupName);
        category = view.findViewById(R.id.category);
        ageGroup = view.findViewById(R.id.ageGroup);
        description = view.findViewById(R.id.description);
        activityDate = view.findViewById(R.id.activityDate);
        genderPreference = view.findViewById(R.id.genderPreference);

        description.setMovementMethod(new ScrollingMovementMethod());

        groupName.setText(group.getName().toUpperCase());
        category.setText(group.getCategory());
        ageGroup.setText(group.getAgeRange());
        description.setText(group.getDescription());
        activityDate.setText(new Date(group.getEventdate()).toString());
        genderPreference.setText(group.getGenderPref());

        return view;

    }

}