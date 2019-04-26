package com.example.reconnect;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Helper {

    public static int getModeIcon(String mode) {
        int modeIcon = R.drawable.help_icon;
        switch(mode) {
            case "In-Person Meeting":
                modeIcon = R.drawable.face_to_face_icon;
                break;
            case "Phone Call":
                modeIcon = R.drawable.phone_icon;
                break;
            case "Messaging":
                modeIcon = R.drawable.message_icon;
                break;
            case "Video Chat":
                modeIcon = R.drawable.video_call_icon;
                break;
        }
        return modeIcon;
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
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

    public static String getDateTime(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        String format = "yyyy-MM-dd";
        return new SimpleDateFormat(format).format(cal.getTime());
    }
}
