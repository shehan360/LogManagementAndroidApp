package com.example.sheha.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //public static final String EXTRA_MESSAGE="com.example.sheha.myfirstapp.MESSAGE";
    String message;

    Intent intent;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = prefs.edit();

        if(prefs.getBoolean("isLoggedIn",false)){
            intent= new Intent(this,HomeActivity.class);
            Toast.makeText(getApplicationContext(), "Logged In!",
                    Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

    }

    public void login(View view){
        intent= new Intent(this,HomeActivity.class);
        EditText usernameEditText=(EditText)findViewById(R.id.userNameText);
        EditText passwordEditText=(EditText)findViewById(R.id.passwordText);
        String username=usernameEditText.getText().toString();
        String password=passwordEditText.getText().toString();


        String url = "https://floating-waters-16084.herokuapp.com/users/authenticate";
        Map<String, String> params = new HashMap();
        params.put("username", username);
        params.put("password", password);
        JSONObject parameters = new JSONObject(params);
        // prepare the Request
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,parameters,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response

                        try {
                            message=response.get("success").toString();

                            if(message.equals("false")){
                                Toast.makeText(getApplicationContext(), "Invalid Login!",
                                        Toast.LENGTH_SHORT).show();
                                editor.putBoolean("isLoggedIn",false);
                                editor.commit();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Success!",
                                        Toast.LENGTH_SHORT).show();
                                //intent.putExtra(EXTRA_MESSAGE,message);
                                editor.putBoolean("isLoggedIn",true);
                                editor.commit();
                                startActivity(intent);
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
                        message=error.toString();

                    }
                }
        );


        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }
}
