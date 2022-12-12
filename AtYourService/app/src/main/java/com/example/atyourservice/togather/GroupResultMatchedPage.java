package com.example.atyourservice.togather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Groups;
import com.google.firebase.database.DatabaseReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupResultMatchedPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupResultMatchedPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private DatabaseReference dbRef;
    private Groups groupList;
    private Button createGroup;

    public GroupResultMatchedPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupResultMatchedPage newInstance() {
        GroupResultMatchedPage fragment = new GroupResultMatchedPage();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            groupList = (Groups) bundle.getSerializable("GroupResult");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView groupResultRecycler;
        View v =  inflater.inflate(R.layout.fragment_group_result_matched_page, container, false);
        System.out.println("HERE :  ");
        groupResultRecycler = v.findViewById(R.id.recyclerViewGroupResultMatched);
        groupResultRecycler.setLayoutManager(new LinearLayoutManager(v.getContext()));
        groupResultRecycler.setAdapter(new GroupResultsMatchedPageAdapter(groupList.getGroups(), v.getContext()));
        createGroup = v.findViewById(R.id.createGroup);

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, com.example.atyourservice.togather.CreateGroupFragment.class, null)
                        .commit();
            }
        });
        return v;
    }
}