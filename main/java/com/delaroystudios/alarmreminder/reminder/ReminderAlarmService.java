package com.delaroystudios.alarmreminder.reminder;

import android.app.AlarmManager;
import android.app.IntentService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;


import androidx.media.app.NotificationCompat;

import com.delaroystudios.alarmreminder.AddReminderActivity;
import com.delaroystudios.alarmreminder.R;
import com.delaroystudios.alarmreminder.data.AlarmReminderContract;

import java.util.Calendar;

/**
 * Created by delaroy on 9/22/17.
 */

    public class ReminderAlarmService extends IntentService{
        private static final String TAG = ReminderAlarmService.class.getSimpleName();
       // PowerManager pms = (PowerManager) getSystemService(Context.POWER_SERVICE);

        private static final int NOTIFICATION_ID = 42;

        Cursor cursor;

        //This is a deep link intent, and needs the task stack
        public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {
            Intent action = new Intent(context, ReminderAlarmService.class);
            action.setData(uri);

            return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        public ReminderAlarmService() {

            super(TAG);
        }


    @Override
        protected void onHandleIntent(Intent intent) {

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Uri uri = intent.getData();


            //Display a notification to view the task details
            Intent action = new Intent(this, AddReminderActivity.class);
            action.setData(uri);
            PendingIntent operation = TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(action)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            //Grab the task description
            if (uri != null) {
                cursor = getContentResolver().query(uri, null, null, null, null);
            }

            String description = "";
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    description = AlarmReminderContract.getColumnString(cursor, AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }





            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

            PowerManager.WakeLock wl = pm.newWakeLock(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,"AppName:tag");
            wl.acquire();

            long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
            Notification note = new Notification.Builder(this)
                    .setContentTitle(getString(R.string.reminder_title))
                    .setContentText(description)
                    .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                    .setContentIntent(operation)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                    .setVibrate(pattern)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .build();

        Uri notification = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alert10);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
            manager.notify(NOTIFICATION_ID, note);


            wl.release();


        }


    }
