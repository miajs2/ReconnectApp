package com.example.reconnect;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;



//Notes for this class: Handles creation of the database
//Also provides functionality  for adding data into the database.
//Methods for data processing are static, so can be used with other activities.
//TODO: Need to add methods to allow for efficient retrieval of data from database (e.g. filtering based on date, filtering based by person etc.)

public class MainActivity extends AppCompatActivity {

    protected  ReconnectDBHelper helper;
    protected  SQLiteDatabase db; //reference to the database object.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new ReconnectDBHelper(this);
        db =  helper.getWritableDatabase();
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

       }
       return false;
    }


    /**
     *Add a single person's record to the person table.
     * @param
     * @param
     * @param
     * @param contact_id: add interaction record using the contact_id to tie it to specific person.
     * @param db: need to pass in database in write mode (ie. mydatabase.getWritableDatabase())
     *
     * @return
     */
    public static boolean addInteractionRecord(String date, String duration, String type, String notes, String contact_id, SQLiteDatabase db){
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

      }
      return false;
    }


    /**
     * TODO: Need this method to retrieve interaction data.
     *Return an arraylist of interactions in the last n days,
     *where each element corresponds to one interaction.
     *Should pass in a dbHelper.getReadableDatabase()
     *@return
     */
    public static ArrayList<String> getInteractionsByDate(int nDays, SQLiteDatabase db){

        String selection = ReconnectContract.Interaction.DATE + " = ?";

        return null;
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

    public static boolean addInteractionRecord(String date, String duration, String type, String notes, String first_name, String last_name, SQLiteDatabase db){
        return false;
    }


    /**
     * TODO: This method not implemented.
     * Helper method to retrieve a contact's id from the database, given a first and last name.
     * In case of more than one name, return the first one.
     * @param firstname
     * @param lastname
     * @param db
     * @return
     */
    private static int findContactID(String firstname, String lastname, SQLiteDatabase db){
       return 0;
    }



    protected void onDestroy(){
        helper.close();
        super.onDestroy();
    }
}
