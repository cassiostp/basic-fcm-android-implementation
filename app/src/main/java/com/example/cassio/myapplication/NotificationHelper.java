package com.example.cassio.myapplication;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class NotificationHelper extends ContextWrapper {
    private NotificationManager mNotificationManager;
    private static final String IMPORTANT_CHANNEL = "important";
    private static final String DEFAULT_CHANNEL = "default";

    public NotificationHelper(Context context) {
        super(context);
    }

    @TargetApi(26)
    void createChannels() {
        NotificationChannel importantChannel =
                new NotificationChannel(
                        IMPORTANT_CHANNEL,
                        getString(R.string.notification_channel_important),
                        NotificationManager.IMPORTANCE_HIGH);

        importantChannel.enableLights(true);
        importantChannel.setLightColor(Color.BLUE);
        importantChannel.enableVibration(true);
        importantChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 500, 200, 500});
        getNotificationManager().createNotificationChannel(importantChannel);

        NotificationChannel defaultChannel =
                new NotificationChannel(
                        DEFAULT_CHANNEL,
                        getString(R.string.notification_channel_default),
                        NotificationManager.IMPORTANCE_DEFAULT);

        defaultChannel.enableLights(true);
        defaultChannel.enableVibration(true);
        defaultChannel.setLightColor(Color.GREEN);
        getNotificationManager().createNotificationChannel(defaultChannel);
    }

    /**
     * @param title the title of the notification
     * @param body  the body text for the notification
     * @return A Notification.Builder configured with the
     * selected channel and details
     */
    public NotificationCompat.Builder getNotificationImportant(String title, String body) {
        return new NotificationCompat.Builder(getApplicationContext(), IMPORTANT_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(getPendingIntent())
                .setColor(Color.RED)
                .setStyle(new NotificationCompat.BigTextStyle());
    }

    /**
     * @param title the title of the notification
     * @param body  the body text for the notification
     * @return A Notification.Builder configured with the
     * selected channel and details
     */
    public NotificationCompat.Builder getNotificationDefault(String title, String body) {
        return new NotificationCompat.Builder(getApplicationContext(), DEFAULT_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(getPendingIntent())
                .setColor(Color.GREEN)
                .setStyle(new NotificationCompat.BigTextStyle());
    }

    /**
     * Create a PendingIntent for opening up the MainActivity
     * when the notification is pressed
     *
     * @return A PendingIntent that opens the MainActivity
     */
    private PendingIntent getPendingIntent() {
        Intent openMainIntent = new Intent(this, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(openMainIntent);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
    }

    public NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }
}
