package com.example.reconnect;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.util.Log;

import java.util.Calendar;


//Notes for this class: Handles creation of the database
//Also provides functionality  for adding data into the database.
//Methods for data processing are static, so can be used with other activities.

//TODO: Need to add methods to allow for efficient retrieval of data from database (e.g. filtering based on date, filtering based by person etc.)
//TODO: Create more helper methods: one method needed for retrieving person object.
//TODO: Return list of all connections, where each entry is an individual person.
//Get method to retrieve id given person's first and last name
//Get method to retrieve a person's  id
//Get last n interactions (return as an arraylist of persons).
//Contact and Communication table.


//Names: Alex Baker, John Joes, Mary Smith, Sarah Adams.
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private ActionBar toolbar;

    // Hardcoded data to test format
    String[] names = new String[]{
            "Alex Baker", "John Jones", "Mary Smith", "Sarah Adams",
    };


    int[] avatars = new int[]{
            R.drawable.alex, R.drawable.john, R.drawable.mary, R.drawable.sarah,
    };

    String[] lastConnected = new String[]{
            "Last Connected: 3 weeks ago", "Last Connected: 5 months ago", "Last Connected: 2 days ago", "Last Connected: 7 weeks ago",
    };

    protected  ReconnectDBHelper helper; //should be initialized in main class

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()) {
                case R.id.navigation_contacts:
                    return true;
                case R.id.navigation_summary:
                    startActivity(new Intent(MainActivity.this, Summary.class));
                    return true;
                case R.id.navigation_network:
                    startActivity(new Intent(MainActivity.this, GraphView.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FloatingActionButton fab = findViewById(R.id.addContact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddContact.class));
            }
        });

        List<HashMap<String, String>> aList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("name", names[i]);
            hm.put("last_connected", lastConnected[i]);
            hm.put("avatars", Integer.toString(avatars[i]));
            aList.add(hm);
        }

        String[] from = {"name", "last_connected", "avatars"};
        int[] to = {R.id.name, R.id.date, R.id.avatar};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.contact_home_screen, from, to);
        ListView androidListView = (ListView) findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);
        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3) {
                Intent n = new Intent(getApplicationContext(), Timeline.class);
                n.putExtra("position", position);
                startActivity(n);
            }
        });

        helper = new ReconnectDBHelper(this);
        tableToString(helper.getReadableDatabase(), ReconnectContract.Person.TABLE_NAME);
        tableToString(helper.getReadableDatabase(), ReconnectContract.Interaction.TABLE_NAME);

       // Log.i("Main acrivity class", ReconnectContract.createPersonTable());
        //Log.i("Main activity class", ReconnectContract.createInteractionTable());
    }

    /**
     *Add a single person's record to the person table.
     * @param firstName
     * @param lastName
     * @param pictureLoc
     * @param freq_contact
     * @param db: need to pass in database in write mode (ie. mydatabase.getWritableDatabase())
     * @return
     */
    public static boolean addPersonRecord(String firstName, String lastName, String pictureLoc, String relationship,  String freq_contact, SQLiteDatabase db){

      //Map of values where column names are used as keys.
       ContentValues values = new ContentValues();
       values.put(ReconnectContract.Person.FIRST_NAME, firstName);
       values.put(ReconnectContract.Person.LAST_NAME, lastName);
       values.put(ReconnectContract.Person.PIC_LOCATION, pictureLoc);
       values.put(ReconnectContract.Person.CONTACT_RELATIONSHIP, relationship);
       values.put(ReconnectContract.Person.CONTACT_FREQUENCY, freq_contact);


       try{

          db.insert(ReconnectContract.Person.TABLE_NAME, null, values);
          return true;


       }catch(Exception e){
           Log.i("Mainactivity class", "Adding to person table failed");
       }
       return false;
    }


    /**
     *Add a single person's record to the person table.
     * @param
     * @param
     * @param
     * @param contact_id: add interaction record using the contact_id to tie it to specific person.
     *
     *
     * @return
     */
    public  boolean addInteractionRecord(String date, String duration, String type, String notes, String contact_id){
       SQLiteDatabase db = helper.getWritableDatabase();

      ContentValues values  = new ContentValues();
      values.put(ReconnectContract.Interaction.DATE, date);
      values.put(ReconnectContract.Interaction.DURATION, duration);
      values.put(ReconnectContract.Interaction.NOTES, notes);
      values.put(ReconnectContract.Interaction.TYPE, type);
      values.put(ReconnectContract.Interaction.CONTACT_ID, contact_id);

      try{
            db.insert(ReconnectContract.Interaction.TABLE_NAME, null, values);
            return true;

      }catch(Exception e){
          Log.i("Mainactivity class", "Adding to interactions table failed");
      }
      return false;
    }


    //Return an arraylist of contacts, where each entry is a person.
    public ArrayList<Contact> getContacts(){
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                ReconnectContract.Person._ID,
                ReconnectContract.Person.FIRST_NAME,
                ReconnectContract.Person.LAST_NAME,
                ReconnectContract.Person.PIC_LOCATION,
                ReconnectContract.Person.CONTACT_RELATIONSHIP
        };

        String sortOrder = ReconnectContract.Person.LAST_NAME  + " DESC";
        Cursor cursor = db.query(
                ReconnectContract.Person.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        ArrayList<Contact> friends = new ArrayList<>();
        while(cursor.moveToNext()){
         int indexIDColumn = cursor.getColumnIndexOrThrow(ReconnectContract.Person._ID);
         int firstNameColumn = cursor.getColumnIndexOrThrow(ReconnectContract.Person.FIRST_NAME);
         int lastNameColumn = cursor.getColumnIndexOrThrow(ReconnectContract.Person.LAST_NAME);
         int picLocationColumn = cursor.getColumnIndexOrThrow(ReconnectContract.Person.PIC_LOCATION);
         int relationshipColumn = cursor.getColumnIndexOrThrow(ReconnectContract.Person.CONTACT_RELATIONSHIP);

         Contact myFriend = new Contact();
         myFriend.id = cursor.getString(indexIDColumn);
         myFriend.first_name = cursor.getString(firstNameColumn);
         myFriend.last_name = cursor.getString(lastNameColumn);
         myFriend.pic_location = cursor.getString(picLocationColumn);
         myFriend.contact_relationship = cursor.getString(relationshipColumn);
         friends.add(myFriend);
        }

        cursor.close();
        return friends;
    }


    public ArrayList<Communication> getAllInteractions(){
        return getInteractionsHelper(null, null);
    }


    private ArrayList<Communication> getInteractionsHelper(String filterColumn, String[] filterValues){
        SQLiteDatabase db = helper.getReadableDatabase();

        String sortOrder = ReconnectContract.Interaction.DATE  + " DESC";
        Cursor cursor = db.query(
                ReconnectContract.Interaction.TABLE_NAME,
                null, //get all data that interactions contain.
                filterColumn,
                filterValues,
                null,
                null,
                sortOrder
        );


        ArrayList<Communication> interactions = new ArrayList<>();
        while (cursor.moveToNext()){
            int indexIDColumn = cursor.getColumnIndexOrThrow(ReconnectContract.Interaction._ID);
            int dateColumn = cursor.getColumnIndexOrThrow(ReconnectContract.Interaction.DATE);
            int durationColumn = cursor.getColumnIndexOrThrow(ReconnectContract.Interaction.DURATION);
            int typeColumn = cursor.getColumnIndexOrThrow(ReconnectContract.Interaction.TYPE);
            int notesColumn = cursor.getColumnIndexOrThrow(ReconnectContract.Interaction.NOTES);
            int contactIDColumn =   cursor.getColumnIndexOrThrow(ReconnectContract.Interaction.CONTACT_ID);

            Communication comm = new Communication();
            comm.contact_id = cursor.getString(contactIDColumn);
            comm.notes = cursor.getString(notesColumn);
            comm.type = cursor.getString(typeColumn);
            comm.duration = cursor.getString(durationColumn);
            comm.date = cursor.getString(dateColumn);

            interactions.add(comm);

        }
        cursor.close();
        return interactions;


    }


    //get id of person given their first and last name.
    public String getIDFromName(String firstName, String lastName){
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = ReconnectContract.Person.FIRST_NAME + " = ? AND " +
                ReconnectContract.Person.LAST_NAME + " = ?";
        String[] selectionArgs = {firstName, lastName};
        Cursor cursor = db.query(
                ReconnectContract.Person.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        cursor.moveToNext();
        int id_Column = cursor.getColumnIndexOrThrow(ReconnectContract.Person._ID);
        String id = cursor.getString(id_Column);
        cursor.close();
        return id;
    }


    //Filter based on person's first and last name. Also pass in date range for interaction.
    //Can be used to generate contact history for a person.
    //Pass in a large number if you want all recent interactions e.g. 10000
    public ArrayList<Communication> getAllInteractionsForPerson(String firstName, String lastName, int nDays){

        //get the date range for a person.
        Calendar curCalendar = Calendar.getInstance();
        String curDate = curCalendar.get(Calendar.YEAR) + "-" + curCalendar.get(Calendar.MONTH) + "-" + curCalendar.get(Calendar.DAY_OF_MONTH);
        curCalendar.add(Calendar.DATE, -1 *  nDays);
        String previousDate = curCalendar.get(Calendar.YEAR) + "-" + curCalendar.get(Calendar.MONTH) + "-" + curCalendar.get(Calendar.DAY_OF_MONTH);

        //find the person's id.
        String  person_id = getIDFromName(firstName, lastName);

        //build the query.
        String selection = ReconnectContract.Interaction.CONTACT_ID + " = ? AND " + ReconnectContract.Interaction.DATE + " <= ? AND " + ReconnectContract.Interaction.DATE + " >= ?" ;;
        String[] selectionArgs = {person_id, curDate, previousDate};
        return getInteractionsHelper(selection, selectionArgs);
    }

    //method to retrieve interactions in the last n days.
    public ArrayList<Communication> getAllInteractionsInRange(int nDays){
        return null;
    }

    //get all interactions for a specific person in the specified date range.
    public ArrayList<Communication> getAllInteractionsInRange(String first_name, String last_name, int nDays){
        return null;
    }



    /**
     * For timeline feature
     * ***BUG: This method gives date assuming January is 0. Need to add fix
     *Return an arraylist of interactions in the last n days,
     *where each element corresponds to one interaction.
     * Dates should be inputted in format Year--Month
     *@return
     */
    public  ArrayList<Communication> getAllInteractionsByDate(int nDays){
        Calendar curCalendar = Calendar.getInstance();
        String curDate = curCalendar.get(Calendar.YEAR) + "-" + curCalendar.get(Calendar.MONTH) + "-" + curCalendar.get(Calendar.DAY_OF_MONTH);
        curCalendar.add(Calendar.DATE, -1 *  nDays);
        String previousDate = curCalendar.get(Calendar.YEAR) + "-" + curCalendar.get(Calendar.MONTH) + "-" + curCalendar.get(Calendar.DAY_OF_MONTH);
        String dateSelection = ReconnectContract.Interaction.DATE + " <= ? AND " + ReconnectContract.Interaction.DATE + " >= ?" ;
        String[] dateRange = {curDate, previousDate};
        return getInteractionsHelper(dateSelection, dateRange);
    }


     //delete a contact given their first and last name.
     public boolean deleteContact(String firstName, String lastName){
        String contact_id = getIDFromName(firstName, lastName);
        String selection = ReconnectContract.Person._ID + " = ?"; //filter based on the person's id, which matches contact_id in interactions table.
        String[] selectionArgs = {contact_id};
        try {
            int deletedRows = helper.getWritableDatabase().delete(ReconnectContract.Person.TABLE_NAME, selection, selectionArgs);
            return true;
        }catch(Exception e){
            Log.i("Main activity failed", "Can't delete from contact table");
            return false;
        }
     }

    /** TODO: Update a person's info based on new info.
     * Must be made when button is clicked/activity launched.
     * Can't write function independently.
     *
     * @param firstName
     * @param lastName
     * @return
     */
     public boolean updateContact(String firstName, String lastName){
        return false;
     }


    /** TODO: Update interaction info based on new data.
     * Must be made when button is clicked/trigger event occurs.
     * Can't write function independently.
     * @return
     */
    public boolean updateInteraction(){
        return false;
    }


     //Allows you to delete an interaction based on the contact's name, type of interaction and date.
    //All this info required to ensure that we don't accidentally delete wrong data.
     public boolean deleteInteraction(String firstName, String lastName, String typeInteraction, String dateInteraction){
         String contact_id = getIDFromName(firstName, lastName);
         String selection = ReconnectContract.Interaction.CONTACT_ID + " = ? " + ReconnectContract.Interaction.TYPE + " = ? " + ReconnectContract.Interaction.DATE + " = ?";
         String[] selectionArgs = {contact_id, typeInteraction, dateInteraction};
         try {
             int deletedRows = helper.getWritableDatabase().delete(ReconnectContract.Interaction.TABLE_NAME, selection, selectionArgs);
             return true;
         }catch(Exception e){
             Log.i("Main activity failed", "Can't delete from interactions table");
             return false;
         }
     }
    /*


    /**
     *
     * /

    /**
     * TODO: This method not implemented. Need to implement to allow adding interaction based on person's name.
     * Add an interaction record given a person's first name and last name.
     *
     * @param date
     * @param duration
     * @param type
     * @param notes
     * @param first_name
     * @param last_name
     * @param db
     * @return
     */

    public  boolean addInteractionRecord(String date, String duration, String type, String notes, String first_name, String last_name, SQLiteDatabase db){

        String contact_id = getIDFromName(first_name, last_name);
        return addInteractionRecord(date, duration, type, notes, contact_id);
    }

    protected void onDestroy(){
        helper.close();
        super.onDestroy();
    }


   //Method gotten from stack overflow to help with printing (https://stackoverflow.com/questions/27003486/printing-all-rows-of-a-sqlite-database-in-android)
    public String tableToString(SQLiteDatabase db, String tableName) {
        Log.d("","tableToString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        tableString += cursorToString(allRows);
        return tableString;
    }

    //Method gotten from stack overflow to help with printing (https://stackoverflow.com/questions/27003486/printing-all-rows-of-a-sqlite-database-in-android)
    public String cursorToString(Cursor cursor){
        String cursorString = "";
        if (cursor.moveToFirst() ){
            String[] columnNames = cursor.getColumnNames();
            for (String name: columnNames)
                cursorString += String.format("%s ][ ", name);
            cursorString += "\n";
            do {
                for (String name: columnNames) {
                    cursorString += String.format("%s ][ ",
                            cursor.getString(cursor.getColumnIndex(name)));
                }
                cursorString += "\n";
            } while (cursor.moveToNext());
        }
        return cursorString;
    }
}
