package com.example.atyourservice.togather;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Chats;
import com.example.atyourservice.models.Chat;
import com.example.atyourservice.models.Group;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.security.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AdapterGroupChatList extends RecyclerView.Adapter<AdapterGroupChatList.HolderGroupChatList>{
    private Context context;
    private List<Group> groupChatLists;
    private DatabaseReference ref;

    public AdapterGroupChatList(Context context, List<Group> groupChatLists){
        this.context = context;
        this.groupChatLists = groupChatLists;
        this.ref = FirebaseDatabase.getInstance().getReference();
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
        //String groupIcon = model.getIcon();
        Chats messageList = new Chats();
        String groupName = model.getName();

        holder.groupNameTv.setText(groupName.toUpperCase());

        Group group = new Group();
        group.setId(groupId);
        //group.setIcon(groupIcon);
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

        final String[] latestMessage = {""};
        final String[] latestSender = {""};
        final long[] timeStamp = {0};

        DatabaseReference messageDb = ref.child("messages").child(groupId);
        messageDb.addChildEventListener(new ChildEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    Chat message = snapshot.getValue(Chat.class);
                    if (message != null) {
                        messageList.getChats().add(message);
                        messageList.getChats().sort(Comparator.comparing(Chat::getTimestamp));
                        latestMessage[0] = messageList.getChats().get(messageList.getChats().size()-1).getChat();
                        latestSender[0] =  messageList.getChats().get(messageList.getChats().size()-1).getSenderid();
                        timeStamp[0] =  messageList.getChats().get(messageList.getChats().size()-1).getTimestamp();

                        holder.getSenderNameTv().setText(latestSender[0]);
                        holder.getMessageTv().setText(latestMessage[0]);
                        Date date = new Date(timeStamp[0]);
                        Format formatter = new SimpleDateFormat("MM-dd HH:mm");
                        String dateView = formatter.format(date);
                        holder.getTimeTv().setText(dateView);
                    }
                } else {
                    holder.getSenderNameTv().setText("");
                    holder.getMessageTv().setText("");
                    holder.getTimeTv().setText("");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
            //groupIconIv = itemView.findViewById(R.id.iconofgroup);
            groupNameTv = itemView.findViewById(R.id.nameofgroup);
            senderNameTv = itemView.findViewById(R.id.nameofsender);
            messageTv = itemView.findViewById(R.id.newestmessage);
            timeTv = itemView.findViewById(R.id.newestmessagetime);
        }

        public ImageView getGroupIconIv() {
            return groupIconIv;
        }

        public TextView getGroupNameTv() {
            return groupNameTv;
        }

        public TextView getSenderNameTv() {
            return senderNameTv;
        }

        public TextView getMessageTv() {
            return messageTv;
        }

        public TextView getTimeTv() {
            return timeTv;
        }
    }
}
