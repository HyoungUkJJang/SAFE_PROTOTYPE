package com.example.win.safe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.example.win.safe.MainActivity.handler;
import static com.example.win.safe.MainActivity.instream;
import static com.example.win.safe.MainActivity.outstream;
import static com.example.win.safe.MainActivity.socket;

public class FindPage1 extends Activity {

    Button findSubmit;
    EditText findId,findName,findMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpage1);

        findId = (EditText)findViewById(R.id.findid);
        findName = (EditText)findViewById(R.id.findname);
        findMail = (EditText)findViewById(R.id.findmail);
        findSubmit = (Button)findViewById(R.id.findsubmit);

        findSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            outstream.println("Find" +" "+ findId.getText().toString() + " " + findName.getText().toString()+ " " +findMail.getText().toString());
                            outstream.flush();
                            Log.d("Find", "보냇다");

                            instream = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
                            final String Recive = instream.readLine();
                            Log.d("Server", Recive);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Toast.makeText(MainActivity.this, Recive, Toast.LENGTH_SHORT).show();
                                    if (Recive.equals("true") == true) {
                                        Toast.makeText(FindPage1.this, Recive, Toast.LENGTH_SHORT).show();
                                        Intent it = new Intent(FindPage1.this,FindPage2.class);
                                        startActivity(it);
                                        finish();
                                    } else {
                                        Toast.makeText(FindPage1.this, "일치하는 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }
}
