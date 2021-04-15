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

import java.util.HashMap;
import java.util.Map;

public class Add_partner extends AppCompatActivity implements View.OnClickListener{

    EditText partnername, mEditTextTo;
       TextView pa;
    String meetingid, capacity, pname,date,start,end,room;
    Button add;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_partner);
        Bundle b = getIntent().getExtras();
        meetingid = b.getString("meeting_id");
       room= b.getString("room");
        capacity = b.getString("capacity");
      date = b.getString("date");
       start = b.getString("start");
       end= b.getString("end");
        partnername = (EditText) findViewById(R.id.et_mpartner);
        add=(Button)findViewById(R.id.b_addmpartner);
        add.setOnClickListener(this);
        pa=(TextView)findViewById(R.id.et_pa);
        mEditTextTo = findViewById(R.id.edit_text_to);

//pa.setText(meetingid +""+ capacity);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_my_partners,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONArray Array = new JSONArray(response);
                          pa.setText("Your Partners :"+"\n");
                            for(int i =0;i<Array.length();i++){
                                JSONObject object = Array.getJSONObject(i);
                                String email= object.getString("email");
                                String username = object.getString("username");
                                // data.setText("Your Partners :"+"\n");
                                pa.append("Name: "+ username +"  ,  "+ "EMAIL: "+email+ "\n");
                               // pa.append( "EMAIL: "+email+ "\n");
                               // mEditTextTo.append(email + ",");



                            }



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
                params.put("meeting_id",meetingid);

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


    public void addpartner() {
        pname = partnername.getText().toString().trim();
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

                params.put("meeting_id", meetingid);
                params.put("partner", pname);
                params.put("capacity", capacity);


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

    public void sendmail(){
        Intent passdata=new Intent(this,SendEmail.class);
        passdata.putExtra("meeting_id",meetingid );
        passdata.putExtra("date",date);
        passdata.putExtra("room",room);
        passdata.putExtra("start",start);

        passdata.putExtra("end",end);


        startActivity(passdata);
    }

    @Override
    public void onClick(View v) {
        if(v == add){
            pname = partnername.getText().toString().trim();
            checkisuser(pname);
        }

    }
}