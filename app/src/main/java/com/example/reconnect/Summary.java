package com.example.reconnect;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Summary extends AppCompatActivity {

    // Hardcoded data to test format
    String[] names = new String[]{
            "Alex Baker", "John Jones", "Mary Smith", "Sarah Adams",
    };


    int[] avatars = new int[]{
            R.drawable.alex, R.drawable.john, R.drawable.mary, R.drawable.sarah,
    };

    String[] lastConnected = new String[]{
            "2 days ago", "5 days ago", "1 week ago", "3 weeks ago",
    };

    int[] modes = new int[]{
            R.drawable.phone_icon, R.drawable.message_icon, R.drawable.face_to_face_icon, R.drawable.phone_icon,
    };

    String[] durations = new String[]{
            "1h", "15m", "2h", "30m",
    };

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

        DataManager dataManager = new DataManager(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Get dropdown from XML
        Spinner dropdownHistory = findViewById(R.id.history);
        String[] histories = new String[]{"1 Week", "2 Weeks","1 Month", "3 Months", "6 Months", "1 Year"};
        ArrayAdapter<String> historyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, histories);
        dropdownHistory.setAdapter(historyAdapter);

        // temporary list of contacts
        List<HashMap<String, String>> aList = new ArrayList<>();
        dataManager.addInteractionRecord("2019-04-21","30 minutes","Phone","","Philip","Jones");
        myInteractions = dataManager.getAllInteractionsByDate(7);

        for (Communication interaction: myInteractions) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("name", interaction.contact_id);
            hm.put("date", interaction.date);
            hm.put("avatars", Integer.toString(R.drawable.alex));
            hm.put("mode", Integer.toString(R.drawable.phone_icon));
            hm.put("duration", interaction.duration);
            aList.add(hm);
        }

        String[] from = {"name", "date", "avatars", "mode", "duration"};
        int[] to = {R.id.name, R.id.date, R.id.avatar, R.id.mode, R.id.duration};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.interaction_template_summary, from, to);
        ListView androidListView = (ListView) findViewById(R.id.summary_list);
        androidListView.setAdapter(simpleAdapter);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
