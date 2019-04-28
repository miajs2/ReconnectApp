package com.example.reconnect;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


/**
 TODO: Decide on behavior of notifications and set it here.
 * Currently, we just display the notifications on the screen when the app is initialized.
 * Class to handle the creation of notifications.
 * Used the official Android documentation as a guide (https://developer.android.com/training/notify-user/build-notification).
 *
 */
public class ReconnectNotifications {



    /*Notification builder is the key, the id associated with that notification is the value*/
    protected static HashMap<NotificationCompat.Builder, Integer> notificationList ;

    /*Activity from which this class is initialized. */
    private static Activity previousActivity;

    /**counter used to generate id for each notification. */
    private static int counter;

    private static DataManager dataManager;


    //Needs an activity object and a data manager as well.
    public ReconnectNotifications(AppCompatActivity activity, DataManager d){
        notificationList= new HashMap<>();
        previousActivity = activity;
        dataManager = d;
    }

    //TODO: still need to set upo a pendingIntent for the notification when clicked. Discuss with group.
    public static void generateNotifications(){
          HashMap<Contact, String> contactsWithReminders = dataManager.getContactsToReconnectWith();
          for (HashMap.Entry<Contact, String> contactWithInfo : contactsWithReminders.entrySet()){
              Contact friend = contactWithInfo.getKey();
              String reminder = contactWithInfo.getValue();

              NotificationCompat.Builder builder =
                      new NotificationCompat.Builder(previousActivity, previousActivity.getString(R.string.CHANNEL_ID))
                              .setSmallIcon(R.drawable.reconnectreminder)
                              .setContentTitle("Reconnect with " + friend.first_name)
                              .setContentText(reminder)
                              .setStyle(new NotificationCompat.BigTextStyle()
                              .bigText(reminder))
                               .setAutoCancel(true);

              notificationList.put(builder, counter++); //add notification to notification list
          }

    }



    //TODO: Store channel name and description in strings.xml file. Need to decide on names and conventions with group
    //This method was adopted from the android developers tutorial found here (https://developer.android.com/training/notify-user/build-notification).
    //Safe to call this method many times; notification channel is not recreated if it already exists.

    private static void createNotificationChannel() {
        // Create the NotificationChannel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ReconnectReminderChannel";
            String description = "Remind user to reconnect with friends";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(previousActivity.getString(R.string.CHANNEL_ID), name, importance);
            channel.setDescription(description);

            // Register the channel with the system; *** Note: can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = previousActivity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void deployNotifications() {
        //create the notification channel.
        createNotificationChannel();

        //make notifications and add them to the list.
        generateNotifications();



        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(previousActivity);


        //display each notification
        for (HashMap.Entry<NotificationCompat.Builder, Integer> singleNotification : notificationList.entrySet()) {

            NotificationCompat.Builder builder = singleNotification.getKey();
            int notificationId = singleNotification.getValue();

            //deploy a single notification
            notificationManager.notify(notificationId, builder.build());

        }

    }
}
