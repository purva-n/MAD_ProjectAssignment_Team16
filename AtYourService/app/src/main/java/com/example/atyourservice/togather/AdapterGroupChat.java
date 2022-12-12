package com.example.atyourservice.togather;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Chats;
import com.example.atyourservice.models.Chat;
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

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterGroupChat extends RecyclerView.Adapter<HolderGroupChat> {
    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;

    private Context context;
    private List<Chat> chatList;
    GoogleSignInClient mGoogleSignInClient;
    String GpersonEmail;


    public AdapterGroupChat(Context context, List<Chat> chatList){
        this.context = context;
        this.chatList = chatList;

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

    public void updateGroupChat(List<Chat> chatList) {
        this.chatList = chatList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolderGroupChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new HolderGroupChat(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderGroupChat holder, int position) {
        Chat model = chatList.get(position);
        String message = model.getChat();
        String senderid = model.getSenderid();
        Long sendtime = model.getTimestamp();

        //Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        //@SuppressLint("SimpleDateFormat") String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal).toString();
        holder.getNameTv().setText(senderid);
        holder.getMessageTv().setText(message);
        holder.getTimeTv().setText((new Date(sendtime)).toString());
        setUserName(model,holder);
    }

    private void setUserName(Chat model, HolderGroupChat holder) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.orderByChild(GpersonEmail).equalTo(model.getSenderid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot ) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    String name = ""+ds.child("name").getValue();
                    holder.getNameTv().setText(name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position){
        System.out.println("Sender is :: " + chatList.get(position).getSenderid());
       if (chatList.get(position).getSenderid().equals(GpersonEmail)){
            return R.layout.chatview_right;
        }else{
            return R.layout.chatview_left;
        }
    }


}
