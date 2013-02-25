/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jakewharton.notificationcompat2.sample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.jakewharton.notificationcompat2.NotificationReplaceService;
import com.jakewharton.notificationcompat2.NotificationCompat2;

import static android.content.Intent.ACTION_RUN;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.view.View.OnClickListener;
import static com.jakewharton.notificationcompat2.NotificationReplaceService.ACTION_SWITCH_NOTIFICATIONS;
import static com.jakewharton.notificationcompat2.NotificationReplaceService.SWITCH_NOTIFICATION_ARG_ID;
import static com.jakewharton.notificationcompat2.NotificationReplaceService.SWITCH_NOTIFICATION_ARG_NOTIFICATION;
import static com.jakewharton.notificationcompat2.NotificationCompat2.*;

public class SampleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        findViewById(R.id.simple).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mgr.notify(R.id.simple, getSimple("Simple").build());
            }
        });
        findViewById(R.id.high_priority).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mgr.notify(R.id.high_priority, getSimple("High Priority").setPriority(PRIORITY_HIGH).build());
            }
        });
        findViewById(R.id.low_priority).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mgr.notify(R.id.low_priority, getSimple("Low Priority").setPriority(PRIORITY_LOW).build());
            }
        });
        findViewById(R.id.max_priority).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mgr.notify(R.id.max_priority, getSimple("Max Priority").setPriority(PRIORITY_MAX).build());
            }
        });
        findViewById(R.id.min_priority).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mgr.notify(R.id.min_priority, getSimple("Min Priority").setPriority(PRIORITY_MIN).build());
            }
        });
        findViewById(R.id.picture).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.profile2);
                mgr.notify(R.id.picture, getSimple("Picture Large").setLargeIcon(icon).build());
                mgr.notify(R.id.picture + 1, getSimple("Picture Small").setSmallIcon(R.drawable.profile2).build());
            }
        });
        findViewById(R.id.big_picture).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mgr.notify(R.id.big_picture, new NotificationCompat2.BigPictureStyle(getSimple("Big Picture"))
                        .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.rockaway_sunset))
                        .setBigContentTitle("Courtesy Romain Guy")
                        .setSummaryText("http://curious-creature.org")
                        .build());
            }
        });
        findViewById(R.id.big_picture_api).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mgr.notify(R.id.big_picture_api, getSimple("Big Picture (via API)")
                        .setStyle(new NotificationCompat2.BigPictureStyle()
                                .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.rockaway_sunset))
                                .setBigContentTitle("Courtesy Romain Guy")
                                .setSummaryText("http://curious-creature.org"))
                        .build());
            }
        });
        findViewById(R.id.big_text).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mgr.notify(R.id.big_text, new NotificationCompat2.BigTextStyle(getSimple("Big Text"))
                        .bigText(""
                                + "This is a very long piece of text. "
                                + "This is a very long piece of text. "
                                + "This is a very long piece of text. "
                                + "This is a very long piece of text. "
                                + "This is a very long piece of text. "
                                + "This is a very long piece of text. "
                                + "This is a very long piece of text. ")
                        .build());
            }
        });
        findViewById(R.id.inbox).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mgr.notify(R.id.inbox, new NotificationCompat2.InboxStyle(getSimple("Inbox"))
                        .addLine("Line One")
                        .addLine("Line Two")
                        .addLine("Line Three")
                        .addLine("Line Four")
                        .addLine("Line Five")
                        .build());
            }
        });
        findViewById(R.id.actions).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mgr.notify(R.id.actions, getSimple("Actions")
                        .addAction(android.R.drawable.sym_def_app_icon, "One", getPendingIntent())
                        .addAction(android.R.drawable.sym_def_app_icon, "Two", getPendingIntent())
                        .build());
            }
        });
        findViewById(R.id.progress).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mgr.notify(R.id.progress, getSimple("Progress").setProgress(0, 0, true).build());
            }
        });
        findViewById(R.id.widget).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationWidgetService.initNotification(SampleActivity.this);
            }
        });
        findViewById(R.id.actions_extended).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int notifiId = R.id.actions_extended;

                // First - Building the extended notification.
                // This will replace the first one when the "More.." button will be pressed
                Notification notiReplacement = getSimple("Actions extended")
                        .addAction(R.drawable.no_icon, "udinic1", getPendingIntent())
                        .addAction(R.drawable.no_icon, "udinic2", getPendingIntent())
                        .addAction(R.drawable.no_icon, "udinic3", getPendingIntent())
                        .build();

                // Creating the switch notification intent.
                // The receiver will get this and send notiExtended to the NotificationManager, replacing the current one with the same Id
                Intent switchNotificationIntent = new Intent();
                switchNotificationIntent.setClass(SampleActivity.this, NotificationReplaceService.class);
                switchNotificationIntent.setAction(ACTION_SWITCH_NOTIFICATIONS);
                switchNotificationIntent.putExtra(SWITCH_NOTIFICATION_ARG_ID, notifiId);
                switchNotificationIntent.putExtra(SWITCH_NOTIFICATION_ARG_NOTIFICATION, notiReplacement);
                PendingIntent intentReplaceNotification = PendingIntent.getService(SampleActivity.this, 0, switchNotificationIntent, 0);

                // Building the main notification. The "More.." action is the one to sent the "switch notification" intent
                Notification notiMain = getSimple("Action with extension")
                        .addAction(android.R.drawable.sym_def_app_icon, "Action", getPendingIntent())
                        .addAction(android.R.drawable.sym_def_app_icon, "More..", intentReplaceNotification)
                        .build();


                mgr.notify(notifiId, notiMain);
            }
        });

        handleIntent(getIntent());
    }

    private NotificationCompat2.Builder getSimple(CharSequence title) {
        return new NotificationCompat2.Builder(this)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setTicker("Ticker: " + title)
                .setContentTitle("Title: " + title)
                .setContentText("Content Text")
                .setContentIntent(getPendingIntent());
    }

    private PendingIntent getPendingIntent() {
        Intent i = new Intent(this, SampleActivity.class);
        i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(this, 0, i, 0);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);    //To change body of overridden methods use File | Settings | File Templates.

        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {

        if ("udini1".equals(intent.getAction()))
            Toast.makeText(this, "udini1", Toast.LENGTH_SHORT).show();
        else if ("udini2".equals(intent.getAction()))
            Toast.makeText(this, "udini2", Toast.LENGTH_SHORT).show();
        else if ("udini".equals(intent.getAction()))
            Toast.makeText(this, "udini", Toast.LENGTH_SHORT).show();
    }

    private void initWidget() {
        NotificationCompat2.Builder builder = new NotificationCompat2.Builder(this);

        RemoteViews layout = new RemoteViews(this.getPackageName(), R.layout.widget_notification);
//            builder.setContent(layout);

        Intent openApp = new Intent(this, SampleActivity.class);
        openApp.setAction("udini1");
        PendingIntent actionOpenApp = PendingIntent.getActivity(this, 50+hashCode(), openApp, PendingIntent.FLAG_UPDATE_CURRENT);
        layout.setOnClickPendingIntent(R.id.notif_widget_icon1,actionOpenApp);

        Intent openApp2 = new Intent(this, SampleActivity.class);
        openApp2.setAction("udini1");
        PendingIntent actionOpenApp2 = PendingIntent.getActivity(this, 51+hashCode(), openApp2, PendingIntent.FLAG_UPDATE_CURRENT);
        layout.setOnClickPendingIntent(R.id.notif_widget_icon2, actionOpenApp2);

//        Intent activeOpenFromBtnAdd = new Intent(getAppContext(), Main.class);
//        activeOpenFromBtnAdd.setAction(ACTION_RUN);
//        activeOpenFromBtnAdd.putExtra(Main.EXTRA_PERFORM_ON_START, Main.OPEN_KEYBOARD);
//        activeOpenFromBtnAdd.putExtra(Main.EXTRA_SOURCE, CATEGORY_WIDGET_NOTIFICATION);
//        PendingIntent pendingFromAddBtn = PendingIntent.getActivity(getAppContext(), 51+hashCode(), activeOpenFromBtnAdd, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FLAG_ACTIVITY_NEW_TASK);
//        layout.setOnClickPendingIntent(R.id.notif_widget_button_add, pendingFromAddBtn);
//        builder.setSmallIcon(R.drawable.icon);
//            builder.setContentTitle("udini");

        // Needed for API < 14?
        Intent openAppRegular = new Intent(this, SampleActivity.class);
        openApp2.setAction("udini");
        builder.setContentIntent(PendingIntent.getService(this, 55, openAppRegular, PendingIntent.FLAG_UPDATE_CURRENT));

        builder.setOngoing(true);
        builder.setAutoCancel(false);
        builder.setContent(layout);
        Notification notificationWidget = builder.build();
        notificationWidget.contentView = layout;
        NotificationManager notManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notManager.notify(151, notificationWidget);

}
}
