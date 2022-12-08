package com.example.atyourservice.togather.Notifications;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;

public class NotificationHolder extends RecyclerView.ViewHolder {
    public TextView body;

    public NotificationHolder(@NonNull View viewItem) {
        super(viewItem);
        body = viewItem.findViewById(R.id.notificationSummary);
    }
}
