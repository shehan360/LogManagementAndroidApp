package com.example.sheha.myfirstapp;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotificationCardListActivity extends AppCompatActivity {

    RecyclerView recList;
    NotificationDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_card_list);

        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        db= Room.databaseBuilder(getApplicationContext(),NotificationDatabase.class,"sample-db").build();
        new NotificationCardListActivity.DatabaseAsync().execute();

    }

    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Let's add some dummy data to the database.
            /** Notification notf = new Notification();
             notf.setTitle("Test DB");
             notf.setBody("Testing notification 5!");

             //Now access all the methods defined in DaoAccess with sampleDatabase object
             db.daoAccess().insertNotification(notf);

             **/


            List<Notification> t=db.daoAccess().fetchAllData();

            NotificationCardAdapter na= new NotificationCardAdapter(t);
            recList.setAdapter(na);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //To after addition operation here.
        }
    }
}
