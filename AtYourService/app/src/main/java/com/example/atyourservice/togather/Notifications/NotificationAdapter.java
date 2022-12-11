package com.example.atyourservice.togather.Notifications;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Notifications;
import com.example.atyourservice.models.Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> {
        //https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-types
        private final Context context;
        private final List<Notification> notifications;


        public NotificationAdapter(Context context, List<Notification> notifications){
            this.context = context;
            this.notifications = notifications;
        }

//        @Override
//        public int getItemViewType(final int position) {
//            return  R.layout.notification_template;
//        }

        @NonNull
        @Override
        public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.notification_template ,null);
            NotificationHolder holder = new NotificationHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
            holder.title.setText(notifications.get(position).getGroupid());
            holder.message.setText(notifications.get(position).getMessage());

        }

        @Override
        public int getItemCount() {
            return notifications.size();
        }
    }
