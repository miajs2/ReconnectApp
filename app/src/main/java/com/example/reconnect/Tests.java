package com.example.reconnect;

import android.util.Log;

import java.util.ArrayList;

public class Tests {

    public static void runTests(DataManager manager){
        manager.addPersonRecord("John", "Emeka", "", "", "1 week");
        manager.addInteractionRecord("2019-01-01", "20 minutes", "Face to face", "No notes", "John", "Emeka");
        manager.addInteractionRecord("2017-12-31","50 minutes", "Phone", "Conference call with my uncle and his family", "John", "Emeka");

        manager.addPersonRecord("Barack", "Obama", "", "boss", "1 year");

        manager.addPersonRecord("Tom", "Baker", "", "", "1 week");
        manager.addInteractionRecord("2019-04-26", "30 minutes", "Face to face", "No notes", "Tom", "Baker");


        ArrayList<Contact> getInTouchWith = manager.getReminders();
        for (Contact c: getInTouchWith){
            Log.i(c.first_name, "Get in touch with them!");
        }
    }
}
