package com.example.atyourservice.ToGather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.atyourservice.R;
import com.example.atyourservice.mesaging.service.MessagesActivity;
import com.example.atyourservice.mesaging.service.SentMessagesHolder;
import com.example.atyourservice.models.Group;
import com.example.atyourservice.models.User;

import java.util.List;

public class GroupResultFindPageAdapter extends RecyclerView.Adapter<GroupResultFindPageViewHolder> {
    private List<Group> groups;
    private Context context;

    public GroupResultFindPageAdapter(List<Group> groups, Context context) {
        this.groups = groups;
        this.context = context;
    }

    @NonNull
    @Override
    public GroupResultFindPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupResultFindPageViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_group_result_find_page_view_holder,null));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupResultFindPageViewHolder holder, int position) {
        Group grp = groups.get(position);
        //System.out.println("USER ID :::: " + user.getUserId());
        holder.groupName.setText(grp.getName());
        holder.groupDate.setText(grp.getDate().toString());
        holder.groupTime.setText(grp.getTime().toString());
        holder.memberCount.setText(grp.getMemberCount());
        holder.groupProfilePic.setImageResource(R.drawable.default_user_img);

        holder.itemView.setOnClickListener(view -> {
//            Intent intent = new Intent(context, MessagesActivity.class);
//            intent.putExtra("Sender", this.sender);
//            intent.putExtra("Receiver", user);
//            System.out.println("Senderrrr :::::: " + sender);
//            System.out.println("Reciever :::::::: " + user.getUserId());
//            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}