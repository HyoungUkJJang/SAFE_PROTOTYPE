package com.example.win.safe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import static com.example.win.safe.MainActivity.HistoryData;

public class UseHistoryView extends Activity {

    private ListView HistoryList = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usehistorylist);

        HistoryList = (ListView)findViewById(R.id.uselist);
        UseHistoryList oAdapter = new UseHistoryList(HistoryData);
        HistoryList.setAdapter(oAdapter);
    }


    }
