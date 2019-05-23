package com.example.win.safe;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText Inputpwd, Inputid;    // 아이디,비밀번호 입력
    Button loginBtn;              // 로그인 버튼
    TextView joinBtn;            // 회원가입 버튼
    TextView findBtn;           //비밀번호 찾기 버튼
    static Socket socket;
    static BufferedReader instream;
    static PrintWriter outstream; // 아웃스트림
    static Handler handler = new Handler(); //Post방식
    static ArrayList<UseHistoryData> HistoryData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1); // 권한요청 파일쓰기

        Inputid = (EditText) findViewById(R.id.inputid);
        Inputpwd = (EditText) findViewById(R.id.inputpwd);
        loginBtn = (Button) findViewById(R.id.loginbtn);
        joinBtn = (TextView) findViewById(R.id.joinbtn);
        findBtn = (TextView) findViewById(R.id.findbhn);

        Thread WorkerThread = new Thread()
        {
            public void run()
            {
                int port = 9090;
                String host = "localhost";
                try {
                   socket = new Socket("14.55.224.175", 9090);
                    outstream = new PrintWriter(socket.getOutputStream());

                }
                catch (Exception e) {e.printStackTrace();}
            }
        };
        WorkerThread.start();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String id = Inputid.getText().toString();
                            String pwd = Inputpwd.getText().toString();
                            outstream.println("Login"+" "+id+" "+pwd);
                            outstream.flush();
                            Log.d("ClientThread", "보냇다");
                            instream = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
                            final String Recive = instream.readLine();
                            Log.d("Server", Recive);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Toast.makeText(MainActivity.this, Recive, Toast.LENGTH_SHORT).show();
                                    if( Recive.equals("true") == true)
                                    {
                                        Intent it = new Intent(MainActivity.this,MainPage1.class);
                                        startActivity(it);
                                    }
                                    else if(Recive.equals("false") == true)
                                    {
                                        Toast.makeText(MainActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(Recive.equals("noId") == true)
                                    {
                                        Toast.makeText(MainActivity.this, "정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        catch (Exception e ){}
                    }
                }).start();
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, Join.class);
                startActivity(it);
                // finish(); 종료가됨
            }
        });
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, FindPage1.class);
                startActivity(it);
                finish(); //종료가됨

            }
        });
    }

/*
    class ClientThread extends Thread {
        public void run()
        {
            int port = 9090;
            String host = "localhost";
            try {
                Socket socket = new Socket(host, port);

                PrintWriter outstream = new PrintWriter(socket.getOutputStream());
                outstream.write("안녕");
                outstream.flush();

                Log.d("ClientThread","보냇다");
                DataInputStream instream = new DataInputStream(socket.getInputStream());
                String Recive = instream.readUTF();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"하이",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch (Exception e) {e.printStackTrace();}
        }
    }
*/
}



