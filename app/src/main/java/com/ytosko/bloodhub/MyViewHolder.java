package com.ytosko.bloodhub;


import android.view.View;
import android.widget.TextView;
import com.ytosko.bloodhub.R;

public class MyViewHolder {

    TextView nameTxt;
    TextView time;
    TextView msg;

    public MyViewHolder(View itemView) {

        nameTxt= (TextView) itemView.findViewById(R.id.name);
        time= (TextView) itemView.findViewById(R.id.time);
        msg =  itemView.findViewById(R.id.msg);


    }
}