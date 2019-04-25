package com.example.reconnect;

public class Contact {

    public  String first_name;
    public  String last_name;
    public  String pic_location;
    public  String contact_relationship;
    public  String contact_frequency;
    public String id;

    public Contact(){

    }

    public Contact(String first, String last, String loc, String rel, String freq){
        first_name = first;
        last_name = last;
        pic_location = loc;
        contact_relationship = rel;
        contact_frequency = freq;
    }
}
