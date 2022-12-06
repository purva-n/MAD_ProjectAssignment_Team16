package com.example.atyourservice.ToGather.Notifications;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.example.atyourservice.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Locale;

//https://firebase.google.com/docs/cloud-messaging/android/client
public class ToGatherPushNotificationService extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("New Api")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();

        String CHANNEL_ID = "MESSAGE";

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Message Notification", NotificationManager.IMPORTANCE_DEFAULT);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);

        assert text != null;
        RemoteViews notificationLayout = new RemoteViews(getPackageName(),R.layout.notification_template);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(notificationLayout)
                .setContentTitle(title)
                //.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.notification_icon))
                //.setStyle(new Notification.BigPictureStyle().setSummaryText(remoteMessage.getNotification().getBody()).setBigContentTitle(text.toUpperCase(Locale.ROOT)))
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(0, notification.build());
        super.onMessageReceived(remoteMessage);
    }
}


