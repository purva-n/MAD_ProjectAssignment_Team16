package com.example.atyourservice.ToGather.Notifications;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;

public class NotificationHolder extends RecyclerView.ViewHolder {
    public TextView body;

    public NotificationHolder(@NonNull View viewItem) {
        super(viewItem);
        body = viewItem.findViewById(R.id.notificationSummary);
    }
}
