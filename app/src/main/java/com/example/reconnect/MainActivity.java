package com.example.reconnect;

import android.content.Intent;
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
    }
}
