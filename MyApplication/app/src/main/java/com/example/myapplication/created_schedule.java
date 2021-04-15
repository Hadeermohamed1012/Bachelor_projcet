package com.example.myapplication;

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
import com.example.myapplication.Model.UserSchedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class created_schedule extends AppCompatActivity {
    SharedPrefManager sh =  new SharedPrefManager(this);
    //    private Context Activity;
//    private   TextView  yourschedule,schedule;
    String id,meetingid;
    String capacity;
    Button cancel ;
    String date,start,end,room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_schedule);

        id = sh.getUserId();
//     cancel= (Button)findViewById(R.id.b_cancel);
//     cancel.setOnClickListener(this);
        // yourschedule = (TextView)findViewById(R.id.tv_showyourscehdule);
        // schedule = (TextView)findViewById(R.id.tv_schedule);
        final ArrayList<UserSchedule> schedule =new  ArrayList

                <UserSchedule> ();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_my_created_meetings,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONArray Array = new JSONArray(response);
                            for(int i =0;i<Array.length();i++){
                                JSONObject object = Array.getJSONObject(i);
                               date= object.getString("date");
                                 start = object.getString("start");
                              end = object.getString("end");
                                meetingid = object.getString("id");
                                capacity = object.getString("capacity");
                                room  = object.getString("room_id");

                                //String message = object.getString("message");
                                // schedule .append(date + start +end );
                                schedule.add(new UserSchedule(room,date,start,end,meetingid,capacity));



                            }
//                            Toast.makeText(getApplicationContext(),meetingid +""+ id ,Toast.LENGTH_LONG).show();
                           My_customt_adapter myadpter= new created_schedule.My_customt_adapter(schedule);
                            ListView ls=(ListView)findViewById(R.id.my_created_schedule);
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
                params.put("user_id",id);

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
            View view1 = linflater.inflate(R.layout.my_created_schedule, null);

            TextView date= (TextView) view1.findViewById(R.id.tv_list_date);
            TextView room= (TextView) view1.findViewById(R.id.tv_list_room);
            TextView start = (TextView) view1.findViewById(R.id.tv_list_start);
            TextView end = (TextView) view1.findViewById(R.id.tv_list_end);
           date.setText("Date: " + scheduleList.get(position).Date);
           start.setText("Start : " + scheduleList.get(position).Start);
            end.setText("End: " + scheduleList.get(position).End);
           room.setText("Room: " + scheduleList.get(position).Room);
            final UserSchedule schedule = scheduleList.get(position);
            Button cancel = (Button)view1.findViewById(R.id.b_mcancel);
            Button send=(Button)view1.findViewById(R.id.b_msend);
            Button add=(Button)view1.findViewById(R.id.b_maddpartner);

            cancel.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelmeeting(schedule.Meetingid);


                        }
                    });
            send.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sendmail(schedule.Room,schedule.Date,schedule.Start,schedule.End,schedule.Meetingid);

                        }
                    });
           add.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addpartner(schedule.Room,schedule.Meetingid,schedule.Capacity,schedule.Date,schedule.Start,schedule.End);

                        }
                    });



            return view1;

        }
    }
    public void sendmail(String roome,String datee,String starte,String ende,String meetingid){
        Intent passdata=new Intent(this,SendEmail.class);
        passdata.putExtra("meeting_id",meetingid );
        passdata.putExtra("date",datee);
        passdata.putExtra("start",starte);
        passdata.putExtra("end",ende);
        passdata.putExtra("room",roome);
        startActivity(passdata);
    }
    public void addpartner(String roome,String meetingide,String capacitye,String datee,String starte,String ende){
        Intent passdata=new Intent(this,Add_partner.class);
        passdata.putExtra("meeting_id",meetingide );
        passdata.putExtra("capacity",capacitye );
        passdata.putExtra("date",datee);
        passdata.putExtra("start",starte);
        passdata.putExtra("end",ende);
        passdata.putExtra("room",roome);
        startActivity(passdata);
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
}
