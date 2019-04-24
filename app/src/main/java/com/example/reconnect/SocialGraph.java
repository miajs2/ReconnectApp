package com.example.reconnect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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
    private final Point point4;
    private final Point point5;

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
        point1 = new Point((int)this.getX()/2, (int)this.getY()/2);
        point2 = new Point(700, 800);
        point3 = new Point(200, 800);
        point4 = new Point(100,500);
        point5 = new Point(400,400);
    }

    public SocialGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(5);
        path = new Path();

        point1 = new Point(120, 80);
        point2 = new Point(550, 600);
        point3 = new Point(200, 900);
        point4 = new Point(800,400);
        point5 = new Point(800,900);
    }

    public SocialGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(5);
        path = new Path();

        point1 = new Point(200, 300);
        point2 = new Point(700, 800);
        point3 = new Point(200, 800);
        point4 = new Point(500,800);
        point5 = new Point(400,400);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // draw the edge
        path.reset();
        path.moveTo(point1.x, point1.y);
        path.lineTo(point2.x, point2.y);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GRAY);
        p.setStrokeWidth(10);
        canvas.drawPath(path, p);

        // draw the edge
        path.reset();
        path.moveTo(point2.x, point2.y);
        path.lineTo(point3.x, point3.y);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GRAY);
        p.setStrokeWidth(15);
        canvas.drawPath(path, p);

        // draw the edge
        path.reset();
        path.moveTo(point2.x, point2.y);
        path.lineTo(point4.x, point4.y);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GRAY);
        p.setStrokeWidth(25);
        canvas.drawPath(path, p);

        // draw the edge
        path.reset();
        path.moveTo(point2.x, point2.y);
        path.lineTo(point5.x, point5.y);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GRAY);
        p.setStrokeWidth(10);
        canvas.drawPath(path, p);

        Bitmap bm =
                BitmapFactory.decodeResource(getResources(), R.drawable.alex);
        Bitmap rect_avatar1 = Bitmap.createScaledBitmap(bm, 200, 200, false);
        Bitmap avatar1 = getCroppedBitmap(rect_avatar1);

        Bitmap bm2 = BitmapFactory.decodeResource(getResources(),R.drawable.john);
        Bitmap rect_avatar2 = Bitmap.createScaledBitmap(bm2, 200, 200, false);
        Bitmap avatar2 = getCroppedBitmap(rect_avatar2);

        Bitmap bm3 = BitmapFactory.decodeResource(getResources(),R.drawable.mary);
        Bitmap rect_avatar3 = Bitmap.createScaledBitmap(bm3, 200, 200, false);
        Bitmap avatar3 = getCroppedBitmap(rect_avatar3);

        Bitmap bm4 = BitmapFactory.decodeResource(getResources(),R.drawable.sarah);
        Bitmap rect_avatar4 = Bitmap.createScaledBitmap(bm4, 200, 200, false);
        Bitmap avatar4 = getCroppedBitmap(rect_avatar4);

        // draw first vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawBitmap(avatar1,point1.x-50, point1.y-50, null);

        // draw second vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawCircle(point2.x, point2.y, 80, p);

        // draw third vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawBitmap(avatar2,point3.x-100, point3.y-50, null);

        // draw third vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawBitmap(avatar3,point4.x-100, point4.y-80, null);

        // draw third vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawBitmap(avatar4,point5.x-100, point5.y-80, null);
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
