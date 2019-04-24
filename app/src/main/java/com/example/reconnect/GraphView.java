package com.example.reconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class GraphView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get dropdown from XML
        Spinner dropdownHistory = findViewById(R.id.history);
        String[] histories = new String[]{"One Week", "One Month", "One Year"};
        ArrayAdapter<String> historyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, histories);
        dropdownHistory.setAdapter(historyAdapter);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
