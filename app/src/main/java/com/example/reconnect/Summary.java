package com.example.reconnect;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Summary extends AppCompatActivity {

    String selectedHistory = "";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()) {
                case R.id.navigation_contacts:
                    startActivity(new Intent(Summary.this, MainActivity.class));
                    return true;
                case R.id.navigation_summary:
                    return true;
                case R.id.navigation_network:
                    startActivity(new Intent(Summary.this, GraphView.class));
                    return true;
            }
            return false;
        }
    };

    ArrayList<Communication> myInteractions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary2);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        selectedHistory = "1 Week";
        // Get dropdown from XML
        final Spinner dropdownHistory = findViewById(R.id.history);
        String[] histories = new String[]{"1 Week", "2 Weeks","1 Month", "3 Months", "6 Months", "1 Year"};
        final ArrayAdapter<String> historyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, histories);
        dropdownHistory.setAdapter(historyAdapter);
        dropdownHistory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHistory = parent.getItemAtPosition(position).toString();
                historyAdapter.notifyDataSetChanged();
                getSummaryData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedHistory = "1 Week";
            }
        });
    }

    public void getSummaryData() {
        DataManager dataManager = new DataManager(this);
        // temporary list of contacts
        List<HashMap<String, Object>> aList = new ArrayList<>();
//        dataManager.addInteractionRecord("2019-04-21","30 minutes","Phone","","Philip","Jones");

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
        myInteractions = dataManager.getAllInteractionsByDate(days);

        Set<String> sumContacts = new HashSet<String>();

        for (Communication interaction: myInteractions) {
            HashMap<String, Object> hm = new HashMap<>();
            Contact c = dataManager.getNameFromID(interaction.contact_id);
            sumContacts.add(interaction.contact_id);
            String name = c.first_name + " " + c.last_name;
            Uri uri = Uri.parse(c.pic_location);
            Bitmap bm;
            try {
                bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

            } catch (Exception e) {
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
            }
            bm = Helper.cropToSquare(bm);
            bm = Helper.getCroppedBitmap(bm);
            hm.put("name", name);
            hm.put("date", interaction.date);
            hm.put("avatars", bm);
            hm.put("mode", Integer.toString(Helper.getModeIcon(interaction.type)));
            hm.put("duration", interaction.duration);
            aList.add(hm);
        }

        int numInteractions = myInteractions.size();
        TextView sumString = (TextView) findViewById(R.id.summary_description);
        String numContacts = "";
        if (sumContacts.size() == 1) {
            numContacts = "1 person";
        } else {
            numContacts = Integer.toString(sumContacts.size()) + " people";
        }
        sumString.setText("You reconnected " + Integer.toString(numInteractions) + " times with " + numContacts + " over the past " + selectedHistory.toLowerCase() + ":");

        String[] from = {"name", "date", "avatars", "mode", "duration"};
        int[] to = {R.id.name, R.id.date, R.id.avatar, R.id.mode, R.id.duration};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.interaction_template_summary, from, to);
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
        ListView androidListView = (ListView) findViewById(R.id.summary_list);
        androidListView.setAdapter(simpleAdapter);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
