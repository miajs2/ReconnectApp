package com.example.reconnect;

import android.util.Log;

import java.util.ArrayList;

public class Tests {

    public static void runTests(DataManager manager){

        /**
        manager.addPersonRecord("John", "Emeka", "", "", "1 week");
        manager.addInteractionRecord("2019-01-01", "20 minutes", "Face to face", "No notes", "John", "Emeka");
        manager.addInteractionRecord("2017-12-31","50 minutes", "Phone", "Conference call with my uncle and his family", "John", "Emeka");

        manager.addPersonRecord("Barack", "Obama", "", "boss", "1 year");

        manager.addPersonRecord("Tom", "Baker", "", "", "1 week");
        manager.addInteractionRecord("2019-04-26", "30 minutes", "Face to face", "No notes", "Tom", "Baker");

        */

       // manager.addInteractionRecord("2017-12-10","50 minutes", "Phone", "Conference call with my uncle and his family", "John", "Emeka");
        //manager.addInteractionRecord("2019-04-20","50 minutes", "Phone", "Conference call with my uncle and his family", "John", "Emeka");
        //manager.addInteractionRecord("2019-03-18","10 minutes", "Messaging", "Chatting about football", "John", "Emeka");
        manager.addPersonRecord("Peter", "Okolotu", "", "", "2 months");
        manager.addInteractionRecord("2019-04-21", "15 minutes", "Phone", "Talked with friend", "Peter", "Okolotu");
        manager.addInteractionRecord("2019-04-15", "15 minutes", "Phone", "Talked with friend", "Peter", "Okolotu");
        manager.addInteractionRecord("2019-02-07", "15 minutes", "Phone", "Talked with friend", "Peter", "Okolotu");
        manager.addInteractionRecord("2019-04-01", "15 minutes", "Phone", "Talked with friend", "Peter", "Okolotu");
        manager.addInteractionRecord("2019-03-15", "15 minutes", "Phone", "Talked with friend", "Peter", "Okolotu");

        String id = manager.getIDFromName("John", "Emeka");
        Log.i("John", id);
        String id2 = manager.getIDFromName("John", "Emeka");
        Log.i("John", id2);
        String id3 = manager.getIDFromName("Peter", "Okolotu");
        Log.i("Peter", id3);
        String firstName = manager.getNameFromID("27").first_name;
        Log.i("This should be Peter", firstName);



        ArrayList<Communication>  interactions = manager.getAllInteractionsForPerson("Peter", "Okolotu", 30);

        Log.i("Length of list", interactions.size() + "");
        for (Communication c: interactions){
            Log.i("history", c.date + manager.getNameFromID(c.contact_id).first_name);
        }
    }
}
