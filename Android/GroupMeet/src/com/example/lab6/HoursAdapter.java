package com.example.lab6;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HoursAdapter extends BaseAdapter {

	private Context mContext;
	private boolean isSelected[];
	private String hours[];
	//GroupMeet gm ;

	public HoursAdapter(Context c, boolean selected[], String hours[], GroupMeet gm){
		this.isSelected = selected;
		this.mContext = c;
		this.hours = hours;
		//this.gm = gm;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hours.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
	     if(convertView==null){
	        LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = vi.inflate(R.layout.hourview_item, null);
	        holder = new ViewHolder();
	        holder.hours =(TextView) convertView.findViewById(R.id.hours_text);
	        convertView.setTag(holder);
	     }else{
	        holder = (ViewHolder)convertView.getTag(); 
	     }
	     if(isSelected[arg0])
	      holder.hours.setBackgroundColor(Color.GREEN);
	     else
	    	 holder.hours.setBackgroundColor(Color.WHITE);
	     if(arg0 > hours.length -1)
	    	 holder.hours.setText("N/A");
	     holder.hours.setText(hours[arg0]);
	    return convertView;
	}
	
	static class ViewHolder{
		TextView hours;
	}
	

}
