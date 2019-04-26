package com.example.reconnect;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class AddInteraction extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interactions);
        final DataManager manager = new DataManager(this);

        final Spinner typeSpinner = (Spinner) findViewById(R.id.interaction_type);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.interactions_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter1);

        final Spinner durationSpinner = (Spinner) findViewById(R.id.interaction_duration);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.duration, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(adapter2);

        final DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
        final Button addInteraction = findViewById(R.id.createInteraction);
        final EditText notesView = (EditText) findViewById(R.id.notes);

        String fullName = "Jane Doe";

        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("fullName") != null) {
            fullName = (bundle.getString("fullName"));
        }

        String[] names = fullName.split(" ");
        final String fName = names[0];
        final String lName = names[1];

        addInteraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notes = notesView.getText().toString();
                String type = typeSpinner.getSelectedItem().toString();
                String duration = durationSpinner.getSelectedItem().toString();
                Date date1 = (Date) new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = sdf.format(date1);
                manager.addInteractionRecord(dateString, duration, type, notes, fName, lName);
                startActivity(new Intent(AddInteraction.this, Timeline.class));
            }
        });
    }
}