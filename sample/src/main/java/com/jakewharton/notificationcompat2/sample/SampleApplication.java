package com.jakewharton.notificationcompat2.sample;

import android.app.Application;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.util.Log;
import com.jakewharton.notificationcompat2.NotificationAction;
import com.jakewharton.notificationcompat2.NotificationCompat2;

/**
 * This Application class is used to hold the receiver for the "SWITCH_BUTTONS" intent.
 *
 * Since this receiver needs to be alive even after the main activity was
 * destroyed - we use Application.
 *
 * @author Udi Cohen (udinic@gmail.com)
 *
 */
public class SampleApplication extends Application {

    public static String ACTION_NOTIFICATION_SWITCH_BUTTONS =       "SWITCH_BUTTONS";
    public static String NOTIFICATION_SWITCH_BUTTONS_ARG_ID =       "NOTIFI_ID";
    public static String NOTIFICATION_SWITCH_BUTTONS_ARG_ACTIONS =  "NOTIFI_ACTIONS";
    public static String NOTIFICATION_SWITCH_BUTTONS_ARG_TITLE =    "NOTIFI_TITLE";

    @Override
    public void onCreate() {
        super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.

        BroadcastReceiver bla = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (ACTION_NOTIFICATION_SWITCH_BUTTONS.equals(intent.getAction())) {

                    int notifiId = intent.getIntExtra(NOTIFICATION_SWITCH_BUTTONS_ARG_ID, -1);
                    String notifiTitle = intent.getStringExtra(NOTIFICATION_SWITCH_BUTTONS_ARG_TITLE);
                    Parcelable actions[] = intent.getParcelableArrayExtra(NOTIFICATION_SWITCH_BUTTONS_ARG_ACTIONS);

                    // Creating the new notification based on the data came from the intenr
                    NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    NotificationCompat2.Builder builder = NotificationsHelper.getSimple(SampleApplication.this, notifiTitle);

                    for (Parcelable action : actions) {
                        NotificationAction notificationAction = (NotificationAction) action;
                        builder.addAction(notificationAction.getIcon(), notificationAction.getTitle(), notificationAction.getIntent());
                    }

                    mgr.notify(notifiId, builder.build());
                }
            }
        };

        registerReceiver(bla, new IntentFilter(ACTION_NOTIFICATION_SWITCH_BUTTONS));
    }
}
