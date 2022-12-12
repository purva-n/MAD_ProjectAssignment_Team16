package com.example.atyourservice.togather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Groups;
import com.example.atyourservice.models.Group;
import com.example.atyourservice.models.HomePageActivities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesListAdapter extends RecyclerView.Adapter<ActivitiesListAdapter.ActivitiesViewHolder>{
    List<HomePageActivities> list;
    DatabaseReference dbRef;
    Context ctx;

    public ActivitiesListAdapter(Context ctx, List<HomePageActivities>  list) {
        this.ctx = ctx;
        this.list = list;
        this.dbRef = FirebaseDatabase.getInstance().getReference();
    }

    public void setFilteredList(List<HomePageActivities> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
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
        holder.getActivityName().setText(activities.getActivityName());
        holder.getImage().setImageResource(activities.getActivityImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.child("groups").orderByChild("category").equalTo(activities.getActivityName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Groups groups = new Groups();
                        if(snapshot.exists()) {
                            for (DataSnapshot grpSnap : snapshot.getChildren()) {
                                if (grpSnap.exists()) {
                                    Group g = grpSnap.getValue(Group.class);
                                    if (g != null) {
                                        g.setId(grpSnap.getKey());
                                        groups.getGroups().add(g);
                                    }

                                } else {
                                    // Not found
                                }
                            }

                            FragmentTransaction ft = ((AppCompatActivity)ctx).getSupportFragmentManager().beginTransaction();
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            GroupResultFindPageRecyclerView rv = GroupResultFindPageRecyclerView.newInstance();

                            if(groups.getGroups().size() > 0) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("GroupResult", groups);
                                rv.setArguments(bundle);
                                ft.setReorderingAllowed(true)
                                        .addToBackStack("HomePageActivity")
                                        .replace(R.id.fragmentContainer,
                                                rv,
                                                null)
                                        .commit();
                            }

                        }else {
                            ((AppCompatActivity)ctx).getSupportFragmentManager().beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.fragmentContainer, com.example.atyourservice.togather.GroupResultNotFoundPage.class, null)
                                    .addToBackStack("HomePageActivity")
                                    .commit();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
         });
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
