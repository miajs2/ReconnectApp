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

    ArrayList<Contact> myContacts;

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
        DataManager manager = new DataManager(this);

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

        myContacts = manager.getContacts();

        List<HashMap<String, String>> aList = new ArrayList<>();

        for (Contact c : myContacts) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("name", c.first_name + " " + c.last_name);
            hm.put("last_connected", "Last Connected: 5 weeks");
            hm.put("avatars", Integer.toString(R.drawable.mary));
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


    }





}