package com.example.reconnect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);

        DataManager dataManager = new DataManager(this);

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
        String fName = names[0];
        String lName = names[1];

        ImageView avatar = (ImageView) findViewById(R.id.timeline_avatar);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
        bm = Helper.getCroppedBitmap(bm);
        avatar.setImageBitmap(bm);

        // Get dropdown from XML
        Spinner dropdownHistory = findViewById(R.id.timeline_spinner);
        String[] histories = new String[]{"1 Week", "2 Weeks","1 Month", "3 Months", "6 Months", "1 Year"};
        ArrayAdapter<String> historyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, histories);
        dropdownHistory.setAdapter(historyAdapter);

        recyclerView = findViewById(R.id.timeline_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        contactInteractions= dataManager.getAllInteractionsForPerson(fName,lName,10000);


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
        Toast.makeText(this, "You clicked ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
