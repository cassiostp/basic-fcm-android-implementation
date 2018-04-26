package com.example.cassio.myapplication

import android.util.Log

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private lateinit var notificationHelper: NotificationHelper

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val title = remoteMessage!!.notification!!.title
        val body = remoteMessage.notification!!.body
        val data = remoteMessage.data

        Log.d(TAG, "From: " + remoteMessage.from!!)
        Log.d(TAG, "Data: $data")
        Log.d(TAG, "Message Notification Body: " + body!!)

        if (data.isNotEmpty()) {
            //Do something with the data.
        } else {
            // Omit this if only background notifications.
            sendNotification(title, body)
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageTitle
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageTitle: String?, messageBody: String?) {
        val title = messageTitle ?: DEFAULT_TITLE
        notificationHelper = NotificationHelper(applicationContext)
        val builder = notificationHelper.getNotificationDefault(title, messageBody)
        notificationHelper.notificationManager.notify(Random().nextInt(), builder.build())
        Log.d(TAG, "BUILDER: " + builder.build().toString())
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
        private const val DEFAULT_TITLE = "My Application"
    }
}
