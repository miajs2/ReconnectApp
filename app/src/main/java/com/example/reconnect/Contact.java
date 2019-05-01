package com.example.reconnect;

public class Contact implements Comparable<Contact> {

    public  String first_name;
    public  String last_name;
    public  String pic_location;
    public  String contact_relationship;
    public  String contact_frequency;
    public String id;
    public String date_added;
    public String reminder_note;

    public Contact(){

    }

    public Contact(String first, String last, String loc, String rel, String freq, String when_added){
        first_name = first;
        last_name = last;
        pic_location = loc;
        contact_relationship = rel;
        contact_frequency = freq;
        date_added = when_added;

    }

    @Override
    public int compareTo(Contact c) {
        return this.first_name.compareTo(c.first_name);
    }
}
