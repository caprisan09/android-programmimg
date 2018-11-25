package com.example.hunar_parneet.androidlabs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class TestToolbar extends AppCompatActivity {

    Context ctx = this;
    public ArrayList<String> currentMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        currentMsg = new ArrayList<>();
        currentMsg.add("You selected item 1");

        Toolbar lab8_toolbar = findViewById(R.id.lab08_toolbar);
        setSupportActionBar(lab8_toolbar);
        Button snackButton = findViewById(R.id.snack_Btn);
        snackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(findViewById(R.id.snack_Btn), "Message to show", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu );
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_one:

                Toast.makeText(ctx, "Option one is selected", Toast.LENGTH_LONG).show();
                Snackbar.make(findViewById(R.id.action_one), currentMsg.get(0), Snackbar.LENGTH_LONG).show();
                break;

            case R.id.action_two:
                Toast.makeText(ctx, "Option two is selected", Toast.LENGTH_LONG).show();
                AlertDialog.Builder exit = new AlertDialog.Builder(ctx);
                exit.setMessage(R.string.return_msg).setTitle(R.string.return_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ctx,R.string.exit_app_message, Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .show();
                break;
            case R.id.action_three:

                Toast.makeText(ctx, "Option three is selected", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                final View view = getLayoutInflater().inflate(R.layout.builder_content, null);
                builder.setView(view)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                currentMsg.clear();
                                EditText dialogEditText = view.findViewById(R.id.dialog_editText);
                                currentMsg.add(dialogEditText.getText().toString());
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ctx,R.string.exit_app_message, Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.help:
                AlertDialog.Builder about = new AlertDialog.Builder(ctx);
                about.setMessage(R.string.author_name).setTitle(R.string.app_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ctx,R.string.app_message, Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .show();
                break;


        }

        return true;
    }

}
