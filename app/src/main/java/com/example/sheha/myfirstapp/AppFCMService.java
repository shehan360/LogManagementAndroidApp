package com.example.sheha.myfirstapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sheha on 12/11/2017.
 */

public class AppFCMService extends FirebaseMessagingService {
    private final static String TAG="FCM Message";
    NotificationDatabase db;
    String body;
    String date;
    String title;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


                // Handle message within 10 seconds
                Log.d(TAG, "FCM: " + remoteMessage.getData().get("body"));
            db = Room.databaseBuilder(getApplicationContext(), NotificationDatabase.class, "sample-db").build();

            Date current = Calendar.getInstance().getTime();
            SimpleDateFormat df;
            df = new SimpleDateFormat(("yyyy-MM-dd-hh.mm.ss"));
            date = df.format(current);
            title = remoteMessage.getData().get("title");
            body = remoteMessage.getData().get("body");


            Notification notf = new Notification();
            notf.setBody(body);
            notf.setTitle(title);
            notf.setDate(date);

            db.daoAccess().insertNotification(notf);
            showNotification(remoteMessage);

        }

        }

    private void showNotification(RemoteMessage remoteMessage){


        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                PendingIntent.FLAG_ONE_SHOT);
        Bitmap notifyImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_stat_name);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(notifyImage)
                .setColor(Color.parseColor("#FFE74C3C"))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

        //new AppFCMService.DatabaseAsync().execute();

    }


    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {


            Notification notf=new Notification();
            notf.setBody(body);
            notf.setTitle(title);
            notf.setDate(date);
            db.daoAccess().insertNotification(notf);



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //To after addition operation here.
        }
    }
}

