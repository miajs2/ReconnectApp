package com.example.reconnect;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

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

    //TODO: still need to set up a pendingIntent for the notification when clicked. Discuss with group.
    //TODO: Remove hardcoded string which symbolizes we're preparing to remind someone.
    //IMPORTANT NOTE: This method assumes that reminders will actually be displayed imminently, else unexpected behavior
    //This is because "REMINDME" is essentially a flag that says that we have generated a reminder for this person already.
    public static void generateNotifications(){


          HashMap<Contact, String> contactsWithReminders = dataManager.getContactsToReconnectWith();
          for (HashMap.Entry<Contact, String> contactWithInfo : contactsWithReminders.entrySet()){
              Contact friend = contactWithInfo.getKey();
              dataManager.updateContactReminder(friend.first_name, friend.last_name, "REMINDME");
              String reminder = contactWithInfo.getValue();

              // Create an explicit intent for when a notification is clicked.
              Intent intent = new Intent();
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
              PendingIntent pendingIntent = PendingIntent.getActivity(previousActivity, 0, intent, 0);



              NotificationCompat.Builder builder =
                      new NotificationCompat.Builder(previousActivity, previousActivity.getString(R.string.CHANNEL_ID))
                              .setSmallIcon(R.drawable.reconnectreminder)
                              .setColor(Color.BLUE)
                              .setContentTitle("Reconnect with " + friend.first_name)
                              .setContentText(reminder)
                              .setStyle(new NotificationCompat.BigTextStyle()
                              .bigText(reminder))
                              .setContentIntent(pendingIntent)
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
