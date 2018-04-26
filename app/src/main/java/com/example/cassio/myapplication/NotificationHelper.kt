package com.example.cassio.myapplication

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder

class NotificationHelper(context: Context) : ContextWrapper(context) {
    private var mNotificationManager: NotificationManager? = null

    /**
     * Create a PendingIntent for opening up the MainActivity
     * when the notification is pressed
     *
     * @return A PendingIntent that opens the MainActivity
     */
    private// The stack builder object will contain an artificial back stack for the
    // started Activity.
    // This ensures that navigating backward from the Activity leads out of
    // your application to the Home screen.
    // Adds the back stack for the Intent (but not the Intent itself)
    // Adds the Intent that starts the Activity to the top of the stack
    val pendingIntent: PendingIntent?
        get() {
            val openMainIntent = Intent(this, MainActivity::class.java)
            val stackBuilder = TaskStackBuilder.create(this)
            stackBuilder.addParentStack(MainActivity::class.java)
            stackBuilder.addNextIntent(openMainIntent)
            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)
        }

    val notificationManager: NotificationManager
        get() {
            val newNotificationManager = mNotificationManager ?: getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager = newNotificationManager
            return newNotificationManager
        }

    @TargetApi(26)
    internal fun createChannels() {
        val importantChannel = NotificationChannel(
                IMPORTANT_CHANNEL,
                getString(R.string.notification_channel_important),
                NotificationManager.IMPORTANCE_HIGH)

        importantChannel.enableLights(true)
        importantChannel.lightColor = Color.BLUE
        importantChannel.enableVibration(true)
        importantChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 500, 200, 500)
        notificationManager.createNotificationChannel(importantChannel)

        val defaultChannel = NotificationChannel(
                DEFAULT_CHANNEL,
                getString(R.string.notification_channel_default),
                NotificationManager.IMPORTANCE_DEFAULT)

        defaultChannel.enableLights(true)
        defaultChannel.enableVibration(true)
        defaultChannel.lightColor = Color.GREEN
        notificationManager.createNotificationChannel(defaultChannel)
    }

    /**
     * @param title the title of the notification
     * @param body  the body text for the notification
     * @return A Notification.Builder configured with the
     * selected channel and details
     */
    fun getNotificationImportant(title: String, body: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, IMPORTANT_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setColor(Color.RED)
                .setStyle(NotificationCompat.BigTextStyle())
    }

    /**
     * @param title the title of the notification
     * @param body  the body text for the notification
     * @return A Notification.Builder configured with the
     * selected channel and details
     */
    fun getNotificationDefault(title: String, body: String?): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, DEFAULT_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setColor(Color.GREEN)
                .setStyle(NotificationCompat.BigTextStyle())
    }

    companion object {
        private const val IMPORTANT_CHANNEL = "important"
        private const val DEFAULT_CHANNEL = "default"
    }
}
