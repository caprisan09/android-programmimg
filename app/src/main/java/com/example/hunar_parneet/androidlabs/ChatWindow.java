package com.example.hunar_parneet.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    private ListView list;
    private Button sendBtn;
    private EditText bottomText;
    private ArrayList<String> chatDump = new ArrayList<>();
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private ChatDatabaseHelper dbOpener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        final ChatAdapter messageAdapter = new ChatAdapter(this);

        dbOpener = new ChatDatabaseHelper(this); //helper object
        final SQLiteDatabase db = dbOpener.getWritableDatabase();

        list = (ListView)findViewById(R.id.viewList);
        list.setAdapter(messageAdapter);

        sendBtn = (Button)findViewById(R.id.sendBtn);
        bottomText = (EditText)findViewById(R.id.bottomText);



        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chatEntry = bottomText.getText().toString();
                chatDump.add(chatEntry);


                ContentValues newRow = new ContentValues();
                newRow.put("KEY_MESSAGE", bottomText.getText().toString());//all columns have a value
                //ready to insert into database:
                db.insert(ChatDatabaseHelper.TABLE_NAME, "ReplacementValue", newRow);
                messageAdapter.notifyDataSetChanged();
                bottomText.setText("");
            }
        });

        Cursor results = db.query(ChatDatabaseHelper.TABLE_NAME, new String[] {ChatDatabaseHelper.COL_ID, ChatDatabaseHelper.COL_MESSAGE},
                /*"? = ?", new String[] {ChatDatabaseHelper.COL_ID, "2"}*/null, null, null,null, null, null );
results.moveToFirst();
      //  while(!results.isAfterLast())
        //{
            //Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + results.getString(results.getColumnIndex(ChatDatabaseHelper.COL_MESSAGE)));
        Log.i(ACTIVITY_NAME, "Cursor's column count = "+results.getColumnCount());
for(int i = 0; i <results.getColumnCount(); i++)
    Log.i(ACTIVITY_NAME, "Cursor column: = "+results.getColumnName(i));

        for(int i = 0; i < results.getCount(); i++)
        {
            String na = results.getString(results.getColumnIndex(ChatDatabaseHelper.COL_MESSAGE));
chatDump.add(na);
            results.moveToNext();  //read next row
            Log.i(ACTIVITY_NAME, "Message: "+ na);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbOpener.close();
    }

    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx){
            super(ctx,0);
        }

        @Override
        public int getCount() {
            //return super.getCount();

            return chatDump.size();
        }

        @Override
        public String getItem(int position) {
            //return super.getItem(position);
            return chatDump.get(position);
        }

        @Override
        public View getView(int position, View oldView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView) result.findViewById(R.id.message_txt);

            message.setText(getItem(position));
            return result;
        }

        @Override
        public long getItemId(int position) {
            //return super.getItemId(position);
            return position;
        }
    }
}
