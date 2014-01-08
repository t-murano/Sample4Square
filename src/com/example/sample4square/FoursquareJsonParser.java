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
	static final String PHOTO_SIZE = "300x300";
	
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
				if (photoObject.getInt("count") > 0) { // デバッグ用に-1にする
					Log.d(TAG, "photos count > 0");
					// categoriesのiconのURL(prefix)に"food"を含むかどうか調べる
					JSONObject venueObject = tmpObject.getJSONObject("venue");
					JSONArray categoriesArray = venueObject.getJSONArray("categories");
					JSONObject categoriesObject = categoriesArray.getJSONObject(0);
					JSONObject iconObject = categoriesObject.getJSONObject("icon");
					// "food"を含んでいる場合のみCheckinDataを作成する
					if (iconObject.getString("prefix").indexOf("food") != -1) { //デバッグ用に||trueにする
						CheckinData cd = new CheckinData();
						cd.setCheckinId(tmpObject.getString("id"));
						cd.setVenueName(venueObject.getString("name"));
						cd.setVenueId(venueObject.getString("id"));
						
						if (tmpObject.has("shout")) {
							cd.setMessage(tmpObject.getString("shout"));
						}
						
						JSONObject locationObject = venueObject.getJSONObject("location");
						cd.setLng(locationObject.getDouble("lng"));
						cd.setLat(locationObject.getDouble("lat"));
						JSONObject statsObject = venueObject.getJSONObject("stats");
						cd.setCheckinCount(statsObject.getInt("checkinsCount"));
						if (locationObject.has("state") && locationObject.has("city") && locationObject.has("address")) {
							String state = locationObject.getString("state");
							String city = locationObject.getString("city");
							String address = locationObject.getString("address");
							cd.setVenueAddress(state, city, address);
						}
						
						JSONObject userObject = tmpObject.getJSONObject("user");
						String lastName = userObject.getString("lastName");
						String firstName = userObject.getString("firstName");
						cd.setFriendName(lastName, firstName);
						JSONObject userPhotoObject = userObject.getJSONObject("photo");
						String userImageURL = generateImageURL(userPhotoObject.getString("prefix"), userPhotoObject.getString("suffix"));
						cd.setUserPhotoURL(userImageURL);
						
						JSONArray photoArray = photoObject.getJSONArray("items");
						JSONObject photoObject2 = photoArray.getJSONObject(0);
						cd.setPhotoURL(photoObject2.getString("prefix"), photoObject2.getString("suffix"));
						
						list.add(cd);
					}					
				}
			}
			
 		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	static String generateImageURL(String prefix, String suffix) {
		prefix = prefix.replace("\\", "");
		suffix = suffix.replace("\\", "");
		return prefix + PHOTO_SIZE + suffix;
	}
}
