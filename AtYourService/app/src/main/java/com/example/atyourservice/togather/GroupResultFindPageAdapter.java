package com.example.atyourservice.togather;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atyourservice.R;
import com.example.atyourservice.models.Group;
import com.example.atyourservice.models.User;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class GroupResultFindPageAdapter extends RecyclerView.Adapter<GroupResultFindPageViewHolder> {
    private List<Group> groups;
    private Context context;

    public GroupResultFindPageAdapter(List<Group> groups, Context context) {
        this.groups = groups;
        this.context = context;
    }

    @Override
    public int getItemViewType(final int position) {
        return  R.layout.activity_group_result_find_page_view_holder;
    }

    @NonNull
    @Override
    public GroupResultFindPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return new GroupResultFindPageViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_group_result_find_page_view_holder,null));
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new GroupResultFindPageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupResultFindPageViewHolder holder, int position) {
        Group grp = groups.get(position);
        //System.out.println("USER ID :::: " + user.getUserId());
        holder.getGroupName().setText(grp.getName());
        holder.getGroupDate().setText(new Date(grp.getDate()).toString());
        holder.getMemberCount().setText(String.valueOf(grp.getMemberCount()));

        //TODO: Group Profile Img
//        int drawableId = 0;
//        try {
//            Class res = R.drawable.class;
//            Field field = null;
//            field = res.getField(grp.getIcon());
//            drawableId = field.getInt(null);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        holder.getGroupProfilePic().setImageResource(drawableId);


        holder.getJoinGroup().setOnClickListener(view -> {
//

            holder.getJoinGroup().setText("Joined");
            holder.getJoinGroup().setEnabled(false);
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}