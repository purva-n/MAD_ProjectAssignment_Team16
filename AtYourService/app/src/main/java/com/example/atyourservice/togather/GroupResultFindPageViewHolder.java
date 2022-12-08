package com.example.atyourservice.togather;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atyourservice.R;

public class GroupResultFindPageViewHolder extends RecyclerView.ViewHolder {

    public TextView groupName;
    public TextView groupDate;
    public TextView groupTime;
    public TextView memberCount;
    public ImageView groupProfilePic;

    public GroupResultFindPageViewHolder(@NonNull View view) {
        super(view);
        this.groupName = view.findViewById(R.id.groupName);
        this.groupDate = view.findViewById(R.id.groupDate);
        this.groupTime = view.findViewById(R.id.groupTime);
        this.memberCount = view.findViewById(R.id.memberCount);
        this.groupProfilePic = view.findViewById(R.id.groupImg);
    }
}