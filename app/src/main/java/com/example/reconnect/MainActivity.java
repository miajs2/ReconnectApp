package com.example.reconnect;

import android.content.Intent;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.ArrayList;



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

    protected  ReconnectDBHelper helper;
    protected  SQLiteDatabase db; //reference to the database object.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.addContact);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddContact.class));
            }
        });

        Button button2 = (Button) findViewById(R.id.viewSummary);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Summary.class));
            }
        });

        Button button3 = (Button) findViewById(R.id.viewNetwork);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GraphView.class));
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
        int[] to = {R.id.name, R.id.last_connected, R.id.avatar};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.contact_home_screen, from, to);
        ListView androidListView = (ListView) findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);

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
