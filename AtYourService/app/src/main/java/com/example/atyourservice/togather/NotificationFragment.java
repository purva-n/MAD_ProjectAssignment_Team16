package com.example.atyourservice.togather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atyourservice.ActivityRecyclerView;
import com.example.atyourservice.EventLinksAdapter;
import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Embedded;
import com.example.atyourservice.models.Notifications;
import com.example.atyourservice.togather.Notifications.NotificationAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Notifications> notificationsList = new ArrayList<Notifications>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        //TODO replace child"uuid" with current user

        DatabaseReference notiDB =dbRef.child("users").child("uuid2").child("notification");
        notiDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Notifications notificationItem = new Notifications(snapshot1.child("groupid").getValue(String.class), snapshot1.child("message").getValue(String.class));
                    notificationsList.add(notificationItem);}
                    for(int i =0; i<notificationsList.size(); i++){
                        System.out.println(notificationsList.get(i));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        /*
        RecyclerView notificationRecycler;

        notificationRecycler = getView().findViewById(R.id.recyclerView);
        notificationRecycler.setLayoutManager(new LinearLayoutManager());
        notificationRecycler.setAdapter(new NotificationAdapter(ActivityRecyclerView.this, notifications))
*/
    }
}