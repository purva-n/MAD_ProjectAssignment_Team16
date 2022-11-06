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

    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
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
            context.startActivity(new Intent(context, MessagesActivity.class));
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

