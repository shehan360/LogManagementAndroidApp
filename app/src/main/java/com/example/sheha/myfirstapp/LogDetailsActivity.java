package com.example.sheha.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LogDetailsActivity extends AppCompatActivity {
    String list;
    TextView textView1;
    SimpleDateFormat sdf;


    UsersAdapter adapter;
    UsersOtherErrorsAdapter adapter2;

    String[] arr={"timeValidFrom","timeValidTo","dateUploaded","atmId","location","deviceType"};
    String[] arr2={"Time Valid From: ","Time Valid To: ","Date Uploaded: ","ATM Id: ","Location: ","Device Type: "};
    String msg="Log Name: ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_details);

        Intent intent=getIntent();
        list=intent.getStringExtra("logName");
        msg+=list+"\n";
        textView1=findViewById(R.id.textView8);

        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


        ArrayList<LogTransactionError> arrayOfErrors = new ArrayList<LogTransactionError>();
// Create the adapter to convert the array to views
        adapter = new UsersAdapter(this, arrayOfErrors);
// Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.terrorlist);
        listView.setAdapter(adapter);

        ListView listView2=(ListView)findViewById(R.id.oerrorlist);
        ArrayList<LogOtherError> arrayOfOtherErrors = new ArrayList<LogOtherError>();
// Create the adapter to convert the array to views
        adapter2 = new UsersOtherErrorsAdapter(this, arrayOfOtherErrors);
        listView2.setAdapter(adapter2);

        setLogInfo();
        getLogTransactionErrors();
        setOtherErrors();



    }

    public void setOtherErrors(){
        String url = "https://floating-waters-16084.herokuapp.com/logdata/othertechnicalerrors";

        Map<String, String> params = new HashMap();
        params.put("logName", list);


        JSONObject parameters = new JSONObject(params);
        // prepare the Request
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, url,parameters,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {


                        try {
                            for(int i=0;i<response.length();i++){
                                LogOtherError err= new LogOtherError("Error ID: "+response.getJSONObject(i).getJSONObject("terrors").getString("errorId"),
                                        "Description: "+response.getJSONObject(i).getJSONObject("terrors").getString("errorName"),
                                        "Time: "+response.getJSONObject(i).getJSONObject("terrors").getString("time"));
                                adapter2.add(err);
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());

                        textView1.setText("Error!");
                    }
                }
        );


        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }

    public void getLogTransactionErrors(){
        String url = "https://floating-waters-16084.herokuapp.com/logdata/technicaltransactionerrors";

        Map<String, String> params = new HashMap();
        params.put("logName", list);


        JSONObject parameters = new JSONObject(params);
        // prepare the Request
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, url,parameters,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {


                        try {
                            for(int i=0;i<response.length();i++){
                                LogTransactionError err= new LogTransactionError("Error ID: "+response.getJSONObject(i).getJSONObject("transactions").getJSONObject("error").getString("errorId"),
                                        "Description: "+response.getJSONObject(i).getJSONObject("transactions").getJSONObject("error").getString("desc"),
                                        "Time: "+response.getJSONObject(i).getJSONObject("transactions").getString("time"),
                                        "Transaction Type: "+response.getJSONObject(i).getJSONObject("transactions").getString("type"));
                                adapter.add(err);
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());

                        textView1.setText("Error!");
                    }
                }
        );


        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }

    public void setLogInfo(){


        String url = "https://floating-waters-16084.herokuapp.com/logdata/loginfo";

        Map<String, String> params = new HashMap();
        params.put("logName", list);


        JSONObject parameters = new JSONObject(params);
        // prepare the Request
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, url,parameters,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {



                        try {

                            JSONObject logObj=response.getJSONObject(0);

                            for(int i=0;i<arr.length;i++){
                                String tst;

                                if(i>=0&&i<=2){
                                    String dateStr=logObj.getString(arr[i]);
                                    //Log.d("Timee",dateStr);
                                    try {
                                        Date d=sdf.parse(dateStr);
                                        //Log.d("Timee",d.toString());
                                        d.setTime(d.getTime()+19800000);
                                        tst=d.toString();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        tst="Date error";
                                    }
                                }
                                else {
                                    tst = logObj.getString(arr[i]);
                                }
                                //tst = logObj.getString(arr[i]);
                                msg +=arr2[i]+tst+"\n";
                            }


                            textView1.setText(msg);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());

                        textView1.setText("Error!");
                    }
                }
        );


        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(postRequest);


    }

}
