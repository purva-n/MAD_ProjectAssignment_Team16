package com.example.atyourservice.togather.Notifications;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;

public class NotificationHolder extends RecyclerView.ViewHolder {
    public TextView message;
    public TextView title;
    public CardView cardView;

    public NotificationHolder(@NonNull View viewItem) {
        super(viewItem);
        message = viewItem.findViewById(R.id.messageBody);
        title = viewItem.findViewById(R.id.groupidName);
        cardView= viewItem.findViewById(R.id.notifcationContainter);

    }
}

