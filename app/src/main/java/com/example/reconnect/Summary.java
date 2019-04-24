package com.example.reconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
            R.drawable.default_avatar, R.drawable.default_avatar, R.drawable.default_avatar, R.drawable.default_avatar,
    };

    String[] lastConnected = new String[]{
            "3 weeks ago", "5 months ago", "2 days ago", "7 weeks ago",
    };

    int[] modes = new int[]{
            R.drawable.phone_icon, R.drawable.message_icon, R.drawable.face_to_face_icon, R.drawable.phone_icon,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary2);

        // Get dropdown from XML
        Spinner dropdownHistory = findViewById(R.id.history);
        String[] durations = new String[]{"One Week", "One Month", "One Year"};
        ArrayAdapter<String> historyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, durations);
        dropdownHistory.setAdapter(historyAdapter);

        // temporary list of contacts
        List<HashMap<String, String>> aList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("name", names[i]);
            hm.put("last_connected", lastConnected[i]);
            hm.put("avatars", Integer.toString(avatars[i]));
            hm.put("mode", Integer.toString(modes[i]));
            aList.add(hm);
        }

        String[] from = {"name", "last_connected", "avatars", "mode"};
        int[] to = {R.id.name, R.id.last_connected, R.id.avatar, R.id.mode};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.interaction_template_summary, from, to);
        ListView androidListView = (ListView) findViewById(R.id.summary_list);
        androidListView.setAdapter(simpleAdapter);
    }
}
