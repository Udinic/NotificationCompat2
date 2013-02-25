package com.jakewharton.notificationcompat2.sample;

import android.app.Application;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 30/08/12
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class NotificationCompat2App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationWidgetService.initNotification(this);
    }
}
