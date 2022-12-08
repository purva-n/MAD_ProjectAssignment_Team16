package com.example.atyourservice.togather;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ActivityListViewHolder> {

    @NonNull
    @Override
    public ActivityListAdapter.ActivityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityListAdapter.ActivityListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ActivityListViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView image;
        public ActivityListViewHolder(@NonNull View UserView){
            super(UserView);
            name = UserView.findViewById(R.id.userName);
            image = UserView.findViewById(R.id.userProfileImg);
        }
    }
}
