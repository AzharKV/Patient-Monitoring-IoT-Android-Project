package com.delaroystudios.alarmreminder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.okhttp.internal.framed.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    AppCompatImageView imgout, remind;
    TextView hbeat, humidity, temp;
    AlertDialog.Builder builder;


    private NotificationManager mNotificationManager;
    private static String DEFAULT_CHANNEL_ID = "default_channel";
    private static String DEFAULT_CHANNEL_NAME = "Default";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgout = findViewById(R.id.imgout);
        remind = findViewById(R.id.remind);
        hbeat = findViewById(R.id.hbeat);
        humidity = findViewById(R.id.humidity);
        temp = findViewById(R.id.temp);

        builder = new AlertDialog.Builder(this);


        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {

                            apicall();
                            // PerformBackgroundTask this class is the class that extends AsynchTask


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000); //execute in every 50000 ms


        final Handler handler1 = new Handler();
        Timer timer1 = new Timer();
        TimerTask doAsynchronousTasks = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {

                            int hbeats = Integer.parseInt(hbeat.getText().toString());
                            double tempr = Double.parseDouble(temp.getText().toString());

                            if (hbeats > 100) {
                                try {
                                    Uri notification = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alert5);
                                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                    r.play();

                                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    AlertDialog alert = builder.create();
                                    alert.setTitle(" Notification!" + " HIGH Blood Pressure!");
                                    alert.show();

                                    final Context context = null;
                                    final Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                                    long[] pattern = {0, 1000, 1000};
                                    v.vibrate(pattern, 0);

                                    //Toast.makeText(HomeActivity.this,"Your HearBeat is High", LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else if (hbeats < 60) {
                                try {
                                    Uri notification = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alert5);
                                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                    r.play();

                                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    AlertDialog alert = builder.create();
                                    alert.setTitle(" Notification!" + " LOW Blood Pressure!");
                                    alert.show();

                                    final Context context = null;
                                    final Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                                    long[] pattern = {0, 1000, 1000};
                                    v.vibrate(pattern, 0);

                                    // Toast.makeText(HomeActivity.this,"Your HeartBeat is low", LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                //2.Build Notification with NotificationCompat.Builder
                                //   mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                //2.Build Notification with NotificationCompat.Builder
//                                Notification notification = new NotificationCompat.Builder(this,DEFAULT_CHANNEL_ID)
//                                        .setContentTitle("Simple Notification")   //Set the title of Notification
//                                        .setContentText("I am a boring notification.")    //Set the text for notification
//                                        .setSmallIcon(R.drawable.ic_not).build();
//
//                                //Send the notification.
//                                mNotificationManager.notify(1, notification);
                                //  NotificationCompat.Builder builder=new NotificationCompat.Builder(this,DEFAULT_CHANNEL_ID).setSmallIcon(R.drawable.ic_not).setContentTitle("Your Heat Beat is low").setContent("Your HeartBeat is Low").setPriority(NotificationCompat.PRIORITY_DEFAULT);

                            }
                            if (tempr < 30.0) {
                                try {
                                    Uri notification = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alert5);
                                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                    r.play();

                                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    AlertDialog alert = builder.create();
                                    alert.setTitle(" Notification!" + " LOW TEMPEARTURE!");
                                    alert.show();

                                    final Context context = null;
                                    final Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                                    long[] pattern = {0, 1000, 1000};
                                    v.vibrate(pattern, 0);


                                    //Setting the title manually


                                    //    makeText(HomeActivity.this,"Low Temprature", LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else if (tempr > 35.0) {
                                try {
                                    Uri notification = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alert5);
                                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                    r.play();

                                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    AlertDialog alert = builder.create();
                                    alert.setTitle(" Notification!" + " HIGH TEMPEARTURE!");
                                    alert.show();

                                    final Context context = null;
                                    final Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                                    long[] pattern = {0, 1000, 1000};
                                    v.vibrate(pattern, 0);

                                    //  makeText(HomeActivity.this,"High Temperature", LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }


                            // PerformBackgroundTask this class is the class that extends AsynchTask


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTasks, 0, 10000);


        final Handler handler2 = new Handler();
        Timer timer2 = new Timer();
        TimerTask doAsynchronousTaskss = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {

                            int hbeats = Integer.parseInt(hbeat.getText().toString());


                            //2.Build Notification with NotificationCompat.Builder
                            //   mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                            //2.Build Notification with NotificationCompat.Builder
//                                Notification notification = new NotificationCompat.Builder(this,DEFAULT_CHANNEL_ID)
//                                        .setContentTitle("Simple Notification")   //Set the title of Notification
//                                        .setContentText("I am a boring notification.")    //Set the text for notification
//                                        .setSmallIcon(R.drawable.ic_not).build();
//
//                                //Send the notification.
//                                mNotificationManager.notify(1, notification);
                            //  NotificationCompat.Builder builder=new NotificationCompat.Builder(this,DEFAULT_CHANNEL_ID).setSmallIcon(R.drawable.ic_not).setContentTitle("Your Heat Beat is low").setContent("Your HeartBeat is Low").setPriority(NotificationCompat.PRIORITY_DEFAULT);

                            if (hbeats <= 60) {
                                try {
                                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                    r.play();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                AlertDialog alert = builder.create();
                                alert.setTitle(" Notification!                                                                                     " + " Low Blood Pressure!");
                                alert.show();

                                //  makeText(HomeActivity.this,"Your Heartbeat is Low", LENGTH_LONG).show();
                            } else if (hbeats >= 100) {
                                try {
                                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                    r.play();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                AlertDialog alert = builder.create();
                                alert.setTitle(" Notification!                                                                                     " + "High Blood Pressure!");
                                alert.show();

                                // makeText(HomeActivity.this,"Your Heartbeat is High", LENGTH_LONG).show();
                            }


                            // PerformBackgroundTask this class is the class that extends AsynchTask


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTaskss, 0, 50000);


        remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rem = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(rem);
            }
        });


        imgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Life Care");
                builder.setMessage("Do you want to Logout? ");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new HealthPreference(HomeActivity.this).logoutUser();
                        Intent in = new Intent(HomeActivity.this, LoginActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(in);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();

            }
        });
    }


    public static void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Create channel only if it is not already created
            if (notificationManager.getNotificationChannel(DEFAULT_CHANNEL_ID) == null) {
                notificationManager.createNotificationChannel(new NotificationChannel(
                        DEFAULT_CHANNEL_ID, DEFAULT_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                ));

//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    NotificationChannel notificationChannel = new NotificationChannel(channel_id , channel_name, NotificationManager.IMPORTANCE_HIGH);
//                    notificationChannel.enableLights(true);
//                    notificationChannel.enableVibration(true);
//                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//                    NotificationManager notificationManagers = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                    notificationManager.createNotificationChannel(notificationChannel);
//                }

            }
        }
    }

    public void apicall() {
        String targetURL = "https://api.thingspeak.com/channels/890485/feeds.json?api_key=Z1D4AU3S6ANADIBY&results=10";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(targetURL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                // String   place= extras.getString("place");
                Log.e("error", responseBody.toString());
                try {


                    String data = new String(responseBody);
                    JSONObject parentObject = new JSONObject(new String(responseBody));
                    JSONArray userDetails = parentObject.getJSONArray("feeds");

                    for (int i = 0; i < userDetails.length(); i++) {
                        try {
                            JSONObject oneObject = userDetails.getJSONObject(i);
                            // Pulling items from the array
                            String oneObjectsItem = oneObject.getString("field1");
                            String oneObjectsItem2 = oneObject.getString("field2");
                            String oneObjectsItem3 = oneObject.getString("field3");
                            hbeat.setText(oneObjectsItem);
                            temp.setText(oneObjectsItem3);
                            humidity.setText(oneObjectsItem2);


                        } catch (JSONException e) {

                            // Oops
                        }

                    }

                    // String x = userDetails.getJSONArray("field1").toString();
                    //  Log.e("works",user.toString());

                    //


                } catch (Exception e) {
                    Log.e("error", e.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }});}}


