package com.in.dsdriver.driver.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.in.dsdriver.R;


public class NotificationHelper {


    private Context mContext;
    public static NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    Intent resultIntent;
    //SessionManager session;
    public static final String NOTIFICATION_CHANNEL_ID = "com.in.dsdriver";


    public NotificationHelper(Context context) {
        mContext = context;
    }

    /**
     * Create and push the notification
     */
    public void createNotification(String title, String message, String user, String activity)
    {

        try {
            //session = new SessionManager(mContext);

            /*if (session.isLogin()) {

                *//**Creates an explicit intent for an Activity in your app**//*
                if (session.getUserType().equalsIgnoreCase("technician")) {
                    resultIntent = new Intent(mContext, TechnicianDashBoard.class);
                    resultIntent.putExtra("intenti", activity);
                    resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } else {
                    resultIntent = new Intent(mContext, CustomerDashBoard.class);
                    resultIntent.putExtra("intenti", activity);
                    resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }*/

                PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                        0 /* Request code */, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
            Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mContext.getPackageName() + "/" + R.raw.notification);
                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
                mBuilder.setSmallIcon(R.drawable.logo);
                mBuilder.setContentTitle(title)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentText(message)
                        .setSound(sound)
                        .setContentIntent(resultPendingIntent);

                mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.BLUE);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert mNotificationManager != null;
                    mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    mNotificationManager.createNotificationChannel(notificationChannel);

                    assert mNotificationManager != null;
                    mNotificationManager.notify(965824521 /* Request Code */, mBuilder.build());

                }
        }catch (Exception e){
            Log.d("nddq", ""+e);
        }

    }


}
