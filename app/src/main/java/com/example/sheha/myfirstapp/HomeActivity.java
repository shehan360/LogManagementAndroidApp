package com.example.sheha.myfirstapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    Date current;
    JSONArray resTodayLogList;
    JSONArray resDownLogList;
    Intent intent;
    Intent intent1;

    SimpleDateFormat df;
    String date;




    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    NotificationDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);
        current= Calendar.getInstance().getTime();
        intent= new Intent(this,UploadedLogListActivity.class);
        intent1=new Intent(this,DownLogListActivity.class);
        df= new SimpleDateFormat(("yyyy-MM-dd"));
        date=df.format(current);
        date="2018-02-15";

        prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = prefs.edit();

        setTextTodayLogList();
        setTextDownLogList();

        db= Room.databaseBuilder(getApplicationContext(),NotificationDatabase.class,"sample-db").build();
        new DatabaseAsync().execute();

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


            //Now access all the methods defined in DaoAccess with sampleDatabase object


            TextView textView1=findViewById(R.id.textView7);
            List<Notification> t=db.daoAccess().fetchAllData();
            //textView1.setText(t.get(0).getTitle()+t.get(0).getBody());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //To after addition operation here.
        }
    }

    public void todayLogListView(View view){
        intent.putExtra("todayLogList",resTodayLogList.toString());
        startActivity(intent);
    }

    public void downLogListView(View view){
        intent1.putExtra("downLogList",resDownLogList.toString());
        startActivity(intent1);
    }

    public void setTextTodayLogList(){

        final TextView textView=findViewById(R.id.textView5);


        String url = "https://floating-waters-16084.herokuapp.com/logdata/lognamesbyuploaddate";
        Map<String, String> params = new HashMap();
        params.put("date",date );

        JSONObject parameters = new JSONObject(params);
        // prepare the Request
        JsonArrayRequest postRequest = new JsonArrayRequest (Request.Method.POST, url,parameters,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        resTodayLogList=response;
                        String msg;
                        msg="Logs uploaded today: "+resTodayLogList.length();
                        //msgList[0]="Logs uploaded today: "+resTodayLogList.length();

                        textView.setText(msg);


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());

                        textView.setText(error.toString());
                    }
                }
        );


        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }

    public void setTextDownLogList(){

        final TextView textView1=findViewById(R.id.textView7);

        String url = "https://floating-waters-16084.herokuapp.com/logdata/downlognames";
        Map<String, String> params = new HashMap();
        params.put("date",date );

        JSONObject parameters = new JSONObject(params);
        // prepare the Request
        JsonArrayRequest postRequest = new JsonArrayRequest (Request.Method.POST, url,parameters,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        resDownLogList=response;
                        String msg="blank";
                        msg="Log files needing attention: "+resDownLogList.length();

                        textView1.setText(msg);


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());

                        textView1.setText(error.toString());
                    }
                }
        );


        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }

    public void logout(View view){
        editor.putBoolean("isLoggedIn",false);
        editor.commit();
        Intent login=new Intent(this,MainActivity.class);
        startActivity(login);
    }

    public void showNotificationsView(View view){
        FirebaseMessaging.getInstance().subscribeToTopic("deepl");
        Intent i=new Intent(this,NotificationCardListActivity.class);
        startActivity(i);
    }
}
