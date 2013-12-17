package com.example.sample4square;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.CheckinData;
import model.FriendData;

import android.util.JsonReader;
import android.util.Log;

public class FoursquareJsonParser {
	static final String TAG = "FoursquareJsonParser";
	
	public static ArrayList<FriendData> readFriends(JSONObject rootObject) {
		ArrayList<FriendData> list = new ArrayList<FriendData>();
		
		try {
			JSONObject responseObject = rootObject.getJSONObject("response");
			JSONObject friendsObject = responseObject.getJSONObject("friends");
			JSONArray itemsArray = friendsObject.getJSONArray("items");
			for (int i = 0; i < itemsArray.length(); i++) {
				FriendData fd = new FriendData();
				JSONObject tmpObject = itemsArray.getJSONObject(i);
				fd.setId(tmpObject.getLong("id"));
				fd.setLastName(tmpObject.getString("lastName"));
				fd.setFirstName(tmpObject.getString("firstName"));
				// facebookの固有値を取得
				JSONObject contactObject = tmpObject.getJSONObject("contact");
				fd.setFacebook(contactObject.getLong("facebook"));
				// アカウントのサムネのurlの取得
				JSONObject photoObject = tmpObject.getJSONObject("photo");
				String prefix = photoObject.getString("prefix");
				String suffix = photoObject.getString("suffix");
				fd.setPhotoUrl(prefix, suffix);
				list.add(fd);
			}
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static ArrayList<CheckinData> readCheckins(JSONObject rootObject) {
		ArrayList<CheckinData> list = new ArrayList<CheckinData>();
		
		try {
			JSONObject responseObject = rootObject.getJSONObject("response");
			JSONArray recentArray = responseObject.getJSONArray("recent");
			for (int i = 0; i < recentArray.length(); i++) {
//				Log.d(TAG, "in recentArray");
				JSONObject tmpObject = recentArray.getJSONObject(i);
				// checkinが写真付きかどうか調べる count > 0
				JSONObject photoObject = tmpObject.getJSONObject("photos");
				if (photoObject.getInt("count") > 0) {
					Log.d(TAG, "photos count > 0");
					// categoriesのiconのURL(prefix)に"/food\/"を含むかどうか調べる
					JSONObject venueObject = tmpObject.getJSONObject("venue");
					JSONArray categoriesArray = venueObject.getJSONArray("categories");
					JSONObject categoriesObject = categoriesArray.getJSONObject(0);
					JSONObject iconObject = categoriesObject.getJSONObject("icon");
					// "/food\/"を含んでいる場合のみCheckinDataを作成する
					if (iconObject.getString("prefix").indexOf("food") != -1) {
						CheckinData cd = new CheckinData();
						cd.setCheckinId(tmpObject.getString("id"));
						cd.setVenueName(venueObject.getString("name"));
						cd.setVenueId(venueObject.getString("id"));
						
						JSONArray photoArray = photoObject.getJSONArray("items");
						JSONObject photoObject2 = photoArray.getJSONObject(0);
						cd.setPhotoUrl(photoObject2.getString("prefix"), photoObject2.getString("suffix"));
						
						list.add(cd);
					}					
				}
			}
			
 		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
