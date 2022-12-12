package com.example.atyourservice.togather.Notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Notifications;
import com.example.atyourservice.models.Group;
import com.example.atyourservice.models.Notification;
import com.example.atyourservice.togather.GroupChatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationRecyclerView  extends Fragment implements SelectListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private DatabaseReference dbRef;
    private Notifications notificationList;
    public NotificationRecyclerView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationRecyclerView newInstance() {
        NotificationRecyclerView fragment = new NotificationRecyclerView();
        return fragment;
    }
    // TODO update oncreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            notificationList = (Notifications) bundle.getSerializable("notifications");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView notificationRecycler;
        View v =  inflater.inflate(R.layout.fragment_notification, container, false);
        notificationRecycler = v.findViewById(R.id.recyclerFragment);
        notificationRecycler.setLayoutManager(new LinearLayoutManager(v.getContext()));
        notificationRecycler.setAdapter(new NotificationAdapter(v.getContext(), notificationList.getNotifications(),this));
        return v;
    }

    @Override
    public void onItemClicked(Notification notification) {
        Intent intent = new Intent(getContext(), GroupChatActivity.class);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("groups").child(notification.getGroupid());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Group group = snapshot.getValue(Group.class);
                group.setId(snapshot.getKey());
                intent.putExtra("group",group);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

