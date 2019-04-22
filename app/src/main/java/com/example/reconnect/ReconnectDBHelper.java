package com.example.reconnect;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Additional functionality for managing database provided in this class.
 */
public class ReconnectDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Reconnect.db";
    public ReconnectDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL(ReconnectContract.createPersonTable());
         db.execSQL(ReconnectContract.createInteractionTable());
    }

    @Override
    /**
     * TODO: Handle changes when app is revised.
     * Do nothing yet.
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //No change needed yet,.

    }
}
