package com.example.atyourservice.togather;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.MainActivity;
import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Groups;
import com.example.atyourservice.models.Group;
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
import java.util.List;
import java.util.Objects;

public class GroupChatFragment extends Fragment {
    private RecyclerView groupsRv;
    private Groups groupChatLists;
    private AdapterGroupChatList adapterGroupChatList;
    private GoogleSignInClient mGoogleSignInClient;
    private String GpersonEmail;

    public GroupChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_group_chat,container,false);
        groupsRv = view.findViewById(R.id.groupsRv);

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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadGroupChatLists();
    }

    private void loadGroupChatLists() {
        groupChatLists = new Groups();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersDb = dbRef.child("users").child(GpersonEmail).child("groups");
        groupChatLists.getGroups().clear();

        adapterGroupChatList = new AdapterGroupChatList(getContext(), groupChatLists.getGroups());

        usersDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    String key = ds.getValue(String.class);
                    DatabaseReference groupsDb = dbRef.child("groups").child(key);
                    groupsDb.addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Group grp = snapshot.getValue(Group.class);
                                        if (grp != null) {
                                            grp.setId(snapshot.getKey());
                                            groupChatLists.getGroups().add(grp);
                                            System.out.println("In for");
                                            System.out.println(groupChatLists.getGroups());
                                        }
                                    }
                                    adapterGroupChatList.updateGroupChatLists(groupChatLists.getGroups());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        groupsRv.setAdapter(adapterGroupChatList);

    }

}