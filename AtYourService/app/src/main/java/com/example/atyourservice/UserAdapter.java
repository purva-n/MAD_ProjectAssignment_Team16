package com.example.atyourservice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.models.User;

import java.io.InputStream;
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
        View v = LayoutInflater.from(context).inflate(R.layout.user,parent,false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = list.get(position);
        holder.name.setText(user.getUserName());
//        Uri imgUri = Uri.parse(user.getUriImage());
//        InputStream stream = getContentResolver().openInputStream(imgUri);
        holder.image.setImageResource(R.drawable.default_user_img);
        holder.itemView.setOnClickListener(view -> {
//            Uri uri = Uri.parse(link);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(uri);
            context.startActivity(new Intent(context,MessagesActivity.class));
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

