package com.example.atyourservice.togather;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.atyourservice.R;

import com.example.atyourservice.api.response.pojo.Embedded;
import com.example.atyourservice.api.response.pojo.Groups;
import com.example.atyourservice.models.Group;

import java.util.List;

public class GroupResultFindPageRecyclerView extends AppCompatActivity {

    private Groups groupList;
    RecyclerView groupResultRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_result_find_page);

        groupList = (Groups) getIntent().getSerializableExtra("GroupResultResponse");

        groupResultRecycler = findViewById(R.id.recyclerViewGroupResult);
        groupResultRecycler.setLayoutManager(new LinearLayoutManager(GroupResultFindPageRecyclerView.this));
        groupResultRecycler.setAdapter(new GroupResultFindPageAdapter(groupList.getGroups(), GroupResultFindPageRecyclerView.this));
    }
}