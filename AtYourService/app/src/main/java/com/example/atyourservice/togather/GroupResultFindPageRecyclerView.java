package com.example.atyourservice.togather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atyourservice.R;
import com.example.atyourservice.UserList;
import com.example.atyourservice.api.response.pojo.Groups;
import com.example.atyourservice.mesaging.service.MessagesRecycler;
import com.example.atyourservice.models.Group;
import com.example.atyourservice.models.User;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class GroupResultFindPageRecyclerView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private DatabaseReference dbRef;
    private Groups groupList;
    public GroupResultFindPageRecyclerView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupResultFindPageRecyclerView newInstance() {
        GroupResultFindPageRecyclerView fragment = new GroupResultFindPageRecyclerView();
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

        View v =  inflater.inflate(R.layout.activity_group_result_find_page, container, false);

        groupResultRecycler = v.findViewById(R.id.recyclerViewGroupResult);
        groupResultRecycler.setLayoutManager(new LinearLayoutManager(v.getContext()));
        groupResultRecycler.setAdapter(new GroupResultFindPageAdapter(groupList.getGroups(), v.getContext()));

        return v;
    }
    
}