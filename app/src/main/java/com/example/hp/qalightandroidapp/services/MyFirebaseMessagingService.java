package com.example.hp.qalightandroidapp.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.activities.MainActivity;
import com.example.hp.qalightandroidapp.fragments.notifications.database_notifications.DatabaseHandler;
import com.example.hp.qalightandroidapp.fragments.notifications.database_notifications.ModelDatabaseNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import static com.example.hp.qalightandroidapp.Constants.EXTRA_NOTIFICATION_FRAGMENT;
import static com.example.hp.qalightandroidapp.Constants.RESULT_CODE_FCM;

/**
 * Created by hp on 016 16.10.2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        Log.d("TAG", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("TAG", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            showNotification(remoteMessage.getNotification().getBody());

            // adding notification to db
            db.addNotification(new ModelDatabaseNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), 0));


            Log.d("Reading: ", "Reading all contacts..");
            List<ModelDatabaseNotification> contacts = db.getAllNotifications();

            for (ModelDatabaseNotification cn : contacts) {
                String log = "Id: "+cn.get_id()+" ,Name: " + cn.get_title() + " ,Body: " + cn.get_description();
                // Writing Contacts to log
                Log.d("Name: ", log);
            }
        }

    }

    private void showNotification(String message) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // intent to open specific fragment
        intent.putExtra(EXTRA_NOTIFICATION_FRAGMENT, "openIt");

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), RESULT_CODE_FCM, intent, PendingIntent.FLAG_ONE_SHOT);

        // sound of notification
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(message)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .setSound(alarmSound);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());
    }


}
