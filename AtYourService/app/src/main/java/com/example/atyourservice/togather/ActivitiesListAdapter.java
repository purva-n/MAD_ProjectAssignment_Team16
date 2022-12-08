package com.example.atyourservice.togather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
import com.example.atyourservice.models.HomePageActivities;

import java.util.ArrayList;

public class ActivitiesListAdapter extends RecyclerView.Adapter<ActivitiesListAdapter.ActivitiesViewHolder>{
    ArrayList<HomePageActivities> list;

    public ActivitiesListAdapter(ArrayList<HomePageActivities>  list) {

        this.list = list;
    }


    @Override
    public int getItemViewType(final int position) {
        return R.layout.activity_home_list_activities_template;
    }

    @NonNull
    @Override
    public ActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("PARENT ::: " + parent);
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ActivitiesViewHolder(view);
       // return new ActivitiesViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_home_list_activities_template, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitiesViewHolder holder, int position) {
        HomePageActivities activities = list.get(position);
        System.out.println("List size here : " + list.size());
        System.out.println("position : " + position);

        System.out.println("HERE " + activities.getActivityName());
        System.out.println("holder :: " +  holder.activityName);
        holder.getActivityName().setText(activities.getActivityName());
        holder.getImage().setImageResource(activities.getActivityImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ActivitiesViewHolder extends RecyclerView.ViewHolder {

        private TextView activityName;
        private ImageView image;
        public ActivitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.activityName);
            image = itemView.findViewById(R.id.activityImg);
        }

        public TextView getActivityName() {
            return activityName;
        }

        public ImageView getImage() {
            return image;
        }
    }
}
