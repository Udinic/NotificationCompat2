package com.jakewharton.notificationcompat2.sample;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.jakewharton.notificationcompat2.NotificationCompat2;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;


/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 28/08/12
 * Time: 11:06
 * To change this template use File | Settings | File Templates.
 */
public class NotificationWidgetService extends IntentService {

    public static final String ACTION_INIT_NOTIFICATION = "ACTION_INIT_NOTIFICATION";
    public static final String ACTION_CLICK = "ACTION_CLICK";
    private int NOTIFICATION_WIDGET_ID = 834;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public NotificationWidgetService() {
        super("NotificationWidgetService");
    }

    public static void initNotification(Context ctx) {
        Intent start = new Intent(ctx, NotificationWidgetService.class);
        start.setAction(ACTION_INIT_NOTIFICATION);
        ctx.startService(start);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            if (ACTION_INIT_NOTIFICATION.equals(intent.getAction())) {
                Log.d("NotificationWidgetService", "init notification");
                NotificationCompat2.Builder builder = new NotificationCompat2.Builder(this);

                RemoteViews layout = new RemoteViews(this.getPackageName(), R.layout.widget_notification);
//            builder.setContent(layout);

                Intent openApp = new Intent(this, SampleActivity.class);
                openApp.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                openApp.setAction("udini1");
                PendingIntent actionOpenApp = PendingIntent.getActivity(this, 50 + hashCode(), openApp, PendingIntent.FLAG_UPDATE_CURRENT);
                layout.setOnClickPendingIntent(R.id.notif_widget_icon1, actionOpenApp);

                Intent openApp2 = new Intent(this, SampleActivity.class);
                openApp2.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                openApp2.setAction("udini2");
                PendingIntent actionOpenApp2 = PendingIntent.getActivity(this, 51 + hashCode(), openApp2, PendingIntent.FLAG_UPDATE_CURRENT);
                layout.setOnClickPendingIntent(R.id.notif_widget_icon2, actionOpenApp2);

                builder.setSmallIcon(R.drawable.no_icon);
//            builder.setContentTitle("udini");

                // Needed for API < 14?
                Intent openAppRegular = new Intent(this, SampleActivity.class);
                openAppRegular.setAction("udini");
                openAppRegular.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                builder.setContentIntent(PendingIntent.getActivity(this, 55, openAppRegular, PendingIntent.FLAG_UPDATE_CURRENT));

                builder.setOngoing(true);
                builder.setAutoCancel(false);
                builder.setContent(layout);
                Notification notificationWidget = builder.build();
                notificationWidget.contentView = layout;
                NotificationManager notManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
                notManager.notify(12, notificationWidget);
            } else if (ACTION_CLICK.equals(intent.getAction())) {
                Toast.makeText(this, "click!", Toast.LENGTH_SHORT).show();
                Log.d("NotificationWidgetService", "Click");
            }
        } catch (Throwable t) {
            Log.e("udini", t.getMessage(), t);
        }
    }
}
