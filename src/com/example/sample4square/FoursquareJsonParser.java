package com.example.sample4square;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.FriendData;

import android.util.JsonReader;
import android.util.Log;

public class FoursquareJsonParser {
	static final String TAG = "FoursquareJsonParser";
	
	public static ArrayList<FriendData> readFriends(JsonReader reader) {
		ArrayList<FriendData> friendList = new ArrayList<FriendData>();
		
		try {
			reader.beginObject();
			Log.d(TAG, "readObject");
			while (reader.hasNext()) {
				String name = reader.nextName();
				Log.d(TAG, name);
				if (name.equals("response")) {
					
					reader.beginObject();
					// 最初がchecksumで次がfriends
					name = reader.nextName();
					Log.d(TAG, name);
					reader.skipValue();
					// friends
					name = reader.nextName();
					Log.d(TAG, name);
					reader.beginObject();
					name = reader.nextName();
					Log.d(TAG, name);
					// 最初がcountで次がitems
					reader.skipValue();
					name = reader.nextName();
					Log.d(TAG, name);
					reader.beginArray();
					while (reader.hasNext()) {
						friendList.add(readFriendData(reader));
					}
					reader.endArray();
					reader.endObject();
					reader.endObject();
				} else {
					reader.skipValue();
				}
			} 
			reader.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return friendList;
	}
	
	static FriendData readFriendData(JsonReader reader) throws IOException{
		FriendData fd = new FriendData();
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("id")) {
				fd.setId(reader.nextLong());
				Log.d(TAG, name);
			} else if (name.equals("firstName")) {
				fd.setFirstName(reader.nextString());
			} else if (name.equals("lastName")) {
				fd.setLastName(reader.nextString());
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return fd;
	}
	
}
