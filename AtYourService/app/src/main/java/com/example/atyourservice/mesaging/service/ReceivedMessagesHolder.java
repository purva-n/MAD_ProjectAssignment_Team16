package com.example.atyourservice.mesaging.service;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;

public class ReceivedMessagesHolder extends RecyclerView.ViewHolder {
    public ImageView imageReceived;
    public TextView timestampReceived;

    public ReceivedMessagesHolder(@NonNull View viewItem) {
        super(viewItem);
        this.timestampReceived = viewItem.findViewById(R.id.timestampReceived);
        this.imageReceived = viewItem.findViewById(R.id.stickerReceived);
    }
}


