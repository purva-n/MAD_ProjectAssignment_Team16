package com.example.atyourservice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.atyourservice.ChatPackage.MessagesActivity;

import com.example.atyourservice.ChatPackage.MessagesActivity;
import com.example.atyourservice.models.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    Context context;
    ArrayList<User> list;
    String sender_id;

    public UserAdapter(Context context, ArrayList<User> list, String sender_id) {
        this.context = context;
        this.list = list;
        this.sender_id = sender_id;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_user_list_template, null));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = list.get(position);
        System.out.println("USER ID :::: " + user.getUserId());
        holder.name.setText(user.getUserId());
        holder.image.setImageResource(R.drawable.default_user_img);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MessagesActivity.class);
            intent.putExtra("userId", this.sender_id);
            intent.putExtra("receiverId", user.getUserId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView image;
        public UserViewHolder(@NonNull View UserView){
            super(UserView);
            name = UserView.findViewById(R.id.userName);
            image = UserView.findViewById(R.id.userProfileImg);
        }
    }
}

