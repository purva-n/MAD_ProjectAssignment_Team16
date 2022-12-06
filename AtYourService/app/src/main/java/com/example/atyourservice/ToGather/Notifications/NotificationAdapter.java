package com.example.atyourservice.ToGather.Notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
import com.example.atyourservice.mesaging.service.ReceivedMessagesHolder;
import com.example.atyourservice.mesaging.service.SentMessagesHolder;
import com.example.atyourservice.models.Message;
import com.example.atyourservice.models.Notifications;

import java.lang.reflect.Field;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        //https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-types
        private final Context context;
        private final List<Notifications> notifications;
        int drawableId;

        public NotificationAdapter(Context context, List<Notifications> notifications){
            this.context = context;
            this.notifications = notifications;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.notification_template,null);
            NotificationHolder holder = new NotificationHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
           // holder.itemView.findViewById(R.id.notificationSummary.)

        }


        @Override
        public int getItemCount() {
            return notifications.size();
        }
    }
