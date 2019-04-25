//Note: Used official Android developers page as reference/tutorial
//Reference found here: https://developer.android.com/training/data-storage/sqlite#java.

package com.example.reconnect;

import android.provider.BaseColumns;
import java.lang.Math;
import java.util.Random;

/**TODO:
 * Need to add functionality to insert an entry.
 * Need to add functionality to delete an entry.
 *
*/
public final class ReconnectContract {
    //Class should not be instantiated directly;
    //hence, constructor private.


    private ReconnectContract(){

    }
    //schema for a person.
    //we inherit an _ID field because we extend BaseColumns
    public static class Person implements BaseColumns {
      public static final String TABLE_NAME = "Person";
      public static final String FIRST_NAME = "First Name";
      public static final String LAST_NAME = "Last Name";
      public static final String PIC_LOCATION = "Picture";
      public static final String CONTACT_RELATIONSHIP = "Relationship";
      public static final String CONTACT_FREQUENCY = "Frequency of Contact";

    }

    //schema for one interaction
    public static class Interaction implements BaseColumns {
        public static final String TABLE_NAME = "Interaction";
        public static final String DATE = "Date";
        public static final String DURATION = "Duration_Mins";
        public static final String TYPE = "Type";
        public static final String NOTES = "Notes";
        public static final String CONTACT_ID = "Contact ID";
    }



    //DONE
    //sql string for generating person table
    public  static String createPersonTable () {
        String CREATE_PERSON_TABLE = "CREATE TABLE IF NOT EXISTS " + Person.TABLE_NAME
                + " (" + Person._ID + " INTEGER PRIMARY KEY, " + Person.FIRST_NAME + " TEXT NOT NULL, "
                + Person.LAST_NAME + " TEXT NOT NULL, " + Person.CONTACT_RELATIONSHIP + " TEXT, " + Person.CONTACT_FREQUENCY +  " TEXT NOT NULL, " + Person.PIC_LOCATION + " TEXT)";
        return CREATE_PERSON_TABLE;
    }


    //DONE
    //sql string for generating interaction table
    //TODO: Add constraints check for foreign key (interaction.contact_id). Should be tied to a valid person_id in the person table.
    public static String createInteractionTable (){
        String CREATE_INTERACTION_TABLE = "CREATE TABLE IF NOT EXISTS " + Interaction.TABLE_NAME +
                " (" + Interaction._ID + " INTEGER PRIMARY KEY, " + Interaction.CONTACT_ID + " INTEGER NOT NULL, " + Interaction.DATE + " TEXT NOT NULL, " +
                Interaction.DURATION + " TEXT NOT NULL, " + Interaction.TYPE + " TEXT NOT NULL, " +
                Interaction.NOTES + " TEXT NOT NULL)";
        return CREATE_INTERACTION_TABLE;

    }



    /**
     *Randomly generate info for a person.
     *TODO: decide on what exactly this method should be returning. Maybe generate a random person.
     *
     * @return
     */
    public static String generatePersonEntry(){
        String[] first_names = "John, Mary, Peter, Susan, Mia, Wendy, Chinny, Alex, Sarah, Nura, Abby".split(",");
        String[] last_names = "Doe, Jones, Blackwater, Gold, Black, Emeka, Okoluto, Obama, Biden, Cooper".split(",");
        Random random  = new Random();
        //pick first and last name randomly, from the two arrays.
        String first_name = first_names[random.nextInt(first_names.length)];
        String last_name = last_names[random.nextInt(last_names.length)];


        //TODO: generate string to be returned.
        return "INSERT INTO " + Person.TABLE_NAME + " (";
    }


    //private static final String CREATE_PERSON_TABLE
}
