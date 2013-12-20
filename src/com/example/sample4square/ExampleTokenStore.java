/*
 * Copyright (C) 2013 Foursquare Labs, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.sample4square;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Used as an example of holding onto a fetched token. You'd want to persist
 * the token in a real application so the user does not have to authenticate
 * every time they use the app.
 * 
 * Note that you should encrypt the token when persisting.
 * 
 * @date 2013-06-01
 */
public class ExampleTokenStore {
	private static ExampleTokenStore sInstance;
	private final String PREF_NAME = "USER_TOKEN"; 
	private final String TOKEN_KEY = "user_token";
	private String token;
	
	public static ExampleTokenStore get() {
		if (sInstance == null) {
			sInstance = new ExampleTokenStore();
		}
		
		return sInstance;
	}
	
	public String getToken(Context context) {
		SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		if (pref.contains(TOKEN_KEY)) {
			token = pref.getString(TOKEN_KEY, null);
		}
		return token;
	}
	
	public void setToken(Context context, String token) {
		this.token = token;
		SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(TOKEN_KEY, token);
		editor.commit();
	}
}