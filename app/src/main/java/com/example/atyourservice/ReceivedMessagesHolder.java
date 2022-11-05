package com.example.atyourservice;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReceivedMessagesHolder extends RecyclerView.ViewHolder {
    public ImageView imageReceived;
    public TextView timestampReceived;
    public TextView senderIDReceived;

    public ReceivedMessagesHolder(@NonNull View viewItem) {
        super(viewItem);
        this.timestampReceived = viewItem.findViewById(R.id.timestampReceived);
        this.imageReceived = viewItem.findViewById(R.id.stickerReceived);
        this.senderIDReceived = viewItem.findViewById(R.id.senderIDReceived);
    }
}


