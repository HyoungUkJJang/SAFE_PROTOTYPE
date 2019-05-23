package com.example.win.safe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Delayed;

import static com.example.win.safe.MainActivity.HistoryData;
import static com.example.win.safe.MainActivity.handler;
import static com.example.win.safe.MainActivity.instream;
import static com.example.win.safe.MainActivity.outstream;
import static com.example.win.safe.MainActivity.socket;

public class MainPage1 extends Activity {

    ImageView CCTVGO,OTPGO,CHANGEGO,EMERGENCY,USEHISTORY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage1);

        CCTVGO = (ImageView)findViewById(R.id.cctvgo);
        OTPGO = (ImageView)findViewById(R.id.otpbtn);
        CHANGEGO = (ImageView)findViewById(R.id.changebtn);
        EMERGENCY = (ImageView)findViewById(R.id.emergencybtn);
        USEHISTORY = (ImageView)findViewById(R.id.usehistorybtn);

        CCTVGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainPage1.this,CCTV.class);
                startActivity(it);
            }
        });

        OTPGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainPage1.this,OTP.class);
                startActivity(it);
            }
        });
        CHANGEGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainPage1.this,ChangePwd.class);
                startActivity(it);
            }
        });
        USEHISTORY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final ArrayList<UseHistoryData> PutHistroy = new ArrayList<>();
                            outstream.println("List");
                            outstream.flush();

                            instream = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
                            String Recive;
                            HistoryData.clear();
                            while (true) {
                                Recive = instream.readLine();
                                if (Recive.equals("null")) break;

                                String[] array = Recive.split(" ");
                                UseHistoryData AddData = new UseHistoryData();
                                AddData.setUseName(array[0]);
                                AddData.setUseOpenTime(array[1]);
                                AddData.setUseCloseTime(array[2]);
                                HistoryData.add(AddData);
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                   // final ArrayList<UseHistoryData> PUT = PutHistroy;
                                    Intent it = new Intent(MainPage1.this,UseHistoryView.class);
                                    //it.putParcelableArrayListExtra("key",PutHistroy);
                                    startActivity(it);
                                }
                            });
                        }
                        catch (Exception e) {e.printStackTrace();}
                    }
                }).start();
            }
        });
        EMERGENCY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder EmergencyGo = new AlertDialog.Builder(MainPage1.this);
                EmergencyGo.setMessage("112에 신고 하시겠습니까?");
                EmergencyGo.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent Ego = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"112"));
                        startActivity(Ego);
                    }
                });
                EmergencyGo.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainPage1.this,"취소하였습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                EmergencyGo.setTitle("긴급!");
                EmergencyGo.show();
            }
        });

    }
}
