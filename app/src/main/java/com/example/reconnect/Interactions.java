package com.example.reconnect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Interactions extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interactions);

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
       final  Button addContact = findViewById(R.id.createInteraction);

        addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(Interactions.this, datePicker.getDayOfMonth()+""+datePicker.getMonth()+""+datePicker.getYear(),Toast.LENGTH_LONG).show();
            }
        });

        //final EditText notesView = (EditText) findViewById(R.id.notes);

        /*
        Button addContact = findViewById(R.id.createInteraction);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String notes = notesView.getText().toString();
                //String type = typeSpinner.getSelectedItem().toString();
                //String duration = durationSpinner.getSelectedItem().toString();
                //Date date1 = (Date) new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //String dateString = sdf.format(date1);
                //startActivity(new Intent(Interactions.this, Timeline.class));
            }
        });
        */


    }
}