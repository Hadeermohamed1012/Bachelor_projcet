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
import com.example.myapplication.Model.PartnerSchedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class View_partner_Schedule extends AppCompatActivity implements View.OnClickListener {

Button show;
EditText pname;

    TextView data;
String name;
boolean flag ;
String message ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_partner__schedule);
        show = (Button) findViewById(R.id.b_view);
        show.setOnClickListener(this);
        pname = (EditText) findViewById(R.id.et_pname);
    data = (TextView)findViewById(R.id.tvp) ;
        name = pname.getText().toString().trim();
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

//    public void show_partner_schedule(){
//
//       pname = (EditText) findViewById(R.id.et_pname);
//      // data = (TextView)findViewById(R.id.tv_view) ;
//      name = pname.getText().toString().trim();
//
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Constants.URL_show_partnerschedule ,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        data.append(name);
//
//
//                        try {
//
//
//                            JSONArray Array = new JSONArray(response);
//
//                            for(int i =0;i<Array.length();i++){
//                                JSONObject object = Array.getJSONObject(i);
//                                String date= object.getString("date");
//                                String start = object.getString("start");
//                                String end = object.getString("end");
//
//
//
////                              String message = object.getString("message");
////                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
//                               // data.append(name);
//
//
//
//                            }
//                            //Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//
//                        Toast.makeText(
//                                getApplicationContext(),
//                                error.getMessage(),
//                                Toast.LENGTH_LONG
//                        ).show();
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("user",name);
//
//                return params;
//            }
//
//        };
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
//    }
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
                               check();
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
    public void check(){
                                     Intent data = new Intent(this, Main3Activity.class);

              data.putExtra("partner", name);
//
               startActivity(data);
//                           }
    }
    @Override
    public void onClick(View v) {
        if (v== show){

            name = pname.getText().toString().trim();
checkisuser(name);




//            if (!checkisuser(name)) {
//                flag=true;
//                Toast.makeText(getApplicationContext(),"your partner's name is invalid",Toast.LENGTH_LONG).show();
//            }
//             else {
//                Intent data = new Intent(this, Main3Activity.class);
//
//                data.putExtra("partner", name);
//
//                startActivity(data);
//
//                // Toast.makeText(getApplicationContext(),"heloooo"+name,Toast.LENGTH_LONG).show();
//
//            }

         // show_partner_schedule();

        }

    }
}
