package com.example.atyourservice.togather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
import com.example.atyourservice.models.Group;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterGroupChatList extends RecyclerView.Adapter<AdapterGroupChatList.HolderGroupChatList>{
    private Context context;
    private List<Group> groupChatLists;

    public AdapterGroupChatList(Context context, List<Group> groupChatLists){
        this.context = context;
        this.groupChatLists = groupChatLists;
    }

    public void updateGroupChatLists(List<Group> groupList) {
        this.groupChatLists = groupList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolderGroupChatList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatview_layout,parent,false);
        return new HolderGroupChatList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderGroupChatList holder, int position) {
        Group model = groupChatLists.get(position);
        String groupId = model.getId();
        String groupIcon = model.getIcon();
        String groupName = model.getName();

        holder.groupNameTv.setText(groupName);
        try{
            Picasso.get().load(groupIcon).placeholder(R.drawable.default_group_chat_icon).into(holder.groupIconIv);
        }
        catch (Exception e){
            holder.groupIconIv.setImageResource(R.drawable.default_group_chat_icon);
        }

        Group group = new Group();
        group.setId(groupId);
        group.setIcon(groupIcon);
        group.setName(groupName);
        //open chat window
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GroupChatActivity.class);
                intent.putExtra("group",group);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupChatLists.size();
    }

    //viewholder class
    class HolderGroupChatList extends RecyclerView.ViewHolder{
        private ImageView groupIconIv;
        private TextView groupNameTv;
        private TextView senderNameTv;
        private TextView messageTv;
        private TextView timeTv;

        public HolderGroupChatList(@NonNull View itemView){
            super(itemView);
            groupIconIv = itemView.findViewById(R.id.iconofgroup);
            groupNameTv = itemView.findViewById(R.id.nameofgroup);
            senderNameTv = itemView.findViewById(R.id.nameofsender);
            messageTv = itemView.findViewById(R.id.newestmessage);
            timeTv = itemView.findViewById(R.id.newestmessagetime);
        }
    }
}
