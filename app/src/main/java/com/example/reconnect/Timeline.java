package com.example.reconnect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Timeline extends AppCompatActivity implements TimelineAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;

    ArrayList<Communication> contactInteractions;
    ArrayList<Contact> contacts;
    Contact timelineContact;
    String selectedHistory = "";
    String fName = "";
    String lName = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);

        final DataManager dataManager = new DataManager(this);

        // adds back button to top bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView name = (TextView) findViewById(R.id.timeline_name);

        // adds floating action button for adding an interaction
        FloatingActionButton fab = findViewById(R.id.addInteraction);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = name.getText().toString();
                Intent n = new Intent(getApplicationContext(), AddInteraction.class);
                n.putExtra("fullName", fullName);
                startActivity(n);
            }
        });

        Bundle bundle = getIntent().getExtras();
        String nameTemp = "";

        if(bundle.getString("fullName")!= null) {
            nameTemp = bundle.getString("fullName");
            name.setText(nameTemp);
        }

        String[] names = nameTemp.split(" ");
        fName = names[0];
        lName = names[1];

        contacts = dataManager.getContacts();
        for(Contact c: contacts){
            String cName = c.first_name + " " + c.last_name;
            String fullName = bundle.getString("fullName");
            if(cName.equals(fullName)){
                timelineContact = c;
            }
        }

        final TextView connectionFreq = (TextView) findViewById(R.id.connection_frequency);
        String connFreq = timelineContact.contact_frequency.toLowerCase();
        connectionFreq.setText("Goal: Connect every " + connFreq);

        ImageView avatar = (ImageView) findViewById(R.id.timeline_avatar);
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.alex);
        Uri uri = Uri.parse(timelineContact.pic_location);
        Bitmap bm;
        try {
            bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

        } catch (Exception e) {
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
        }
        bm = Helper.cropToSquare(bm);
        bm = Helper.getCroppedBitmap(bm);
        avatar.setImageBitmap(bm);

        // Get dropdown from XML
        Spinner dropdownHistory = findViewById(R.id.timeline_spinner);
        String[] histories = new String[]{"1 Week", "2 Weeks","1 Month", "3 Months", "6 Months", "1 Year"};
        ArrayAdapter<String> historyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, histories);
        dropdownHistory.setAdapter(historyAdapter);
        dropdownHistory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHistory = parent.getItemAtPosition(position).toString();
                getTimelineData(fName, lName, dataManager);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedHistory = "1 Week";
            }
        });

    }

    public void getTimelineData(String fName, String lName, DataManager dataManager) {
        int days = 100000;
        switch(selectedHistory){
            case "1 Week":
                days=7;
                break;
            case "2 Weeks":
                days=14;
                break;
            case "1 Month":
                days=30;
                break;
            case "3 Months":
                days=90;
                break;
            case "6 Months":
                days=180;
                break;
            case "1 Year":
                days=365;
                break;
        }

        recyclerView = findViewById(R.id.timeline_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        contactInteractions= dataManager.getAllInteractionsForPerson(fName,lName,days);


        // specify an adapter (see also next example)
        mAdapter = new TimelineAdapter(this, contactInteractions);

        ((TimelineAdapter) mAdapter).setClickListener(this);
        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onItemClick(View view, int position) {
        Communication c = contactInteractions.get(position);
        String notes = c.notes;
        if (notes.length() == 0) {
            notes = "No notes recorded for this interaction.";
        }

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(notes);

        alertDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Timeline.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
