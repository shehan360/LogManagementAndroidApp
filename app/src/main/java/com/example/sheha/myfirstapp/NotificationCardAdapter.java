package com.example.sheha.myfirstapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sheha on 12/16/2017.
 */

public class NotificationCardAdapter extends RecyclerView.Adapter<NotificationCardAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;

    public NotificationCardAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder notificationViewHolder, int i) {
        Notification n = notificationList.get(i);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh.mm");
        String date;
        try {
            Date dO=format.parse(n.getDate());
            Date current= Calendar.getInstance().getTime();

            Date today=new Date(current.getYear(),current.getMonth(),current.getDate());

            if(dO.getTime()-today.getTime()>=0){
                date="Today at "+dO.getHours()+":"+dO.getMinutes();
            }
            else {
                date = dO.toString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            date="";
        }
        notificationViewHolder.dateText.setText(date);
        notificationViewHolder.titleText.setText(n.getTitle());
        notificationViewHolder.bodyText.setText(n.getBody());
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_notificationcardview, viewGroup, false);

        return new NotificationViewHolder(itemView);
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        protected TextView titleText;
        protected TextView dateText;
        protected TextView bodyText;

        public NotificationViewHolder(View v){
            super(v);
            titleText=(TextView) v.findViewById(R.id.notificationTitle);
            dateText=(TextView) v.findViewById(R.id.notificationDate);
            bodyText=(TextView) v.findViewById(R.id.notificationBody);
        }
    }
}


