package com.example.win.safe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class ChangePwd extends Activity {

    Button ChBtn;
    EditText ChPwd1,ChPwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepage);
    ChBtn = (Button)findViewById(R.id.chbtn);
    ChPwd1 = (EditText) findViewById(R.id.chpwd1);
    ChPwd2 = (EditText)findViewById(R.id.chpwd2);

    ChBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(ChPwd1.getText().toString().equals(ChPwd2.getText().toString()) == true)
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            outstream.println("Change" + " " +ChPwd1.getText().toString());
                            outstream.flush();

                            instream = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
                            final String Recive = instream.readLine();

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(Recive.equals("true")==true)
                                    {
                                        Toast.makeText(ChangePwd.this,"변경하였습니다.",Toast.LENGTH_SHORT).show();
                                        Intent it = new Intent(ChangePwd.this,MainActivity.class);
                                        startActivity(it);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(ChangePwd.this,"변경 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        catch (Exception e) { e.printStackTrace(); }
                    }
                }).start();

            }
            else
            {
                Toast.makeText(ChangePwd.this,"새 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                ChPwd1.setText("");
                ChPwd2.setText("");
            }
        }
    });

    }
}
