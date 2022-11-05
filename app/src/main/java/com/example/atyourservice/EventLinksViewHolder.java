package com.example.atyourservice;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventLinksViewHolder extends RecyclerView.ViewHolder {
    public TextView eventName;
    public TextView eventDate;
    public TextView eventTime;
    public ImageView icon;

    public EventLinksViewHolder(@NonNull View viewItem) {
        super(viewItem);
        this.eventName = viewItem.findViewById(R.id.eventName);
        this.eventDate = viewItem.findViewById(R.id.eventDate);
        this.eventTime = viewItem.findViewById(R.id.eventTime);
        this.icon = viewItem.findViewById(R.id.eventImg);
    }
}
