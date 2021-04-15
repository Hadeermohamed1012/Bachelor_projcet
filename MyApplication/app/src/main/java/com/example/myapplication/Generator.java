package com.example.myapplication;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class Generator extends AppCompatActivity {
  TextView text;
    Button gen_btn;
    ImageView image;
    String text2Qr,room,date,time,end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);
        Bundle b =getIntent().getExtras();
        room =  b.getString("room_id");
        date = b.getString("date");
        time = b.getString("start");
       end = b.getString("endtime");

        text = (TextView) findViewById(R.id.text);
        text.setText("Room : "+room+ " "+"start : "+time+" "+"end: "+end+" "+"date : "+date);

        gen_btn = (Button) findViewById(R.id.gen_btn);
        image = (ImageView) findViewById(R.id.image);
        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // text2Qr = text.getText().toString().trim();
                text2Qr = "Room"+room+","+time+","+end+","+date;
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);
                }
                catch (WriterException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
