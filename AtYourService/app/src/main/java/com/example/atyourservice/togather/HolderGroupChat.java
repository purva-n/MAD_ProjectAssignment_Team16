package com.example.atyourservice.togather;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;

public class HolderGroupChat extends RecyclerView.ViewHolder{

    private TextView nameTv,messageTv,timeTv;
    public HolderGroupChat(@Nullable View itemView){
        super(itemView);
        nameTv = itemView.findViewById(R.id.nameTv);
        messageTv = itemView.findViewById(R.id.messageTv);
        timeTv = itemView.findViewById(R.id.timeTv);
    }

    public TextView getNameTv() {
        return nameTv;
    }

    public TextView getMessageTv() {
        return messageTv;
    }

    public TextView getTimeTv() {
        return timeTv;
    }
}
