package com.example.reconnect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Timeline extends AppCompatActivity implements TimelineAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);

        // adds back button to top bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // adds floating action button for adding an interaction
        FloatingActionButton fab = findViewById(R.id.addInteraction);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Timeline.this, Interactions.class));
            }
        });

        ImageView avatar = (ImageView) findViewById(R.id.timeline_avatar);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.alex);
        bm = getCroppedBitmap(bm);
        avatar.setImageBitmap(bm);

        TextView name = (TextView) findViewById(R.id.timeline_name);
        name.setText("Alex Baker");

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

        List<String> myData = new ArrayList<>();
        myData.add("October 22, 2019");
        myData.add("blah");
        myData.add("blah");
        myData.add("blah");
        myData.add("blah");
        myData.add("blah");
        myData.add("blah");

        // specify an adapter (see also next example)
        mAdapter = new TimelineAdapter(this, myData);
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

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

}
