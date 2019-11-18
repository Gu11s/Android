package com.yourApp.yourPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();

    EditText et_Username, et_Password;
    private static String username, password;
    String loginURL = "YOUR LOGIN URL HERE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        et_Username = findViewById(R.id.et_Username);
        et_Password = findViewById(R.id.et_Password);
    }

    public void openHome(View v) {

        username = et_Username.getText().toString().trim();
        password = et_Password.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            et_Username.setError("Required ");
        } else if (TextUtils.isEmpty(password)) {
            et_Password.setError("Required ");
        } else {

            JSONObject loginBody = new JSONObject();

            try {
                loginBody.put("username", username);
                loginBody.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, loginURL, loginBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt("status") == 1) {
                                    Intent myIntent = new Intent(getBaseContext(), Home.class);
                                    
                                    //if your login return a TOKEN to Authenticate, do the following:
                                    Log.d("Login", "onResponse: TOKEN " + response.getString("token"));
                                    System.out.println("Token: " + response.getString("token"));
                                    myIntent.putExtra("token", response.getString("token"));
                                    myIntent.putExtra("user", response.getString("user"));
                                    startActivity(myIntent);

                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error", error.toString());
                        }
                    });

            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        }
    }
}
