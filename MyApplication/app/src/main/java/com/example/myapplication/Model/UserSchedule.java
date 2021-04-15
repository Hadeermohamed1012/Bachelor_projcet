package com.example.myapplication.Model;

import android.icu.lang.UScript;

public class UserSchedule {
   public String Date,Start,End,Room,Meetingid,Capacity;


    public UserSchedule(String room,String date ,String start, String end,String meetingid){
        this.Room = room;
        this.Date= date;
        this.Start=start;
        this.End=end;
        this.Meetingid=meetingid;


    }
    public UserSchedule(String room,String date ,String start, String end,String meetingid,String capacity){
        this.Room = room;
        this.Date= date;
        this.Start=start;
        this.End=end;
        this.Meetingid=meetingid;

        this.Capacity=capacity;
    }

}
