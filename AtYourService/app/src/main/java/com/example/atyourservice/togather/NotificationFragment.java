package com.example.atyourservice.togather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Notifications;
import com.example.atyourservice.models.Notification;
import com.example.atyourservice.togather.Notifications.NotificationAdapter;
import com.example.atyourservice.togather.Notifications.NotificationRecyclerView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

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
    private GoogleSignInClient mGoogleSignInClient;
    private String GpersonEmail;
    public AppCompatActivity context;


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
        context = (AppCompatActivity)getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            //Can use to access using below getters
            GpersonEmail = acct.getEmail();
            GpersonEmail = GpersonEmail.substring(0, GpersonEmail.length() - 10).replace(".", "_");
        }

        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference notiDB = dbRef.child("users").child(GpersonEmail).child("notification");
        Notifications notificationList = new Notifications();
        notiDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    notificationList.getNotifications().add(snapshot1.getValue(Notification.class));
                }

                for (int i = 0; i < notificationList.getNotifications().size(); i++) {
                    System.out.println(notificationList.getNotifications().get(i).toString());
                }

                FragmentTransaction ft =context.getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                NotificationRecyclerView nrv = NotificationRecyclerView.newInstance();

                Collections.reverse(notificationList.getNotifications());
                if(notificationList.getNotifications().size() > 0) {
                    if(getContext() ==context){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("notifications", notificationList);
                    nrv.setArguments(bundle);
                    ft.setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,
                                    nrv,
                                    null)
                            .commit();
                }

                    }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });


        for (Notification noti : notificationList.getNotifications()) {
            System.out.println(noti);

        }


    }

}


// TODO below will be added to send message activity
       /* DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference();
        String groupId = "id";
        String currentUser="currentUserId";
        DatabaseReference users =firebaseRef.child("groups").child(groupId).child("users");
        ArrayList<String> notiReceivers= new ArrayList<String>();
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot snapshot1: snapshot.getChildren()){
                    String receivers = snapshot1.getKey();
                    if(!receivers.equalsIgnoreCase(currentUser)){
                        notiReceivers.add(receivers);
                    }}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*//*
        for(String receiver: notiReceivers) {
            firebaseRef.child("users").child("notification").child(receiver).push().setValue(new Notifications("groupId", "message"));

        }


    }
*/


