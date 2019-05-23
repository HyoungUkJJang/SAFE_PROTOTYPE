package com.example.win.safe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.example.win.safe.MainActivity.handler;
import static com.example.win.safe.MainActivity.instream;
import static com.example.win.safe.MainActivity.outstream;
import static com.example.win.safe.MainActivity.socket;

public class OTP extends Activity {

    TextView OtpText;
    Button OtpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otppage);
    OtpText = (TextView)findViewById(R.id.otptext);
    OtpBtn = (Button)findViewById(R.id.otpbtn2);

    OtpBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try
                    {
                        outstream.println("OTP");
                        outstream.flush();
                        instream  = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                        final String Recive = instream.readLine();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                OtpText.setText(Recive);
                                Toast.makeText(OTP.this,"OTP생성완료",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    catch (Exception e) {}


                }
            }).start();
        }
    });


    }
}
