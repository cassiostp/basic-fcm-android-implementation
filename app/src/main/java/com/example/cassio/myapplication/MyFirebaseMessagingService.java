package com.example.cassio.myapplication;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private static final String DEFAULT_TITLE = "My Application";

    NotificationHelper notificationHelper;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        Map data = remoteMessage.getData();

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Data: " + data);
        Log.d(TAG, "Message Notification Body: " + body);

        if (data.size() > 0) {
            //Do something with the data.
        }

        // Omit this if only background notifications.
        sendNotification(title, body);
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageTitle
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageTitle, String messageBody) {
        if(messageTitle == null){
            messageTitle = DEFAULT_TITLE;
        }
        notificationHelper = new NotificationHelper(getApplicationContext());
        NotificationCompat.Builder builder = notificationHelper.getNotificationDefault(messageTitle, messageBody);
        notificationHelper.getNotificationManager().notify(new Random().nextInt(), builder.build());
        Log.d(TAG,"BUILDER: " + builder.build().toString());
    }
}
