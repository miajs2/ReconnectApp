package com.example.reconnect;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
        List<HashMap<String, Object>> aList = new ArrayList<>();
//        dataManager.addInteractionRecord("2019-04-21","30 minutes","Phone","","Philip","Jones");
        myInteractions = dataManager.getAllInteractions();

        for (Communication interaction: myInteractions) {
            HashMap<String, Object> hm = new HashMap<>();
            Contact c = dataManager.getNameFromID(interaction.contact_id);
            String name = c.first_name + " " + c.last_name;
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.alex);
            bm = Helper.getCroppedBitmap(bm);
            hm.put("name", name);
            hm.put("date", interaction.date);
            hm.put("avatars", bm);
            hm.put("mode", Integer.toString(Helper.getModeIcon(interaction.type)));
            hm.put("duration", interaction.duration);
            aList.add(hm);
        }

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
