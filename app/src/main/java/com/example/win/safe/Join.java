package com.example.win.safe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import static com.example.win.safe.MainActivity.handler;
import static com.example.win.safe.MainActivity.instream;
import static com.example.win.safe.MainActivity.outstream;
import static com.example.win.safe.MainActivity.socket;

public class Join extends Activity {

    EditText joinId;
    EditText joinPwd;
    EditText joinPwd2;
    EditText joinName;
    EditText joinMail;
    EditText joinPhone;
    EditText joinSerial;

    Button joinBtn2;
    Button idCheckBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
/*
        Thread WorkerThread = new Thread()
        {
            public void run()
            {
                int port = 9090;
                String host = "localhost";
                try {
                    socket = new Socket("220.87.15.128", 9090);
                    outstream = new PrintWriter(socket.getOutputStream());

                }
                catch (Exception e) {e.printStackTrace();}
            }
        };
        WorkerThread.start();
*/

        joinId = (EditText) findViewById(R.id.joinid);
        joinPwd = (EditText) findViewById(R.id.joinpwd);
        joinPwd2 = (EditText) findViewById(R.id.joinpwd2);
        joinName = (EditText) findViewById(R.id.joinname);
        joinMail = (EditText) findViewById(R.id.joinmail);
        joinPhone = (EditText) findViewById(R.id.joinphone);
        joinSerial = (EditText) findViewById(R.id.joinserial);
        joinBtn2 = (Button)findViewById(R.id.joinbtn2);
        idCheckBtn = (Button)findViewById(R.id.idcheck);

        idCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            outstream.println("Check"+" "+joinId.getText().toString());
                            outstream.flush();

                            instream = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                            final String Recive = instream.readLine();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(Recive.equals("true")==true)
                                    {
                                        Toast.makeText(Join.this,"사용 가능한 아이디 입니다.",Toast.LENGTH_SHORT).show();
                                        joinBtn2.setEnabled(true);
                                    }
                                    else
                                    {
                                        Toast.makeText(Join.this,"이미 사용중인 아이디 입니다.",Toast.LENGTH_SHORT).show();
                                        joinId.setText("");
                                    }
                                }
                            });
                        }
                        catch (Exception e) { e.printStackTrace(); }
                    }
                }).start();
            }
        });


        joinBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( joinPwd.getText().toString().equals(joinPwd2.getText().toString()) == true )
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                outstream.println("Join" + " " + joinId.getText().toString() + " " + joinPwd.getText().toString()
                                        + " " + joinName.getText().toString() + " " + joinMail.getText().toString() + " " + joinPhone.getText().toString() + " " + joinSerial.getText().toString());
                                outstream.flush();
                                Log.d("Join", "보냇다");

                                instream = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
                                final String Recive = instream.readLine();
                                Log.d("Server", Recive);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Toast.makeText(MainActivity.this, Recive, Toast.LENGTH_SHORT).show();
                                        if (Recive.equals("JoinOk") == true) {
                                            Toast.makeText(Join.this, Recive, Toast.LENGTH_SHORT).show();
                                            Intent it = new Intent(Join.this, MainActivity.class);
                                            startActivity(it);
                                        } else {
                                            Toast.makeText(Join.this, "시리얼 번호가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                                            joinSerial.setText("");
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else
                {
                    Toast.makeText(Join.this,"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                    joinPwd.setText("");
                    joinPwd2.setText("");
                }
            }
        });


        //   joinId= (EditText)findViewById(R.id.joinid);
      // joinPwd= (EditText)findViewById(R.id.joinpwd);
      //  joinName= (EditText)findViewById(R.id.joinname);
      // joinAge= (EditText)findViewById(R.id.joinage);
      // joinPhone= (EditText)findViewById(R.id.joinphone);
      // joinBtn2 = (Button)findViewById(R.id.joinbtn2);
        /*
        joinBtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String JoinId = joinId.getText().toString();
                String JoinPwd = joinPwd.getText().toString();
                String JoinName = joinName.getText().toString();
                String JoinAge = joinAge.getText().toString();
                String JoinPhone = joinPhone.getText().toString();

                try{
                    String result2 = new CunstomTask2().execute(JoinId,JoinPwd,JoinName,JoinAge,JoinPhone,"join").get();
                    if(result2.equals("true"))
                    {
                        Toast.makeText(Join.this,"가입성공",Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(Join.this,MainActivity.class);
                        startActivity(it);
                        finish();
                    }
                    else if(result2.equals("false"))
                    {
                        Toast.makeText(Join.this,"가입실패",Toast.LENGTH_SHORT).show();
                        joinId.setText("");
                        joinPwd.setText("");
                        joinName.setText("");
                        joinAge.setText("");
                        joinPhone.setText("");
                    }
                }
                catch (Exception e) {}

            }});

    }
    class CunstomTask2 extends AsyncTask<String, Void, String> {
        String sendMsg2, reciveMsg2;
        @Override
        protected String doInBackground(String...strings)
        {
            try {
                String str2;
                URL url = new URL("http://192.168.56.1:8090/Data/data.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg2 ="id="+strings[0]+"&pwd="+strings[1]+"&name="+strings[2]+"&age="+strings[3]+"&phone="+strings[4]+"&type="+strings[5];

                osw.write(sendMsg2);
                osw.flush();

                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp2 = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader2 = new BufferedReader(tmp2);
                    StringBuffer buffer2 = new StringBuffer();
                    while ((str2 = reader2.readLine()) != null) {
                        buffer2.append(str2);
                    }
                    reciveMsg2= buffer2.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return reciveMsg2;
        }
         */


    }

}
