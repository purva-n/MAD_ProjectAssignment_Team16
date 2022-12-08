package com.example.atyourservice.togather;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Groups;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class GroupResultFindPageRecyclerView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference dbRef;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GroupResultFindPageRecyclerView() {
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
    public static GroupResultFindPageRecyclerView newInstance(String param1, String param2) {
        GroupResultFindPageRecyclerView fragment = new GroupResultFindPageRecyclerView();
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
        Groups groupList;
        RecyclerView groupResultRecycler;

        View v =  inflater.inflate(R.layout.activity_group_result_find_page, container, false);

        groupList = (Groups) getActivity().getIntent().getSerializableExtra("GroupResult");

        groupResultRecycler = v.findViewById(R.id.recyclerViewGroupResult);
        groupResultRecycler.setLayoutManager(new LinearLayoutManager(v.getContext()));
        groupResultRecycler.setAdapter(new GroupResultFindPageAdapter(groupList.getGroups(), v.getContext()));

        return v;
    }
}