package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.LauncherActivity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.X;


public class Available_rooms extends AppCompatActivity   implements  TimePickerDialog.OnTimeSetListener {
    private TextView mDisplayDate;
    TextView textView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
     private Button button,show;
     String Time ,Date,endmeeting,ending;
    String id,capacity,location ;
    ArrayList<AvailableRooms> rooms;
    EditText end;

    @Override
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliable_rooms);
        Button button = (Button) findViewById(R.id.b_time);
        mDisplayDate = (TextView) findViewById(R.id.tv_date);
        TextView textView = (TextView) findViewById(R.id.tv_time);
        end = (EditText) findViewById(R.id.duration);



      final   Button show = (Button) findViewById(R.id.b_show);
       // final   ArrayList<AvailableRooms> rooms =new  ArrayList<AvailableRooms> ();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        Available_rooms.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;


            String  date = year+ "-" + month+ "-" + day;
            Date=date;
                mDisplayDate.setText("Date: "+date);
            }
        };
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) findViewById(R.id.tv_time);
        Time = hourOfDay + ":" + minute;

        textView.setText( "Time : "+hourOfDay + ":" + minute);
    }




    class My_customt_adapter extends BaseAdapter {
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
            View view1 = linflater.inflate(R.layout.room_row, null);

            TextView id = (TextView) view1.findViewById(R.id.tv_list_ID);
            TextView location = (TextView) view1.findViewById(R.id.tv_list_Location);
            TextView capacity = (TextView) view1.findViewById(R.id.tv_list_Capacity);
            id.setText(roomsList.get(position).Id);
            location.setText(roomsList.get(position).Location);
            capacity.setText(roomsList.get(position).Capacity);
            return view1;

        }
    }
   public void show_available_rooms(){
//     final    String time = textView.getText().toString().trim();
//      final   String date = mDisplayDate.getText().toString().trim();
      // Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();


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

                              mDisplayDate.append(location +","+capacity);
                                //rooms.add(new AvailableRooms(id,capacity,location));
                                //String c = rooms.get(0).getCapacity();

                               // rooms.add(i,new AvailableRooms(id,capacity,location));
                                rooms.add(new AvailableRooms(id,capacity,location));
                               // mDisplayDate.append(c);
                            }

//                            String c = rooms.get(0).getCapacity();0
//
//                            mDisplayDate.append(c);

//                         My_customt_adapter myadpter= new  My_customt_adapter(rooms);
//                         //   ListView ls=(ListView)findViewById(R.id.listview);
//                            ls.setAdapter(myadpter);


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
                params.put("date", Date);
                params.put("time", Time);


                return params;
            }
        };

        //RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
      RequestQueue requestQueue= Volley.newRequestQueue(this);
      requestQueue.add(stringRequest);


    }


//

    public void show(View view) {
int n1,n2,n3;
        endmeeting = end.getText().toString().trim();
//      n1= Integer.parseInt(Time);
//      n2= Integer.parseInt(endmeeting);
//      n3 = n1+n2;
//         ending = String.valueOf(n3);
        if(endmeeting.isEmpty()){
            Toast.makeText(getApplicationContext(),"you have to enter endtime",Toast.LENGTH_LONG).show();
        }else if (Date.isEmpty()&& Time.isEmpty()){
            Toast.makeText(getApplicationContext(),"you have to enter time and date",Toast.LENGTH_LONG).show();
        }
        else{
            Intent passdata=new Intent(this,Main2Activity.class);
            passdata.putExtra("date",Date);
            passdata.putExtra("starttime",Time);
            passdata.putExtra("endtime",endmeeting);
            startActivity(passdata);
            Toast.makeText(getApplicationContext(),endmeeting,Toast.LENGTH_LONG).show();
        }



    }
}




