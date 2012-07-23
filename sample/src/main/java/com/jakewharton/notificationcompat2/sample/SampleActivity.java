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
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import com.jakewharton.notificationcompat2.NotificationReplaceService;
import com.jakewharton.notificationcompat2.NotificationCompat2;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.view.View.OnClickListener;
import static com.jakewharton.notificationcompat2.NotificationReplaceService.ACTION_NOTIFICATION_SWITCH_BUTTONS;
import static com.jakewharton.notificationcompat2.NotificationReplaceService.NOTIFICATION_SWITCH_BUTTONS_ARG_ID;
import static com.jakewharton.notificationcompat2.NotificationReplaceService.NOTIFICATION_SWITCH_BUTTONS_ARG_NOTIFICATION;
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
        findViewById(R.id.actions_extended).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int notifiId = R.id.actions_extended;

                // First - Building the extended notification.
                // This will replace the first one when the "More.." button will be pressed
                Notification notiExtended = getSimple("Actions extended")
                        .addAction(R.drawable.no_icon, "udinic1", getPendingIntent())
                        .addAction(R.drawable.no_icon, "udinic2", getPendingIntent())
                        .addAction(R.drawable.no_icon, "udinic3", getPendingIntent())
                        .build();

                // Creating the switch buttons intent.
                // The receiver will get this and send notiExtended to the NotificationManager, replacing the current one with the same Id
                Intent switchButtonIntent = new Intent();
                switchButtonIntent.setClass(SampleActivity.this, NotificationReplaceService.class);
                switchButtonIntent.setAction(ACTION_NOTIFICATION_SWITCH_BUTTONS);
                switchButtonIntent.putExtra(NOTIFICATION_SWITCH_BUTTONS_ARG_ID, notifiId);
                switchButtonIntent.putExtra(NOTIFICATION_SWITCH_BUTTONS_ARG_NOTIFICATION, notiExtended);
                PendingIntent pIntent = PendingIntent.getService(SampleActivity.this, 0, switchButtonIntent, 0);

                // Building the main notification. The "More.." action is the one to sent the "switch buttons" intent
                Notification notiMain = getSimple("Action with extension")
                        .addAction(android.R.drawable.sym_def_app_icon, "Action", getPendingIntent())
                        .addAction(android.R.drawable.sym_def_app_icon, "More..", pIntent)
                        .build();


                mgr.notify(notifiId, notiMain);
            }
        });
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

}
