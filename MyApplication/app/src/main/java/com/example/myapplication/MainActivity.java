package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private ProgressDialog progressDialog;

    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextUsername = (EditText) findViewById(R.id.etuser);
        editTextEmail = (EditText) findViewById(R.id.etmail);
        editTextPassword = (EditText) findViewById(R.id.etpass);

        textViewLogin = (TextView) findViewById(R.id.tvLogin);

        buttonRegister = (Button) findViewById(R.id.bregister);

        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

     progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //Log.i(TAG, "onResponse: response.tostring()");

                        try {

                            JSONObject x = new  JSONObject(response);
                             // String message = jsonObject.getString("message");
    Toast.makeText(getApplicationContext(),x.getString("message"),Toast.LENGTH_LONG).show();

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
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);

                return params;
            }
        };


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public void registersuccessfully(){
        startActivity(new Intent(this, Login.class));

    }

    public void onClick(View v) {
        if (v == buttonRegister) {
            if(editTextUsername.getText().toString().trim().equals("")){
                Toast.makeText(getApplicationContext(), " you should enter your username", Toast.LENGTH_LONG).show();
            }
        else if(editTextEmail.getText().toString().trim().equals("")){
                Toast.makeText(getApplicationContext(), " you should enter your mail", Toast.LENGTH_LONG).show();
            }
            else if(editTextPassword.getText().toString().trim().equals("")){
                Toast.makeText(getApplicationContext(), " you should enter your password", Toast.LENGTH_LONG).show();
            } else if(editTextPassword.getText().toString().trim().length()<6){
                Toast.makeText(getApplicationContext(), " you should enter 6 charachters or digits", Toast.LENGTH_LONG).show();
            }
            else {

                registerUser();
            }
        }
            if(v == textViewLogin) {
                startActivity(new Intent(this, Login.class));
            }

         //  Toast.makeText(Context(), "successfully registered", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
