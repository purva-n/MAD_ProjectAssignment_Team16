package com.example.atyourservice.togather;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterGroupChatList extends RecyclerView.Adapter<AdapterGroupChatList.HolderGroupChatList>{
    private Context context;
    private ArrayList<ModelGroupChatList> groupChatLists;

    public AdapterGroupChatList(Context context, ArrayList<ModelGroupChatList> groupChatLists){
        this.context = context;
        this.groupChatLists = groupChatLists;
    }
    @NonNull
    @Override
    public HolderGroupChatList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatview_layout,parent,false);
        return new HolderGroupChatList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderGroupChatList holder, int position) {
        ModelGroupChatList model = groupChatLists.get(position);
        String groupId = model.getGroupid();
        String groupIcon = model.getGroupicon();
        String groupName = model.getGroupname();

        holder.groupNameTv.setText(groupName);
        try{
            Picasso.get().load(groupIcon).placeholder(R.drawable.default_group_chat_icon).into(holder.groupIconIv);
        }
        catch (Exception e){
            holder.groupIconIv.setImageResource(R.drawable.default_group_chat_icon);
        }

        //open chat window
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GroupChatActivity.class);
                intent.putExtra("groupid",groupId);
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
