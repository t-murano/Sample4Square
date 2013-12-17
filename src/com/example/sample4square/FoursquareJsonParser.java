package com.example.sample4square;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.FriendData;

import android.util.JsonReader;
import android.util.Log;

public class FoursquareJsonParser {
	static final String TAG = "FoursquareJsonParser";
	
	public static ArrayList<FriendData> readFriends(JSONObject jsonObject0) {
		ArrayList<FriendData> list = new ArrayList<FriendData>();
		
		try {
			JSONObject jsonObject1 = jsonObject0.getJSONObject("response");
			JSONObject jsonObject2 = jsonObject1.getJSONObject("friends");
			JSONArray jsonArray = jsonObject2.getJSONArray("items");
			for (int i = 0; i < jsonArray.length(); i++) {
				FriendData fd = new FriendData();
				JSONObject jsonObject3 = jsonArray.getJSONObject(i);
				fd.setId(jsonObject3.getLong("id"));
				fd.setLastName(jsonObject3.getString("lastName"));
				fd.setFirstName(jsonObject3.getString("firstName"));
				// facebookの固有値を取得
				JSONObject jsonContact = jsonObject3.getJSONObject("contact");
				fd.setFacebook(jsonContact.getLong("facebook"));
				// アカウントのサムネのurlの取得
				JSONObject jsonPhoto = jsonObject3.getJSONObject("photo");
				
				list.add(fd);
			}
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return list;
	}

	
}
