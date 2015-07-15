package com.orange.guleh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kevin on 15/7/2015.
 */
public class ListRowAdapter extends BaseAdapter {
    private static ArrayList<Product> list;
    private LayoutInflater l_Inflater;

    public ListRowAdapter(Context context, ArrayList<Product> results) {
        list = results;
        l_Inflater = LayoutInflater.from(context);
    }

    public void add(ArrayList<Product> results) {
        list = results;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
			/*
			 * instantiate viewholder if convertview is null
			 */
            convertView = l_Inflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.code = (TextView) convertView.findViewById(R.id.codeText);

            convertView.setTag(holder);
        } else {
			/*
			 * just get tags from convertView instead
			 */
            holder = (ViewHolder) convertView.getTag();
        }

        holder.code.setText(list.get(position).getCode() + "  -  $ " + list.get(position).getPrice());
        return convertView;
    }

    static class ViewHolder {
        TextView code;
    }
}
