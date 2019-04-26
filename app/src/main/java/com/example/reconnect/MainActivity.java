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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.util.Log;
import android.widget.TextView;

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

        List<HashMap<String, Object>> aList = new ArrayList<>();

        for (Contact c : myContacts) {
            ArrayList<Communication> contactInteractions = manager.getAllInteractionsForPerson(c.first_name,c.last_name,10000);
            Communication mostRecent = contactInteractions.get(0);
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("name", c.first_name + " " + c.last_name);
            hm.put("last_connected", "Last connected: " + mostRecent.date);
            Uri uri = Uri.parse(c.pic_location);
            Bitmap bm;
            try {
                bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

            } catch (Exception e) {
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
            }
            bm = Helper.cropToSquare(bm);
            bm = Helper.getCroppedBitmap(bm);
            hm.put("avatars", bm);
            aList.add(hm);
        }

        String[] from = {"name", "last_connected", "avatars"};
        int[] to = {R.id.name, R.id.date, R.id.avatar};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.contact_home_screen, from, to);
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder(){

            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if( (view instanceof ImageView) & (data instanceof Bitmap) ) {
                    ImageView iv = (ImageView) view;
                    Bitmap bm = (Bitmap) data;
                    iv.setImageBitmap(bm);
                    return true;
                }
                return false;

            }
        });
        ListView androidListView = (ListView) findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);
        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                TextView nameView = (TextView) arg1.findViewById(R.id.name);
                String fullName = nameView.getText().toString();
//                ImageView avatarView = (ImageView) arg1.findViewById(R.id.timeline_avatar);
//                Bitmap bitmapExtra = ((BitmapDrawable)avatarView.getDrawable()).getBitmap();
//                String uriExtra = Helper.getImageUri(getApplicationContext(),bitmapExtra).toString();
                Intent n = new Intent(getApplicationContext(), Timeline.class);
                n.putExtra("position", position);
                n.putExtra("fullName", fullName);
//                n.putExtra("avatarURI", uriExtra);
                startActivity(n);
            }
        });


    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }




}