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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.Model.UserSchedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendEmail extends AppCompatActivity {
String meetingid,date,start,end,room;
TextView data;
  EditText mEditTextTo;
   EditText mEditTextSubject;
   EditText mEditTextMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        Bundle b =getIntent().getExtras();
      meetingid = b.getString("meeting_id");
        date = b.getString("date");
      start = b.getString("start");
       end= b.getString("end");
        room = b.getString("room");

data = (TextView)findViewById(R.id.tv_mypartners);
        mEditTextTo = findViewById(R.id.edit_text_to);
        mEditTextSubject = findViewById(R.id.edit_text_subject);
        mEditTextMessage = findViewById(R.id.edit_text_message);

        Button buttonSend = findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_my_partners,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONArray Array = new JSONArray(response);
                            data.setText("Your Partners :"+"\n");
                            for(int i =0;i<Array.length();i++){
                                JSONObject object = Array.getJSONObject(i);
                                String email= object.getString("email");
                                String username = object.getString("username");
                               // data.setText("Your Partners :"+"\n");
                                //data.append("Name: "+ username +"  ,  "+ "EMAIL: "+email+ "\n");

                                mEditTextTo.append(email+",");
                                mEditTextSubject.setText("our meeting ");
                                mEditTextMessage.setText( "Room : "+room+" date : "+date +" "+ "time :" +start +" "+"end : "+end );

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
    private void sendMail() {
        String recipientList = mEditTextTo.getText().toString();
        String[] recipients = recipientList.split(",");

        String subject = mEditTextSubject.getText().toString();
        String message = mEditTextMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }

}
