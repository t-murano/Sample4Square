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
	
	@ViewById(R.id.userPhoto)
	ImageView userPhoto;
	
	public CheckinItemView(Context context) {
		super(context);
	}
	
	@Background
	public void bind(CheckinData checkinData) {
		Log.d(TAG, "start bind");

		try {
			URL checkinUrl = new URL(checkinData.getPhotoUrl());
			URL userUrl = new URL(checkinData.getUserPhotoURL());
			InputStream checkinStream = checkinUrl.openStream();
			InputStream userStream = userUrl.openStream();
			Drawable checkinDrawable = Drawable.createFromStream(checkinStream, "venueImage");
			Drawable userDrawable = Drawable.createFromStream(userStream, "userImage");
			checkinStream.close();
			userStream.close();
			setImage(checkinData, checkinDrawable, userDrawable);
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
	void setImage(CheckinData checkinData, Drawable checkinDrawable, Drawable userDrawable) {
		String venueName = checkinData.getVenueName();
		String friendName = checkinData.getFriendName();
		String message = checkinData.getMessage();
		String venueAddress = checkinData.getVenueAddress();
		int checkinCount = checkinData.getCheckinCount();
		StringBuilder builder = new StringBuilder();
		builder.append("店名 : " + venueName + "\n");
		builder.append("投稿者 : " + friendName + "\n");
		builder.append("お店の総チェックイン数 : " + checkinCount + "\n");
		builder.append("お店の住所 : " + venueAddress + "\n");
		builder.append("メッセージ : " + message + "\n");
		checkinText.setText(builder.toString());
		checkinPhoto.setImageDrawable(checkinDrawable);
		userPhoto.setImageDrawable(userDrawable);
	}
}
