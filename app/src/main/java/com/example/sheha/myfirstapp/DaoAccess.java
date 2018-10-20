package com.example.sheha.myfirstapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by sheha on 12/15/2017.
 */
@Dao
public interface DaoAccess {
    @Insert
    void insertNotification(Notification notification);

    @Query("SELECT * FROM Notification ORDER BY notficationNo DESC")
    List<Notification> fetchAllData();

    @Query("DELETE FROM Notification")
    void deleteAll();
}
