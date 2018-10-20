package com.example.sheha.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class UploadedLogListActivity extends AppCompatActivity {

    String[] logList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_log_list);

        Intent intent=getIntent();
        String list=intent.getStringExtra("todayLogList");

        try {
            JSONArray todayloglist=new JSONArray(list);
            logList=new String[todayloglist.length()];
            for(int i=0;i<todayloglist.length();i++){
                logList[i]=todayloglist.getJSONObject(i).getString("logName");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, logList);

        ListView listView = (ListView) findViewById(R.id.uploadedListView);
            listView.setAdapter(adapter);

    }
}
