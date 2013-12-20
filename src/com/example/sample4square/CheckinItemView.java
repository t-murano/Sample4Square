package com.example.sample4square;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import model.CheckinData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.checkin_item)
public class CheckinItemView extends LinearLayout {
	static final String TAG = "CheckinItemView";
	@ViewById(R.id.checkinPhoto)
	ImageView checkinPhoto;
	
	@ViewById(R.id.venueName)
	TextView venueName;
	
	public CheckinItemView(Context context) {
		super(context);
	}
	
	public void bind(CheckinData checkinData) {
		Log.d(TAG, "start bind");
		venueName.setText(checkinData.getVenueName());
		Drawable d;
		try {
			URL url = new URL(checkinData.getPhotoUrl());
			InputStream istream = url.openStream();
			d = Drawable.createFromStream(istream, "venueImage");
			istream.close();
			checkinPhoto.setImageDrawable(d);
			Log.d(TAG, "passed bind method");
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
