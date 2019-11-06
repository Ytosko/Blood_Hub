package com.ytosko.bloodhub;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.ytosko.bloodhub.R;

import java.util.ArrayList;

/**
 * Created by Oclemmy on 4/12/2016 for ProgrammingWizards Channel.
 */
public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<ChatHelper1> movies;
    LayoutInflater inflater;

    public CustomAdapter(Context c, ArrayList<ChatHelper1> movies) {
        this.c = c;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater==null)
        {
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.castarray,parent,false);

        }

        MyViewHolder holder=new MyViewHolder(convertView);
        //BIND DATA
        holder.nameTxt.setText(movies.get(position).getName());
        holder.time.setText(movies.get(position).getTime());
        holder.msg.setText(movies.get(position).getLastmsg());

        return convertView;
    }
}