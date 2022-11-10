package com.example.atyourservice.mesaging.service;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.atyourservice.R;
import com.example.atyourservice.models.Message;
import com.example.atyourservice.models.User;
import com.example.atyourservice.notification.service.NotificationApi;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.util.Date;

public class GridAdapter extends BaseAdapter {
    //https://www.geeksforgeeks.org/gridview-using-baseadapter-in-android-with-example/

    Context context;
    int[] sticker;

    LayoutInflater inflater;
    static DatabaseReference stickerDb;
    User sender;
    User receiver;

    public GridAdapter(Context context, int[] sticker, User sender, User receiver) {
        this.context = context;
        this.sticker = sticker;
        stickerDb = FirebaseDatabase.getInstance().getReference().child("senders");
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public int getCount() {
        return sticker.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.sticker_grid,null);
        }

        ImageView imageView= convertView.findViewById(R.id.stickerGrid);
        imageView.setImageResource(sticker[position]);

        imageView.setOnClickListener(view -> {
            String senderId = sender.getUserId();
            String receiverId = receiver.getUserId();


            String key = stickerDb.child(senderId).child("receivers").child(receiverId)
                       .child("stickers").push().getKey();

            assert key != null;
            stickerDb.child(senderId).child("receivers").child(receiverId)
                    .child("stickers").child(key).setValue(new Message(context.getResources().getResourceEntryName(sticker[position]),
                               new Date().getTime()));

            NotificationApi noti = new NotificationApi(receiver.getToken());
            noti.pushNotificationToReceiver(context, "At your Service", senderId, sticker[position]);

        });

        return convertView;
    }
}