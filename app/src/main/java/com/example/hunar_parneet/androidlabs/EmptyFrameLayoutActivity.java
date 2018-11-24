package com.example.hunar_parneet.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class EmptyFrameLayoutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_frame_layout);


        //get bundle back:
        Bundle infoToPass =  getIntent().getExtras(); //this is bundle from line 65, FragmentExample.java

        //repeat from tablet section:


        MessageFragment newFragment = new MessageFragment();
        newFragment.iAmTablet = false;
        newFragment.setArguments( infoToPass ); //give information to bundle

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ftrans = fm.beginTransaction();
        ftrans.replace(R.id.empty_frame, newFragment); //load a fragment into the framelayout
        ftrans.addToBackStack("name doesn't matter"); //changes the back button behaviour
        ftrans.commit(); //actually load it
    }
}
