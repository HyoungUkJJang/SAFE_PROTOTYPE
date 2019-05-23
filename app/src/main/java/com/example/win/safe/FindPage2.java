package com.example.win.safe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindPage2 extends Activity {

    Button GoHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpage2);

        GoHomeBtn = (Button)findViewById(R.id.gohomebtn);
        GoHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(FindPage2.this,MainActivity.class);
                startActivity(it);
                finish();
            }
        });
    }
}
