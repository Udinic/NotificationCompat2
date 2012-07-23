package com.jakewharton.notificationcompat2;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;

/**
 * This is the IntentService in charge of handling the "Switch Notification" action
 *
 * @author Udi Cohen (udinic@gmail.com)
 */
public class NotificationReplaceService extends IntentService {

    public static String ACTION_SWITCH_NOTIFICATIONS =     "com.jakewharton.notificationcompat2.SWITCH_NOTIFICATIONS";
    public static String SWITCH_NOTIFICATION_ARG_ID =           "NOTIF_ID";
    public static String SWITCH_NOTIFICATION_ARG_NOTIFICATION = "NOTIF_NOTIFICATION";

    public NotificationReplaceService() {
        super("NotificationReplaceService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ACTION_SWITCH_NOTIFICATIONS.equals(intent.getAction())) {

            int notifiId = intent.getIntExtra(SWITCH_NOTIFICATION_ARG_ID, -1);
            Notification notification = intent.getParcelableExtra(SWITCH_NOTIFICATION_ARG_NOTIFICATION);

            // Creating the new notification based on the data came from the intent
            NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mgr.notify(notifiId, notification);
        }
    }
}
