package com.example.hunar_parneet.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        final Button pressButton = findViewById(R.id.button);
        pressButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent,50);
                Log.i(ACTIVITY_NAME,"I am a button is clicked");
            }});
        final Button toolbarButton = findViewById(R.id.test_toolbar);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(StartActivity.this, TestToolbar.class);
                startActivityForResult(intent,57);
                Log.i(ACTIVITY_NAME,"Test toolbar button is clicked");
            }});
        final Button chatButton = findViewById(R.id.chatBtn);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat button");
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });
        final Button weatherButton = findViewById(R.id.weatherBtn);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat button");
                Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==50)&&(resultCode==Activity.RESULT_OK)){
            String messagePassed = data.getStringExtra("Response");
            Context context = getApplicationContext();
            CharSequence text = "Switch is OFF!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, messagePassed, duration);
            toast.show();
            Log.i(ACTIVITY_NAME, "Returned to Startactivity.onActivityResult");
        }
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
