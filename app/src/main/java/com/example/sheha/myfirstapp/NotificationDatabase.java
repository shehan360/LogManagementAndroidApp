package com.example.sheha.myfirstapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by sheha on 12/15/2017.
 */
@Database(entities = {Notification.class}, version = 1)
public abstract class NotificationDatabase extends RoomDatabase{
    public abstract DaoAccess daoAccess();
}
