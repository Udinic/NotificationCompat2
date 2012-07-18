package com.jakewharton.notificationcompat2;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 17/07/12
 * Time: 00:56
 * To change this template use File | Settings | File Templates.
 */
public class NotificationAction implements Parcelable {

    private int icon;
    private String title;
    private PendingIntent intent;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getIcon());
        dest.writeString(getTitle());
        dest.writeParcelable(getIntent(),0);
    }

    public static final Creator<NotificationAction> CREATOR
            = new Creator<NotificationAction>() {
        public NotificationAction createFromParcel(Parcel in) {
            return new NotificationAction(in);
        }

        public NotificationAction[] newArray(int size) {
            return new NotificationAction[size];
        }
    };

    public NotificationAction(int icon, String title, PendingIntent intent) {
        this.icon = icon;
        this.title = title;
        this.intent = intent;
    }

    private NotificationAction(Parcel in) {
        setIcon(in.readInt());
        setTitle(in.readString());
        setIntent((PendingIntent)in.readParcelable(null));
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PendingIntent getIntent() {
        return intent;
    }

    public void setIntent(PendingIntent intent) {
        this.intent = intent;
    }
}
