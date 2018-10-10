package com.example.hunar_parneet.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        final ChatAdapter messageAdapter = new ChatAdapter(this);

        list = (ListView)findViewById(R.id.viewList);
        list.setAdapter(messageAdapter);

        sendBtn = (Button)findViewById(R.id.sendBtn);
        bottomText = (EditText)findViewById(R.id.bottomText);



        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chatEntry = bottomText.getText().toString();
                chatDump.add(chatEntry);
                bottomText.setText("");
                messageAdapter.notifyDataSetChanged();
            }
        });
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
