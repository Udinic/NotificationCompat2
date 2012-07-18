package com.jakewharton.notificationcompat2.sample;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.jakewharton.notificationcompat2.NotificationCompat2;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 19/07/12
 * Time: 01:39
 * To change this template use File | Settings | File Templates.
 */
public class NotificationsHelper {

    public static NotificationCompat2.Builder getSimple(Context ctx, CharSequence title) {
        return new NotificationCompat2.Builder(ctx)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setTicker("Ticker: " + title)
                .setContentTitle("Title: " + title)
                .setContentText("Content Text")
                .setContentIntent(getPendingIntent(ctx));
    }

    public static PendingIntent getPendingIntent(Context ctx) {
        Intent i = new Intent(ctx, SampleActivity.class);
        i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(ctx, 0, i, 0);
    }
}
