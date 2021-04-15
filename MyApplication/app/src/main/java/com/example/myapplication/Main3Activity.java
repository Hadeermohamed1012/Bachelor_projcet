package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.example.myapplication.Model.PartnerSchedule;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {

   String name;
   TextView partner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Bundle b =getIntent().getExtras();
    name = b.getString("partner");

        final ArrayList<PartnerSchedule> schedule =new  ArrayList<PartnerSchedule> ();

   partner = (TextView)findViewById(R.id.tvp);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_show_partnerschedule ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONArray Array = new JSONArray(response);


if(Array.length()==0){
    Toast.makeText(getApplicationContext(),"NO Events to show",Toast.LENGTH_LONG).show();

}else{
                            for(int i =0;i<Array.length();i++) {
                                JSONObject object = Array.getJSONObject(i);
                                String date = object.getString("date");
                                String start = object.getString("start");
                                String end = object.getString("end");
                                ///  Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
                                // String message = object.getString("message");


                                schedule.add(new PartnerSchedule(date, start, end));
//                                partner.append(date + start+end);

                            }

                            }
                           My_customt_adapter myadpter= new My_customt_adapter(schedule);
                            ListView ls=(ListView)findViewById(R.id.listviewpschedule);
                            ls.setAdapter(myadpter);

                            //Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();


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
                params.put("user",name);

                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    class My_customt_adapter extends BaseAdapter {
        private ArrayList<PartnerSchedule> scheduleList;
        //private Context mctx;

        public My_customt_adapter(ArrayList<PartnerSchedule> l) {


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
            View view1 = linflater.inflate(R.layout.room_row, null);

            TextView id = (TextView) view1.findViewById(R.id.tv_list_ID);
            TextView location = (TextView) view1.findViewById(R.id.tv_list_Location);
            TextView capacity = (TextView) view1.findViewById(R.id.tv_list_Capacity);
            id.setText("Date: "+scheduleList.get(position).Date);
            location.setText("Start "+scheduleList.get(position).Start);
            capacity.setText("End "+ scheduleList.get(position).End);
            return view1;

        }
    }
}
