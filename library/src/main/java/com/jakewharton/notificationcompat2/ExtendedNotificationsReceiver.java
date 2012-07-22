package com.jakewharton.notificationcompat2;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * This is the receiver that's in charge of receiving "Switch buttons" action.
 *
 * @author Udi Cohen (udinic@gmail.com)
 *
 */
public class ExtendedNotificationsReceiver extends BroadcastReceiver {

    public static String ACTION_NOTIFICATION_SWITCH_BUTTONS =           "com.jakewharton.notificationcompat2.SWITCH_BUTTONS";
    public static String NOTIFICATION_SWITCH_BUTTONS_ARG_ID =           "NOTIF_ID";
    public static String NOTIFICATION_SWITCH_BUTTONS_ARG_NOTIFICATION = "NOTIF_NOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_NOTIFICATION_SWITCH_BUTTONS.equals(intent.getAction())) {

            int notifiId = intent.getIntExtra(NOTIFICATION_SWITCH_BUTTONS_ARG_ID, -1);
            Notification notification = intent.getParcelableExtra(NOTIFICATION_SWITCH_BUTTONS_ARG_NOTIFICATION);

            // Creating the new notification based on the data came from the intent
            NotificationManager mgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            mgr.notify(notifiId, notification);
        }
    }
}
