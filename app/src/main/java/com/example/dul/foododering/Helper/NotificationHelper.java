package com.example.dul.foododering.Helper;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import com.example.dul.foododering.R;

public class NotificationHelper extends ContextWrapper {

    private static final String EATIT_CHANEL_ID ="com.example.dul.foododering.EATit";
    private static final String EATIT_CHANEL_NAME ="EATit";

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannel();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel eatItChannel = new NotificationChannel(EATIT_CHANEL_ID,
       EATIT_CHANEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);
        eatItChannel.enableLights(false);
        eatItChannel.enableVibration(true);
        eatItChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(eatItChannel);
    }

    public NotificationManager getManager() {
        if(manager == null)
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public android.app.Notification.Builder getEatItChannelNotification(String title, String body, PendingIntent contentIntent,
                                                                        Uri soundUri)
    {
        return new android.app.Notification.Builder(getApplicationContext(),EATIT_CHANEL_ID)
                .setContentIntent(contentIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(soundUri)
                .setAutoCancel(false);
    }
}
