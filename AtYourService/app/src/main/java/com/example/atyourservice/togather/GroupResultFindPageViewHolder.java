package com.example.atyourservice.togather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atyourservice.R;

public class GroupResultFindPageViewHolder extends RecyclerView.ViewHolder {

    public TextView groupName;
    public TextView groupDate;
    public TextView memberCount;
    public ImageView groupProfilePic;
    public Button joinGroup;
    public TextView getGroupName() {
        return groupName;
    }

    public TextView getGroupDate() {
        return groupDate;
    }

    public TextView getMemberCount() {
        return memberCount;
    }

    public ImageView getGroupProfilePic() {
        return groupProfilePic;
    }

    public Button getJoinGroup() {
        return joinGroup;
    }

    public GroupResultFindPageViewHolder(@NonNull View view) {
        super(view);
        this.groupName = view.findViewById(R.id.groupName);
        this.groupDate = view.findViewById(R.id.groupDate);
        this.memberCount = view.findViewById(R.id.memberCount);
        this.groupProfilePic = view.findViewById(R.id.groupImg);
        this.joinGroup = view.findViewById(R.id.join);
    }
}