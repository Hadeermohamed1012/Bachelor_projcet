package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class welcome extends AppCompatActivity implements View.OnClickListener {
    private TextView availableroom,bookroom,yourschedule,partnersschedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        availableroom= (TextView)findViewById(R.id.tv_availableroom);
        bookroom= (TextView)findViewById(R.id.tv_bookyourroom);
        yourschedule = (TextView)findViewById(R.id.tv_showyourscehdule);
        partnersschedule = (TextView)findViewById(R.id.tv_showpartnerschedule);

        availableroom.setOnClickListener(this);
        bookroom.setOnClickListener(this);
      yourschedule.setOnClickListener(this);
       partnersschedule.setOnClickListener(this);
        //yourschedule.setOnClickListener(new View_User_Schedule());
    }


    public void onClick(View v) {
        if(v == availableroom){
            startActivity(new Intent(this, Available_rooms.class));
        }
        else if(v==yourschedule){
//            View_User_Schedule viewSchedule = new View_User_Schedule();
//            viewSchedule.view_my_schedule();
           // Toast.makeText(getApplicationContext(),"hello", Toast.LENGTH_LONG).show();
           startActivity(new Intent(this, View_User_Schedule.class));
        }
        else if (v==partnersschedule ){
            startActivity(new Intent(this, View_partner_Schedule.class));

        }
        else if (v==bookroom ){
            startActivity(new Intent(this, created_schedule.class));

        }

    }

}
