package com.example.atyourservice.togather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
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

import java.util.Date;
import java.util.List;

public class GroupResultsMatchedPageAdapter extends RecyclerView.Adapter<GroupResultMatchedPageViewHolder>{
    private List<Group> groups;
    private Context context;
    private DatabaseReference dbRef;
    private GoogleSignInClient mGoogleSignInClient;
    private String GpersonEmail;

    public GroupResultsMatchedPageAdapter(List<Group> groups, Context context) {
        this.groups = groups;
        this.context = context;
        this.dbRef = FirebaseDatabase.getInstance().getReference();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
        if (acct != null) {
            //Can use to access using below getters
            GpersonEmail = acct.getEmail();
            GpersonEmail = GpersonEmail.substring(0, GpersonEmail.length() - 10).replace(".", "_");
        }
    }

    @Override
    public int getItemViewType(final int position) {
        return  R.layout.activity_group_result_matched_page;
    }

    @NonNull
    @Override
    public GroupResultMatchedPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return new GroupResultFindPageViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_group_result_find_page_view_holder,null));
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new GroupResultMatchedPageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupResultMatchedPageViewHolder holder, int position) {
        Group grp = groups.get(position);
        //System.out.println("USER ID :::: " + user.getUserId());
        holder.getGroupName().setText(grp.getName().toUpperCase());
        holder.getGroupDate().setText(new Date(grp.getDate()).toString());
        holder.getMemberCount().setText(String.valueOf(grp.getMemberCount()));

        holder.getGroupName().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                GroupInfoPage rv = GroupInfoPage.newInstance();
                String groupName = ((TextView) view.findViewById(R.id.groupName)).getText().toString();
                System.out.println("GROUP NAMEEE ::::: " + groupName);


                if(grp != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("GroupSelected", grp);
                    rv.setArguments(bundle);
                    ft.setReorderingAllowed(true)
                            .addToBackStack("groupResultList")
                            .replace(R.id.fragmentContainer,
                                    rv,
                                    null)
                            .commit();
                }

            }
        });

        //TODO: get username

        dbRef.child("users").child(GpersonEmail).child("groups").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for(DataSnapshot grpid: snapshot.getChildren()) {
                        if(grpid.getValue().equals(grp.getId())) {
                            holder.joinGroup.setText("Joined");
                            holder.joinGroup.setEnabled(false);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.joinGroup.setOnClickListener(view -> {
//          //TODO: get username
            dbRef.child("users").child(GpersonEmail).child("groups").push().setValue(grp.getId());
            dbRef.child("groups").child(grp.getId()).child("users").push().setValue(GpersonEmail);
            dbRef.child("groups").child(grp.getId()).child("memberCount").addListenerForSingleValueEvent(new ValueEventListener() {
                Long memberCount = 0L;

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    memberCount = (Long) snapshot.getValue();
                    assert memberCount != null;
                    if(dbRef.child("groups").child(grp.getId()).child("memberCount").setValue(memberCount + 1).isSuccessful()) {
                        Toast.makeText(view.getContext(), "Joined Group", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
