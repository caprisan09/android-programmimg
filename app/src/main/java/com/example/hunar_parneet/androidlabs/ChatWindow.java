package com.example.hunar_parneet.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends Activity {

    private ListView list;
    private Button sendBtn;
    private EditText bottomText;
    private ArrayList<String> chatDump = new ArrayList<>();
    private ArrayList<Long> chatID = new ArrayList<>();
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private ChatDatabaseHelper dbOpener;
    private boolean ifFrameExist;
    private Cursor result;
    private SQLiteDatabase db;
    private ChatAdapter messageAdapter;
    private long databaseID;
    private long clickedPosition;
    MessageFragment newFragment;
    FragmentManager fm;
    FragmentTransaction ftrans;
    MessageResult messageAndID;

    private List<MessageResult> chatList=null;

    public ChatWindow(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        messageAdapter = new ChatAdapter(this);
        chatList = new ArrayList<>();
        dbOpener = new ChatDatabaseHelper(this); //helper object
        db = dbOpener.getWritableDatabase();

        list = findViewById(R.id.viewList);
        list.setAdapter(messageAdapter);
        FrameLayout fLayout = findViewById(R.id.frame_layout);
        ifFrameExist = fLayout != null;
        sendBtn = findViewById(R.id.sendBtn);
        bottomText = findViewById(R.id.bottomText);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                clickedPosition = position;
                Bundle infoToPass = new Bundle();
                Log.i(ACTIVITY_NAME, "List item was clicked");
                infoToPass.putString("Message", messageAdapter.getItem(position).getMsg());
                infoToPass.putLong("ID", messageAdapter.getItemId(position));

                if(ifFrameExist) {
                    Log.i(ACTIVITY_NAME, "I am a tablet");
                    // copy this section in EmptyFragmentWindow
                    newFragment = new MessageFragment();

                    newFragment.iAmTablet = true;

                    newFragment.setArguments( infoToPass ); //give information to bundle
                    fm = getFragmentManager();
                    ftrans = fm.beginTransaction();
                    ftrans.replace(R.id.frame_layout, newFragment); //load a fragment into the framelayout
                    ftrans.addToBackStack("name doesn't matter"); //changes the back button behaviour
                    ftrans.commit(); //actually load it

                    //end of section to copy
                }
                else //on a phone
                {
                    Log.i(ACTIVITY_NAME, "I am on phone");
                    //go to new window:
                    Intent nextPage = new Intent(ChatWindow.this, EmptyFrameLayoutActivity.class);
                    nextPage.putExtras(infoToPass); //send info
                    startActivityForResult(nextPage, 67);
                }

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chatEntry = bottomText.getText().toString();
                ContentValues newRow = new ContentValues();
                newRow.put("KEY_MESSAGE", bottomText.getText().toString());//all columns have a value
                //ready to insert into database:
                long ID = db.insert(ChatDatabaseHelper.TABLE_NAME, "ReplacementValue", newRow);
                messageAndID = new MessageResult(ID, chatEntry);
                chatList.add(messageAndID);
                result = db.query(ChatDatabaseHelper.TABLE_NAME, new String[] {ChatDatabaseHelper.COL_ID, ChatDatabaseHelper.COL_MESSAGE},
                        null, null, null,null, null, null );


                messageAdapter.notifyDataSetChanged();
                bottomText.setText("");
            }
        });

     runQuery();

    }

    public void runQuery()
    {
        result = db.query(ChatDatabaseHelper.TABLE_NAME, new String[] {ChatDatabaseHelper.COL_ID, ChatDatabaseHelper.COL_MESSAGE},
                null, null, null,null, null, null );
        if(result!=null){
        result.moveToFirst();

        Log.i(ACTIVITY_NAME, "Cursor's column count = "+result.getColumnCount());
        for(int i = 0; i <result.getColumnCount(); i++)
            Log.i(ACTIVITY_NAME, "Cursor column: = "+result.getColumnName(i));

        for(int i = 0; i < result.getCount(); i++)
        {
            String na = result.getString(result.getColumnIndex(ChatDatabaseHelper.COL_MESSAGE));
            databaseID = result.getLong(result.getColumnIndex(ChatDatabaseHelper.COL_ID));

            messageAndID = new MessageResult(databaseID, na);
            chatList.add(messageAndID);
            result.moveToNext();  //read next row
            Log.i(ACTIVITY_NAME, "Column ID: "+ databaseID);
            Log.i(ACTIVITY_NAME, "Message: "+ na);
        }}}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK){
            Log.i(ACTIVITY_NAME, "Request for deleting the message with ID"+ data.getExtras().getLong("ID"));
            deleteMessage(data.getExtras().getLong("ID"));

        }
    }

    public void deleteMessage(long id){

        /*Delete the selected row*/
        db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.COL_ID +" = ?",  new String[] {Long.toString(id) } );

        Log.i(ACTIVITY_NAME, "I am in the deleteMessage function");
        /*update your array list for both message and database id*/
        /*chatID.remove(id);
        chatDump.remove(id);*/
        //chatDump.remove(id);
        //chatID.remove(id);
        /*run a new database query and get a new Cursor object*/
        //result = db.query(ChatDatabaseHelper.TABLE_NAME, new String[] {ChatDatabaseHelper.COL_ID, ChatDatabaseHelper.COL_MESSAGE},
//                null, null, null,null, null, null );
        /*update the list view*/
        //chatDump.clear();
        chatList.clear();
        runQuery();
        messageAdapter.notifyDataSetChanged();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbOpener.close();
    }

    private class ChatAdapter extends ArrayAdapter<MessageResult>{

        public ChatAdapter(Context ctx){
            super(ctx,0);
        }


        @Override
        public int getCount() {
            //return super.getCount();

            //return chatDump.size();
            return chatList.size();
        }

        @Override
        public MessageResult getItem(int position) {
            //return super.getItem(position);
            return chatList.get(position);
        }

        @Override
        public View getView(int position, View oldView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = result.findViewById(R.id.message_txt);

            message.setText(getItem(position).getMsg());
            return result;
        }

        @Override
        public long getItemId(int position) {

            long local;
            result.moveToPosition(position);
            local = result.getLong(result.getColumnIndex(ChatDatabaseHelper.COL_ID));
            Log.i(ACTIVITY_NAME, ""+local);
            return local;
        }
    }
}
