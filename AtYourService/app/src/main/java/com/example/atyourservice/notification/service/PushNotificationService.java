package com.example.atyourservice.notification.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.example.atyourservice.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//https://firebase.google.com/docs/cloud-messaging/android/client
public class PushNotificationService extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("New Api")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();
        String CHANNEL_ID = "MESSAGE";

        System.out.println("HELLO :: I got called yayyyyyyyyy");

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Message Notification", NotificationManager.IMPORTANCE_DEFAULT);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);

        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(0, notification.build());
        super.onMessageReceived(remoteMessage);
    }
}
