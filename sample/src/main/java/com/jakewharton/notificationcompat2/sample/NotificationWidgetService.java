package com.jakewharton.notificationcompat2.sample;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.anydo.R;
import com.anydo.activity.Main;
import com.anydo.activity.SettingsPreferences;
import com.anydo.application.AnydoApp;
import com.anydo.utils.AnydoLog;
import com.jakewharton.notificationcompat2.NotificationCompat2;

import static android.content.Intent.ACTION_RUN;
import static com.anydo.analytics.AnalyticsConstants.CATEGORY_WIDGET_BIG;
import static com.anydo.analytics.AnalyticsConstants.CATEGORY_WIDGET_NOTIFICATION;
import static com.anydo.application.AnydoApp.getAppContext;

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
    private int NOTIFICATION_WIDGET_ID = 83464;

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
        if (ACTION_INIT_NOTIFICATION.equals(intent.getAction())) {
            AnydoLog.d("NotificationWidgetService", "init notification");
            NotificationCompat2.Builder builder = new NotificationCompat2.Builder(this);

            RemoteViews layout = new RemoteViews(this.getPackageName(), R.layout.widget_notification);
//            builder.setContent(layout);

            Intent openApp = new Intent(AnydoApp.getAppContext(), Main.class);
            PendingIntent actionOpenApp = PendingIntent.getActivity(AnydoApp.getAppContext(), 50+hashCode(), openApp, PendingIntent.FLAG_UPDATE_CURRENT);
            layout.setOnClickPendingIntent(R.id.notif_widget_icon,actionOpenApp);

            Intent activeOpenFromBtnAdd = new Intent(getAppContext(), Main.class);
            activeOpenFromBtnAdd.setAction(ACTION_RUN);
            activeOpenFromBtnAdd.putExtra(Main.EXTRA_PERFORM_ON_START, Main.OPEN_KEYBOARD);
            activeOpenFromBtnAdd.putExtra(Main.EXTRA_SOURCE, CATEGORY_WIDGET_NOTIFICATION);
            PendingIntent pendingFromAddBtn = PendingIntent.getActivity(getAppContext(), 51+hashCode(), activeOpenFromBtnAdd, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FLAG_ACTIVITY_NEW_TASK);
            layout.setOnClickPendingIntent(R.id.notif_widget_button_add, pendingFromAddBtn);

            Intent activeOpenFromMicBtn = new Intent(getAppContext(), Main.class);
            activeOpenFromMicBtn.setAction(ACTION_RUN);
            activeOpenFromMicBtn.putExtra(Main.EXTRA_PERFORM_ON_START, Main.START_SPEECH_ON_START);
            activeOpenFromMicBtn.putExtra(Main.EXTRA_SOURCE, CATEGORY_WIDGET_NOTIFICATION);
            PendingIntent pendingFromMicBtn = PendingIntent.getActivity(getAppContext(), 52+hashCode(), activeOpenFromMicBtn, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FLAG_ACTIVITY_NEW_TASK);
            layout.setOnClickPendingIntent(R.id.notif_widget_button_mic, pendingFromMicBtn);

            Intent activeOpenSettings = new Intent(getAppContext(), SettingsPreferences.class);
            activeOpenSettings.setAction(ACTION_RUN);
            activeOpenSettings.putExtra(Main.EXTRA_SOURCE, CATEGORY_WIDGET_NOTIFICATION);
            PendingIntent pendingFromSettingsBtn = PendingIntent.getActivity(getAppContext(), 53+hashCode(), activeOpenSettings, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FLAG_ACTIVITY_NEW_TASK);
            layout.setOnClickPendingIntent(R.id.notif_widget_button_settings, pendingFromSettingsBtn);

            builder.setSmallIcon(R.drawable.icon);
//            builder.setContentTitle("udini");

            // Needed for API < 14?
            Intent bla = new Intent(AnydoApp.getAppContext(), NotificationWidgetService.class);
            bla.setAction(ACTION_CLICK);
            builder.setContentIntent(PendingIntent.getService(AnydoApp.getAppContext(), 55, bla, PendingIntent.FLAG_UPDATE_CURRENT));

            builder.setOngoing(true);
            builder.setAutoCancel(false);
            builder.setContent(layout);
            Notification notificationWidget = builder.build();
            notificationWidget.contentView = layout;
            NotificationManager notManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
            notManager.notify(NOTIFICATION_WIDGET_ID, notificationWidget);
        } else if (ACTION_CLICK.equals(intent.getAction())) {
            Toast.makeText(this, "click!", Toast.LENGTH_SHORT).show();
            AnydoLog.d("NotificationWidgetService", "Click");
        }
    }
}
