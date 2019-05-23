package com.example.win.safe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UseHistoryList extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<UseHistoryData> m_oData = null;
    private int nListCnt = 0;

    public UseHistoryList(ArrayList<UseHistoryData> data)
    {
        m_oData = data;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount()
    {
        return nListCnt;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }
    @Override
    public long getItemId(int position)
    {
        return 0;
    }
    @Override
    public View getView(int postion, View convertView, ViewGroup parent)
    {
        if(convertView==null)
        {
            final Context context = parent.getContext();
            if(inflater==null)
            {
                inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.usehitsoryitem,parent,false);
        }

        TextView uName = (TextView)convertView.findViewById(R.id.usename);
        TextView uOpen = (TextView)convertView.findViewById(R.id.useopen);
        TextView uClose = (TextView)convertView.findViewById(R.id.useclose);

        uName.setText(m_oData.get(postion).getUseName());
        uOpen.setText(m_oData.get(postion).getUseOpenTime());
        uClose.setText(m_oData.get(postion).getUseCloseTime());

        return convertView;
    }
}
