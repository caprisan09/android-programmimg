package com.example.hunar_parneet.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    private EditText mEmailView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //creating reference to login button
        context = this;
        final SharedPreferences sharedPref = getSharedPreferences("MySavedPrefs", Context.MODE_PRIVATE);
        final Button logButton = findViewById(R.id.logBtn);
        logButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = mEmailView.getText().toString();

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("DefaultEmail", email);
                editor.commit();
// Code here executes on main thread after user presses button
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME,"Login button clicked");
            }});

        Log.i(ACTIVITY_NAME, "In onCreate()");

        mEmailView = findViewById(R.id.email);

       mEmailView.setText(sharedPref.getString("DefaultEmail", "none@none.com"));
    }
    @Override
    protected void onResume() {
        super.onResume();

        Log.i(ACTIVITY_NAME, "In onResume()");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
