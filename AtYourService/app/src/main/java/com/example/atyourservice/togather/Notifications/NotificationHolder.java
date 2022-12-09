package com.example.atyourservice.togather.Notifications;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;

import org.w3c.dom.Text;

public class NotificationHolder extends RecyclerView.ViewHolder {
    public TextView message;
    public TextView title;

    public NotificationHolder(@NonNull View viewItem) {
        super(viewItem);
        message = viewItem.findViewById(R.id.notificationSummary);
        title = viewItem.findViewById(R.id.notificationTitle);


    }
}
