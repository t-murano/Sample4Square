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

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.checkin_item)
public class CheckinItemView extends LinearLayout {
	static final String TAG = "CheckinItemView";
	@ViewById(R.id.checkinPhoto)
	ImageView checkinPhoto;
	
	@ViewById(R.id.checkinText)
	TextView checkinText;
	
	public CheckinItemView(Context context) {
		super(context);
	}
	
	@Background
	public void bind(CheckinData checkinData) {
		Log.d(TAG, "start bind");
		Drawable d = null;
		URL url;

		try {
			url = new URL(checkinData.getPhotoUrl());
			InputStream istream = url.openStream();
			d = Drawable.createFromStream(istream, "venueImage");
			istream.close();
			setImage(checkinData, d);
			Log.d(TAG, "passed bind method");
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}


	
	@UiThread
	void setImage(CheckinData checkinData, Drawable d) {
		String venueName = checkinData.getVenueName();
		String friendName = checkinData.getFriendName();
		StringBuilder builder = new StringBuilder();
		builder.append("店名 : " + venueName + "\n");
		builder.append("投稿者 : " + friendName + "\n");
		checkinText.setText(builder.toString());
		checkinPhoto.setImageDrawable(d);
	}
}
