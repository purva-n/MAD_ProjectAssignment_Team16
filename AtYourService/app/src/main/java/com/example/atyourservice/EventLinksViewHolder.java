package com.example.atyourservice;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.api.response.pojo.Images;

import java.io.InputStream;
import java.net.URL;

public class EventLinksViewHolder extends RecyclerView.ViewHolder {
    public TextView eventName;
    public ImageView icon;

    public EventLinksViewHolder(@NonNull View viewItem) {
        super(viewItem);
        this.eventName = viewItem.findViewById(R.id.eventName);
        this.icon = viewItem.findViewById(R.id.eventImg);
    }
}
