package com.example.reconnect;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class GraphView extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()) {
                case R.id.navigation_contacts:
                    startActivity(new Intent(GraphView.this, MainActivity.class));
                    return true;
                case R.id.navigation_summary:
                    startActivity(new Intent(GraphView.this, Summary.class));
                    return true;
                case R.id.navigation_network:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
