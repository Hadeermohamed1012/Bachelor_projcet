package com.example.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.example.myapplication.Model.AvailableRooms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity  {
TextView display;
    String id,capacity,location,  meeting_id,userid,idroom  ;
    String date,starttime,endtime;
   // ArrayList<AvailableRooms> rooms;
   SharedPrefManager sh =  new SharedPrefManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle b =getIntent().getExtras();
       date = b.getString("date");
         starttime = b.getString("starttime");
        endtime = b.getString("endtime");
        userid = sh.getUserId();
   //final TextView tv = (TextView)findViewById(R.id.wareni);
        final   ArrayList<AvailableRooms> rooms=new  ArrayList<AvailableRooms> ();

//        tv.setText(time + date);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_available_rooms,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();

                        try {

                            JSONArray Array = new JSONArray(response);
                            for(int i =0;i<Array.length();i++){
                                JSONObject object = Array.getJSONObject(i);
                                location= object.getString("location");
                                capacity = object.getString("capacity");
                                id = object.getString("id");
                                // rooms.add(new AvailableRooms(id,capacity,location));

                              // tv.append(location +","+ capacity+ ","+id);
                                rooms.add(new AvailableRooms(id,capacity,location));
                                //String c = rooms.get(0).getCapacity();

                                //rooms.add(i,new AvailableRooms(id,capacity,location));

                                // mDisplayDate.append(c);
                            }
                           // rooms.add(new AvailableRooms(id,capacity,location));
//                            String c = rooms.get(0).getCapacity();0
//
//                            mDisplayDate.append(c);
//
                          My_customt_adapter myadpter= new My_customt_adapter(rooms);
                            ListView ls=(ListView)findViewById(R.id.listview);
                            ls.setAdapter(myadpter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Log.i(TAG, "onResponse: response.tostring()");
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("date", date);
                params.put("starttime", starttime);
                params.put("endtime", endtime);


                return params;
            }
        };

        //RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);







    }
    public void bookroom (final String date, final String starttime ,final String endtime, final String id ,final String capacity){


idroom=id;





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
                            meeting_id = x.getString("id");
                            bookingroom(meeting_id);
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

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
                params.put("start",starttime);
                params.put("end",endtime);

                return params;
            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);




    }
public void bookingroom(String meeting_id){
    Intent passdata = new Intent(this,Booking_Room.class);
    passdata.putExtra("roomdate",date);
    passdata.putExtra("rstime",starttime);
    passdata.putExtra("retime",endtime);
    passdata.putExtra("roomid",idroom);
    passdata.putExtra("roomcapacity",capacity);
    passdata.putExtra("meeting_id",meeting_id);
    startActivity(passdata);
}


    class My_customt_adapter extends BaseAdapter  {
        private ArrayList<AvailableRooms> roomsList;
        //private Context mctx;

        public My_customt_adapter(ArrayList<AvailableRooms> l) {


            this.roomsList = l;

            // this.mctx = c;
        }

        @Override
        public int getCount() {
            return roomsList.size();
        }

        @Override
        public Object getItem(int position) {
            return roomsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linflater = getLayoutInflater();
            View view1 = linflater.inflate(R.layout.available_room, null);

            TextView id = (TextView) view1.findViewById(R.id.tv_room_ID);
            TextView location = (TextView) view1.findViewById(R.id.tv_room_Location);
            TextView capacity = (TextView) view1.findViewById(R.id.tv_room_Capacity);

            id.setText("Room n. :" + roomsList.get(position).Id);
            location.setText("Location : " +roomsList.get(position).Location);
            capacity.setText("Capacity : " +roomsList.get(position).Capacity);
            Button booking = (Button)view1.findViewById(R.id.bookroom);
            final AvailableRooms room =roomsList.get(position);

            booking.setOnClickListener(

                    new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  bookroom(date,starttime,endtime,room.Id,room.Capacity);

                }
            });

            return view1;

        }




    }
}
