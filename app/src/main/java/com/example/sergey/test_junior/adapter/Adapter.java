package com.example.sergey.test_junior.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sergey.test_junior.utils.ProgressButton;
import com.example.sergey.test_junior.R;
import com.example.sergey.test_junior.model.ListData;

import java.util.List;

public class Adapter extends BaseAdapter {
    private List mList;
    private LayoutInflater mInflater;

    public Adapter(Context context, List list) {
        this.mList = list;
        mInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_list, parent, false);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        ListData currentData = (ListData) getItem(position);
        viewHolder.tvTitle.setText(String.valueOf(currentData.getmStringNumber()));
        viewHolder.tvButton.setRatio(currentData.getmStringPercent());
        return convertView;
    }

    private class MyViewHolder {
        TextView tvTitle;
        ProgressButton tvButton;
        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.textView);
            tvButton = (ProgressButton) item.findViewById(R.id.button);
        }
    }
}
