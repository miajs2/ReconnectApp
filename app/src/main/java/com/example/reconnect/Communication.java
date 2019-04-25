package com.example.reconnect;

public class Communication {


    public  String date = "Date";
    public  String duration = "Duration_Mins";
    public  String type = "Type";
    public  String notes = "Notes";
    public  String contact_id = "Contact ID";

    public Communication(){

    }

    public Communication(String date, String duration, String type, String notes, String contact_id){
         this.date = date;
         this.duration = duration;
         this.type = type;
         this.notes = notes;
         this.contact_id = contact_id;
    }
}
