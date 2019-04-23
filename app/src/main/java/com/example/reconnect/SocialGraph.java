package com.example.reconnect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SocialGraph extends View {

    // Hardcoded data to test format
    String[] names = new String[]{
            "Alex Baker", "John Jones", "Mary Smith", "Sarah Adams",
    };


    int[] avatars = new int[]{
            R.drawable.default_avatar, R.drawable.default_avatar, R.drawable.default_avatar, R.drawable.default_avatar,
    };

    String[] lastConnected = new String[]{
            "Last Connected: 3 weeks ago", "Last Connected: 5 months ago", "Last Connected: 2 days ago", "Last Connected: 7 weeks ago",
    };

    private final Paint p;
    private final Path path;
//    private final List<Point> points;
//    private final Point selfPoint;
    private final Point point1;
    private final Point point2;
    private final Point point3;

    public SocialGraph(Context context) {
        super(context);

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(5);
        path = new Path();

        List<HashMap<String, String>> aList = new ArrayList<>();

//        for (int i = 0; i < 4; i++) {
//            HashMap<String, String> hm = new HashMap<>();
//            hm.put("name", names[i]);
//            hm.put("last_connected", lastConnected[i]);
//            hm.put("avatars", Integer.toString(avatars[i]));
//            aList.add(hm);
//        }
//
//        points = new ArrayList<>();
//
//        for (int i = 0; i < aList.size(); i++) {
//            points.add(new Point(0,0));
//        }
//        selfPoint = new Point();
        point1 = new Point(200, 300);
        point2 = new Point(700, 800);
        point3 = new Point(200, 800);
    }

    public SocialGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(5);
        path = new Path();

        point1 = new Point(200, 300);
        point2 = new Point(700, 800);
        point3 = new Point(200, 800);
    }

    public SocialGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(5);
        path = new Path();

        point1 = new Point(200, 300);
        point2 = new Point(700, 800);
        point3 = new Point(200, 800);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // draw the edge
        path.reset();
        path.moveTo(point1.x, point1.y);
        path.lineTo(point2.x, point2.y);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);
        canvas.drawPath(path, p);

        // draw the edge
        path.reset();
        path.moveTo(point2.x, point2.y);
        path.lineTo(point3.x, point3.y);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GRAY);
        canvas.drawPath(path, p);

        // draw first vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawCircle(point1.x, point1.y, 25, p);

        // draw second vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawCircle(point2.x, point2.y, 25, p);

        // draw third vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawCircle(point3.x, point3.y, 25, p);
    }
}
