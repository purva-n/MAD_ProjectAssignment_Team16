package com.example.atyourservice.ChatPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Embedded;
import com.example.atyourservice.models.Message;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ReceivedMessagesHolder> {
    private final Context context;
    private ArrayList<Message> messages;
    public ChatMessageAdapter( Context context, ArrayList<Message> messages){
        this.context = context;
        this.messages= messages;

    }
    @NonNull
    @Override
    public ReceivedMessagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceivedMessagesHolder(LayoutInflater.from(context).inflate(R.layout.messages_received_template,null));

    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedMessagesHolder holder, int position) {
        holder.imageReceived = messages.get(position).getStickerId();
        holder.timestampReceived.setText((int) messages.get(position).getTimestamp());
       // holder.senderIDReceived = receiver ID

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
