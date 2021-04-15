package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.Model.AvailableRooms;
import com.example.myapplication.Model.UserSchedule;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class View_User_Schedule extends AppCompatActivity  {
    SharedPrefManager sh =  new SharedPrefManager(this);
//    private Context Activity;
//    private   TextView  yourschedule,schedule;
  String id,meetingid,date,starttime,endtime,room,capacity;
Button cancel ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__schedule);
         id = sh.getUserId();
//     cancel= (Button)findViewById(R.id.b_cancel);
//     cancel.setOnClickListener(this);
        // yourschedule = (TextView)findViewById(R.id.tv_showyourscehdule);
     // schedule = (TextView)findViewById(R.id.tv_schedule);
        final ArrayList<UserSchedule> schedule =new  ArrayList

                <UserSchedule> ();





        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_show_yourschedule,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONArray Array = new JSONArray(response);
                            for(int i =0;i<Array.length();i++){
                                JSONObject object = Array.getJSONObject(i);
                               date= object.getString("date");
                             starttime = object.getString("start");
                             endtime = object.getString("end");
                                meetingid = object.getString("id");
                                room  = object.getString("room_id");

                                //String message = object.getString("message");
                               // schedule .append(date + start +end );
                                schedule.add(new UserSchedule(room,date,starttime,endtime,meetingid));


                            }
                            //Toast.makeText(getApplicationContext(),meetingid +""+ id ,Toast.LENGTH_LONG).show();
                           My_customt_adapter myadpter= new My_customt_adapter(schedule);
                            ListView ls=(ListView)findViewById(R.id.listviewschedule);
                            ls.setAdapter(myadpter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);

                return params;
            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_home:
                startActivity(new Intent(this, welcome.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
public void generator(String roome,String datee,String starttimee,String endtimee){
    Intent gIntent = new Intent(this, Generator.class);
    gIntent.putExtra("room_id",roome );

    gIntent.putExtra("date",datee);
    gIntent.putExtra("start",starttimee);
    gIntent.putExtra("endtime",endtimee);

    startActivity(gIntent);


}


    public void cancelmeeting(final String meetingide){



        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_cancel_meeting,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONObject x = new  JSONObject(response);



                            String message = x.getString("message");

                            Toast.makeText(getApplicationContext(), message  , Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("user",id);
                params.put("meeting_id",meetingide);


                return params;
            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

////    @Override
////    public void onClick(View v) {
//        if(v==cancel){
////           // cancelmeeting();
////        }
////    }



    class My_customt_adapter extends BaseAdapter {
        private ArrayList<UserSchedule> scheduleList;
        //private Context mctx;

        public My_customt_adapter(ArrayList<UserSchedule> l) {


            this.scheduleList = l;

            // this.mctx = c;
        }

        @Override
        public int getCount() {
            return scheduleList.size();
        }

        @Override
        public Object getItem(int position) {
            return scheduleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater linflater = getLayoutInflater();
            View view1 = linflater.inflate(R.layout.my_schedule,null);

            TextView id = (TextView) view1.findViewById(R.id.tv_list_IDs);
            TextView room = (TextView) view1.findViewById(R.id.tv_list_rooms);
            TextView location = (TextView) view1.findViewById(R.id.tv_list_Locations);
            TextView capacity = (TextView) view1.findViewById(R.id.tv_list_Capacitys);
            id.setText("Date: "+scheduleList.get(position).Date);
            location.setText("Start : "+scheduleList.get(position).Start);
            capacity.setText("End: "+ scheduleList.get(position).End);
            room.setText("Room: "+ scheduleList.get(position).Room);
            final UserSchedule schedule = scheduleList.get(position);
            Button cancel = (Button)view1.findViewById(R.id.b_cancel);
            Button generate = (Button)view1.findViewById(R.id.gen);
          cancel.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                      cancelmeeting(schedule.Meetingid);


                        }
                    });
            generate.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            generator(schedule.Room,schedule.Date,schedule.Start,schedule.End);

                        }
                    });



            return view1;

        }
    }
}
