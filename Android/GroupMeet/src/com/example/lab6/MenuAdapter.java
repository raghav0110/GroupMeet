package com.example.lab6;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuAdapter extends ArrayAdapter<String>{
Context context;

@Override
public int getCount() {
    return 2;
}

public MenuAdapter(Context c) {
    super(c, R.layout.meeting_list_item);
    context=c;

}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
    /*int height=LinearLayout.LayoutParams.MATCH_PARENT;
    int width=LinearLayout.LayoutParams.MATCH_PARENT;*/


    LayoutInflater inflater=(LayoutInflater) context.
            getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    View view=inflater.inflate(R.layout.meeting_list_item, parent, false);
    TextView tv=(TextView) view.findViewById(R.id.meeting_item);
    tv.setTextColor(Color.WHITE);
    tv.setText("Hello World");

    return view;
}   
}
