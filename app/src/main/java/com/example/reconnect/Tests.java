package com.example.reconnect;

import android.util.Log;

import java.util.ArrayList;

public class Tests {


    public static void addData(DataManager manager){
        manager.addPersonRecord("John", "Emeka", "", "Uncle", "1 week");
        manager.addPersonRecord("Harry", "Potter", "", "Colleague", "2 weeks");
        manager.addPersonRecord("Barack", "Obama", "", "Friend", "1 year");
        manager.addPersonRecord("Alex", "Baker", "", "Cousin", "1 month");
        manager.addPersonRecord("Sarah", "Adams", "", "High school friend", "3 months");
        manager.addPersonRecord("Joe", "Biden", "", "Friend", "2 weeks");

        manager.addInteractionRecord("2019-04-28", "15 minutes", "Phone", "Talked with friend", "John", "Emeka");
        manager.addInteractionRecord("2019-04-21", "25 minutes", "Messaging", "Chatted on Facebook with Sarah", "Sarah", "Adams");
        manager.addInteractionRecord("2019-04-24", "50 minutes", "Face to face", "Met for lunch", "Alex", "Baker");
        manager.addInteractionRecord("2019-03-15", "5 minutes", "Phone", "Talked with friend", "John", "Emeka");
        manager.addInteractionRecord("2019-03-01", "17 minutes", "Phone", "Talked with uncle about soccer game", "John", "Emeka");
        manager.addInteractionRecord("2019-01-01", "10 minutes", "Phone", "Talked with wizardry", "Harry", "Potter");
    }
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
        manager.addInteractionRecord("2019-04-27", "15 minutes", "Phone", "Talked with friend", "Peter", "Okolotu");

        String id = manager.getIDFromName("John", "Emeka");
        Log.i("John", id);
        String id2 = manager.getIDFromName("John", "Emeka");
        Log.i("John", id2);
        String id3 = manager.getIDFromName("Peter", "Okolotu");
        Log.i("Peter", id3);
        String firstName = manager.getNameFromID("27").first_name;
        Log.i("This should be Peter", firstName);

         Contact peterObj = manager.getNameFromID("27");
         Log.i("eveeu", peterObj + "");
         String lastDate = manager.getAllInteractionsForPerson(peterObj.first_name, peterObj.last_name, 30).get(0).date;
         Log.i("Peter last convo", lastDate );

        //ArrayList<Contact>  friendsToReconnectWith = manager.getContactsToReconnectWith();

        //Log.i("Length of list", friendsToReconnectWith.size() + "");
        //for (Contact c: friendsToReconnectWith){
          //  Log.i("Reconnect_friend",  c.first_name);
        //}
    }
}
