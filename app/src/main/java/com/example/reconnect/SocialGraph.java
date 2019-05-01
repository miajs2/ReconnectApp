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
    private Point alexPoint;
    private Point selfPoint;
    private Point danielaPoint;
    private Point chrisPoint;
    private Point sarahPoint;
    private Point noraPoint;

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
    }

    public SocialGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(5);
        path = new Path();
    }

    public SocialGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(5);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int xPos = (canvas.getWidth() / 2);
        int yPos = (canvas.getHeight() / 2);
        selfPoint = new Point(xPos, yPos);
        noraPoint = new Point(1100, 1000);
        alexPoint = new Point(250, 500);
        sarahPoint = new Point(800,1500);
        danielaPoint = new Point(200, 1000);
        chrisPoint = new Point(1200,250);


        // draw the edge
        path.reset();
        path.moveTo(alexPoint.x, alexPoint.y);
        path.lineTo(selfPoint.x, selfPoint.y);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.LTGRAY);
        p.setStrokeWidth(25);
        canvas.drawPath(path, p);

        // draw the edge
        path.reset();
        path.moveTo(selfPoint.x, selfPoint.y);
        path.lineTo(danielaPoint.x, danielaPoint.y);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.LTGRAY);
        p.setStrokeWidth(15);
        canvas.drawPath(path, p);

        // draw the edge
        path.reset();
        path.moveTo(selfPoint.x, selfPoint.y);
        path.lineTo(chrisPoint.x, chrisPoint.y);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.LTGRAY);
        p.setStrokeWidth(10);
        canvas.drawPath(path, p);

        // draw the edge
        path.reset();
        path.moveTo(selfPoint.x, selfPoint.y);
        path.lineTo(sarahPoint.x, sarahPoint.y);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.LTGRAY);
        p.setStrokeWidth(45);
        canvas.drawPath(path, p);

        // draw the edge
        path.reset();
        path.moveTo(selfPoint.x, selfPoint.y);
        path.lineTo(noraPoint.x, noraPoint.y);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.LTGRAY);
        p.setStrokeWidth(10);
        canvas.drawPath(path, p);

        Bitmap bm =
                BitmapFactory.decodeResource(getResources(), R.drawable.nora);
        bm = Helper.cropToSquare(bm);
        Bitmap avatar1 = Bitmap.createScaledBitmap(bm, 200, 200, false);
        avatar1 = Helper.getCroppedBitmap(avatar1);

        Bitmap bm2 = BitmapFactory.decodeResource(getResources(),R.drawable.alex);
        bm2 = Helper.cropToSquare(bm2);
        Bitmap avatar2 = Bitmap.createScaledBitmap(bm2, 200, 200, false);
        avatar2 = Helper.getCroppedBitmap(avatar2);

        Bitmap bm3 = BitmapFactory.decodeResource(getResources(),R.drawable.sarah);
        bm3 = Helper.cropToSquare(bm3);
        Bitmap avatar3 = Bitmap.createScaledBitmap(bm3, 200, 200, false);
        avatar3 = Helper.getCroppedBitmap(avatar3);

        Bitmap bm4 = BitmapFactory.decodeResource(getResources(),R.drawable.daniela);
        bm4 = Helper.cropToSquare(bm4);
        Bitmap avatar4 = Bitmap.createScaledBitmap(bm4, 200, 200, false);
        avatar4 = Helper.getCroppedBitmap(avatar4);

        Bitmap bm5 = BitmapFactory.decodeResource(getResources(),R.drawable.chris);
        bm5 = Helper.cropToSquare(bm5);
        Bitmap avatar5 = Bitmap.createScaledBitmap(bm5, 200, 200, false);
        avatar5 = Helper.getCroppedBitmap(avatar5);


        // draw self vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawCircle(selfPoint.x, selfPoint.y, 50, p);

        // draw first vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.medGray));
        canvas.drawCircle(noraPoint.x, noraPoint.y, 120, p);
        canvas.drawBitmap(avatar1, noraPoint.x-100, noraPoint.y-100, null);

        // draw second vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.medGray));
        canvas.drawCircle(alexPoint.x, alexPoint.y, 120, p);
        canvas.drawBitmap(avatar2, alexPoint.x-100, alexPoint.y-100, null);

        // draw third vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.medGray));
        canvas.drawCircle(sarahPoint.x, sarahPoint.y, 120, p);
        canvas.drawBitmap(avatar3, sarahPoint.x-100, sarahPoint.y-100, null);

        // draw fourth vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.medGray));
        canvas.drawCircle(danielaPoint.x, danielaPoint.y, 120, p);
        canvas.drawBitmap(avatar4, danielaPoint.x-100, danielaPoint.y-100, null);

        // draw fifth vertex
        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.medGray));
        canvas.drawCircle(chrisPoint.x, chrisPoint.y, 120, p);
        canvas.drawBitmap(avatar5, chrisPoint.x-100, chrisPoint.y-100, null);



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
