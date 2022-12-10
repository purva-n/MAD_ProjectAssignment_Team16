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
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.MainActivity;
import com.example.atyourservice.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupChatFragment extends Fragment {
    private RecyclerView groupsRv;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ArrayList<ModelGroupChatList> groupChatLists;
    private AdapterGroupChatList adapterGroupChatList;

    public GroupChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_group_chat,container,false);
        groupsRv = view.findViewById(R.id.groupsRv);
        firebaseAuth = FirebaseAuth.getInstance();
        loadGroupChatLists();
        return view;
    }

    private void loadGroupChatLists() {
        groupChatLists = new ArrayList<>();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("groups");
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference groupDB = dbRef.child("groups").child("uuid1").child("users");
        groupDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupChatLists.clear();

                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    if (ds.child("users").child("uuid2").exists()){
                        ModelGroupChatList model = ds.getValue(ModelGroupChatList.class);
                        groupChatLists.add(model);
                    }
                }
                adapterGroupChatList = new AdapterGroupChatList(getContext(), groupChatLists);
                groupsRv.setAdapter(adapterGroupChatList);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

//    private void searchGroupChatLists(final String query) {
//        groupChatLists = new ArrayList<>();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("groups");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                groupChatLists.clear();
//                for (DataSnapshot ds:dataSnapshot.getChildren()){
//                    if (ds.child("users").child(firebaseUser.getUid()).exists()) {
//                        if (ds.child("name").toString().toLowerCase().contains(query.toLowerCase())) {
//                            ModelGroupChatList model = ds.getValue(ModelGroupChatList.class);
//                            groupChatLists.add(model);
//                        }
//                    }
//                }
//                adapterGroupChatList = new AdapterGroupChatList(getActivity(), groupChatLists);
//                groupsRv.setAdapter(adapterGroupChatList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState){
//        setHasOptionsMenu(true);
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
//        inflater.inflate(R.menu.menu_main,menu);
//        MenuItem item = menu.findItem(R.id.searchButton);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(!TextUtils.isEmpty(query.trim())){
//                    searchGroupChatLists(query);
//                }else{
//                    loadGroupChatLists();
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if(!TextUtils.isEmpty(newText.trim())){
//                    searchGroupChatLists(newText);
//                }else{
//                    loadGroupChatLists();
//                }
//                return false;
//            }
//        });
//        super.onCreateOptionsMenu(menu,inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//        if(id==R.id.action_logout){
//            firebaseAuth.signOut();
//            checkUserStatus();
//        }
//        else if(id ==R.id.action_create_group){
//            startActivity(new Intent(getActivity(),GroupCreateActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void checkUserStatus() {
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if(user==null){
//            startActivity(new Intent(getActivity(), HomePageActivity.class));
//            getActivity().finish();
//        }
//    }

}