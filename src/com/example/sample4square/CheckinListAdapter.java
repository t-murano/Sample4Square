package com.example.sample4square;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

import model.CheckinData;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

@EBean
public class CheckinListAdapter extends ArrayAdapter<CheckinData> {
	@RootContext
	Context context;
	
	public CheckinListAdapter(Context context) {
		super(context, 0);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CheckinItemView view;
		if (convertView == null) {
			view = CheckinItemView_.build(context);
		} else {
			view = (CheckinItemView)convertView;
		}
		view.bind(getItem(position));
		return view;
	}
}
