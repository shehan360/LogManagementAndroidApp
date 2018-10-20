package com.example.sheha.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class DownLogListActivity extends AppCompatActivity {

    String[] mobileArray;
    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_log_list);

        Intent intent=getIntent();
        String list=intent.getStringExtra("downLogList");

        intent1=new Intent(this,LogDetailsActivity.class);

        try {
            JSONArray downloglist=new JSONArray(list);
            mobileArray=new String[downloglist.length()];
            for(int i=0;i<downloglist.length();i++){
                mobileArray[i]=downloglist.getJSONObject(i).getString("logName");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, mobileArray);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                intent1.putExtra("logName",mobileArray[position]);
                startActivity(intent1);
            }
        });

    }
}
