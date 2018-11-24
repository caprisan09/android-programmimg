package com.example.hunar_parneet.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MessageDetails extends Activity {

    private final String ACTIVITY_NAME = "MessageDetails";
    private TextView messageText;
    private TextView idText;
    private Button deleteBtn;
    ChatWindow chatWindow;
    private long idPassed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);


        messageText = findViewById(R.id.frame_message);
        idText = findViewById(R.id.frame_id);
        deleteBtn = findViewById(R.id.frame_button);
        idPassed = getIntent().getExtras().getLong("ID");

        Log.i(ACTIVITY_NAME, "I am in the message detail activity");

        messageText.setText(getIntent().getExtras().getString("Message"));
        idText.setText("" + getIntent().getExtras().getLong("ID"));

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*chatWindow.deleteMessage(idPassed);*/
                Log.i(ACTIVITY_NAME, "Delete the message button clicked");
                Intent resultIntent = new Intent();
                resultIntent.putExtra("ID", idPassed);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        });

    }
}
