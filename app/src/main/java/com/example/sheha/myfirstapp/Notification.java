package com.example.sheha.myfirstapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sheha on 12/15/2017.
 */
@Entity
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int notficationNo;
    private String title;
    private String body;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        /*
        System.out.println(date);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh.mm");
        try {
            Date dO=format.parse(date);
            Date current= Calendar.getInstance().getTime();
            if(current.getDay()==dO.getDay()){
                return "Today attttt "+dO.getHours()+":"+dO.getMinutes();
            }
            return dO.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
        */
        return date;

    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNotficationNo() {
        return notficationNo;
    }

    public void setNotficationNo(int notficationNo) {
        this.notficationNo = notficationNo;
    }
}
