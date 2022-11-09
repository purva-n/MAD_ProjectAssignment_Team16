package com.example.atyourservice.mesaging.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Embedded;
import com.example.atyourservice.models.Message;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-types
    private final Context context;
    private final List<Message> messages;
    int drawableId;

    public ChatMessageAdapter(Context context, List<Message> messages){
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if(messages.get(position).getFrom().equals("sender")) {
            type = 0;
        }

        if(messages.get(position).getFrom().equals("receiver")) {
            type = 1;
        }

        return type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new SentMessagesHolder(LayoutInflater.from(context).inflate(R.layout.messages_sent_template,null));
            case 1:
            default:
                return new ReceivedMessagesHolder(LayoutInflater.from(context).inflate(R.layout.messages_received_template,null));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //http://daniel-codes.blogspot.com/2009/12/dynamically-retrieving-resources-in.html
        switch (holder.getItemViewType()) {
            case 0: {
                SentMessagesHolder sentMessagesHolder = (SentMessagesHolder) holder;
                try {
                    Class res = R.drawable.class;
                    Field field = res.getField(messages.get(position).getStickerId());
                    drawableId = field.getInt(null);
                }
                catch (Exception e) {
                    // default sticker
                    Class res = R.drawable.class;
                    Field field = null;
                    try {
                        field = res.getField("default_sticker");
                        drawableId = field.getInt(null);
                    } catch (NoSuchFieldException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }

                }

                sentMessagesHolder.imageSent.setImageResource(drawableId);
                Date date = new Date(messages.get(position).getTimestamp());
                Format formatter = new SimpleDateFormat("MM-dd HH:mm");
                String dateView = formatter.format(date);
                sentMessagesHolder.timestampSent.setText(dateView);
            }
            break;

            case 1: {
                ReceivedMessagesHolder receivedMessagesHolder = (ReceivedMessagesHolder) holder;

                try {
                    Class res = R.drawable.class;
                    Field field = res.getField(messages.get(position).getStickerId());
                    drawableId = field.getInt(null);
                }
                catch (Exception e) {
                    // default sticker
                    Class res = R.drawable.class;
                    Field field = null;
                    try {
                        field = res.getField("default_sticker");
                        drawableId = field.getInt(null);
                    } catch (NoSuchFieldException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }

                receivedMessagesHolder.imageReceived.setImageResource(drawableId);
                Date date = new Date(messages.get(position).getTimestamp());
                Format formatter = new SimpleDateFormat("MM-dd  HH:mm");
                String dateView = formatter.format(date);
                receivedMessagesHolder.timestampReceived.setText(dateView);
            }
        }
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }
}
