package com.example.hunar_parneet.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    protected static final String ACTIVITY_NAME = "ChatDatabaseHelper";

    private static String DATABASE_NAME = "Messages.db";
    private static int VERSION_NUM = 2;
    public static final String TABLE_NAME = "INFO";
    public static final String COL_MESSAGE = "KEY_MESSAGE";
    public static final String COL_ID = "KEY_ID";
    //Context ctx;

    public ChatDatabaseHelper(Context ctx){

        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " ( " + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +COL_MESSAGE+" String);" );
        Log.i(ACTIVITY_NAME, "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME); //delete current table
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onCreate, oldVersion = "+oldVersion+"newVersion = "+newVersion);
    }
}
