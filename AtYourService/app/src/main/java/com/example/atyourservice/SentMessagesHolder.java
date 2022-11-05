package com.example.atyourservice;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SentMessagesHolder extends RecyclerView.ViewHolder {
       public ImageView imageSent;
       public TextView timestampSent;
       public TextView senderIDSent;

        public SentMessagesHolder(@NonNull View viewItem) {
            super(viewItem);
            this.timestampSent = viewItem.findViewById(R.id.timestampSent);
            this.imageSent = viewItem.findViewById(R.id.stickerSent);
            this.senderIDSent = viewItem.findViewById(R.id.senderIDSent);
        }
}


