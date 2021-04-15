package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class Booking_Room extends AppCompatActivity implements View.OnClickListener{
String date,stime,etime,id,endmeeting,userid,pname,meeting_id,capacity;
    Button booking;
    Button addpartner;
    TextView sendmail;
    TextView info;
    EditText end;
    EditText patnername;
    String message;
    SharedPrefManager sh =  new SharedPrefManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking__room);
        Bundle b =getIntent().getExtras();
        date = b.getString("roomdate");
        stime = b.getString("rstime");
        etime = b.getString("retime");
       id= b.getString("roomid");
       capacity = b.getString("roomcapacity");
       meeting_id= b.getString("meeting_id");
       userid = sh.getUserId();
        info = (TextView)findViewById(R.id.Room_info);
        info.setText( "Date :"+date + " " + "\n" +"StartTime : "+ stime + "" +"\n"+"End Time: " + etime +"\n"+"Room n. :"+ id );
     //end = (EditText) findViewById(R.id.duration);
     patnername = (EditText)findViewById(R.id.et_partner);


//      booking = (Button)findViewById(R.id.button1);
//        booking.setOnClickListener(this);
        addpartner = (Button) findViewById(R.id.b_partner);
        addpartner.setOnClickListener(this);
sendmail=(TextView)findViewById(R.id.tv_sendmail);
sendmail.setOnClickListener(this);
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
    public void bookingroom (){
        //endmeeting = end.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_book_room,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONObject x = new  JSONObject(response);
                           // info.append( "end" +endmeeting);


                            String message = x.getString("message");
                            //meeting_id = x.getString("id");
         //  Toast.makeText(getApplicationContext(), message + meeting_id , Toast.LENGTH_LONG).show();

//                               JSONObject object = Array.getJSONObject(i);
//////                                String date= object.getString("date");
//////                                String start = object.getString("start");
//////                                String end = object.getString("end");
////
////
//                              message = object.getString("message");
//
////                                // schedule .append(date + start +end );
////
//                              }
                            //  Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


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
                params.put("user_id",userid);
                params.put("room_id",id);
                params.put("date",date);
                params.put("start",stime);
                params.put("end",etime);

                return params;
            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    public void  checkisuser( final String namee) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_is_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONObject x = new JSONObject(response);
                            message= x.getString("message");
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            if (x.getString("message").equals("your partners's name is right")){
                                addpartner();
//                               flag = false;
//                                //Toast.makeText(getApplicationContext(),"your partner's name is invalid",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"your partner's name is invalid",Toast.LENGTH_LONG).show();
                            }
//



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
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", namee);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void addpartner (){
       pname =  patnername.getText().toString().trim();
       if (pname.isEmpty()){
           Toast.makeText(getApplicationContext(),"you have to enter your partner's name", Toast.LENGTH_LONG).show();
       }else {
           StringRequest stringRequest = new StringRequest(
                   Request.Method.POST,
                   Constants.URL_add_partner,
                   new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {

                           try {


                               JSONObject x = new JSONObject(response);


                               String message = x.getString("message");
                               if (message.contentEquals("capacity of room is full")) {
                                   sendmail();

                               }

                               Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


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
           ) {
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String, String> params = new HashMap<>();

                   params.put("meeting_id", meeting_id);
                   params.put("partner", pname);
                   params.put("capacity", capacity);


                   return params;
               }

           };
           RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


       }}


public void sendmail(){
    Intent passdata=new Intent(this,SendEmail.class);

    passdata.putExtra("meeting_id",meeting_id);
    passdata.putExtra("room",id);
    passdata.putExtra("date",date);
    passdata.putExtra("start",stime);
    passdata.putExtra("end",etime);

    startActivity(passdata);
}


    @Override
    public void onClick(View v) {
        if(v == booking){
            bookingroom();

        }
        if (v == addpartner){
            pname =  patnername.getText().toString().trim();
            checkisuser(pname);
        }
        if(v==sendmail){
            sendmail();
        }
    }
}
