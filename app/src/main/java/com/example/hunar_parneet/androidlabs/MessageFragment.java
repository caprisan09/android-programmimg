package com.example.hunar_parneet.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {
    private final String ACTIVITY_NAME = "MessageFragment";
    private TextView messageText;
    private TextView idText;
    private Button deleteBtn;
    public MessageFragment(){}
    ChatWindow chatWindow;
    public boolean iAmTablet;
    public EmptyFrameLayoutActivity parent;


    public void setIsTablet(Boolean b){
        iAmTablet=b;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle infoToPass = getArguments(); //returns the arguments set before

        String passedMessage = infoToPass.getString("Message");
        final long idPassed = infoToPass.getLong("ID");
        Log.i(ACTIVITY_NAME, "Message: "+passedMessage);
        Log.i(ACTIVITY_NAME, "ID: "+idPassed);

        final View screen = inflater.inflate(R.layout.activity_message_details, container, false);
        messageText = screen.findViewById(R.id.frame_message);
        idText = screen.findViewById(R.id.frame_id);
        deleteBtn = screen.findViewById(R.id.frame_button);

        Log.i(ACTIVITY_NAME, "I am in the message detail activity");

        messageText.setText(passedMessage);
        idText.setText(""+idPassed);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "Delete button on fragment is clicked");


                if (iAmTablet) {
                    chatWindow.deleteMessage(idPassed);
                    getActivity().getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();


                }
                else {
                    Log.i(ACTIVITY_NAME, "Delete the message button clicked");
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("ID", idPassed);
                    getActivity().setResult(Activity.RESULT_OK, resultIntent);
                    getActivity().finish();
                }
            }
        });
        return screen;

    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        if(iAmTablet)
            chatWindow = (ChatWindow) context;
    }
}
