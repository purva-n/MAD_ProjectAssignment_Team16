package com.example.atyourservice.togather;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;

public class GroupResultMatchedPageViewHolder extends RecyclerView.ViewHolder {

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

    public GroupResultMatchedPageViewHolder(@NonNull View view) {
        super(view);
        this.groupName = view.findViewById(R.id.groupName);
        this.groupDate = view.findViewById(R.id.groupDate);
        this.memberCount = view.findViewById(R.id.memberCount);
        this.groupProfilePic = view.findViewById(R.id.groupImg);
        this.joinGroup = view.findViewById(R.id.join);
    }
}
