package com.example.sample4square;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import model.CheckinData;
import model.FriendData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.LayoutRes;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

@EFragment(R.layout.request_fragment)
public class RequestFragment extends Fragment {

	static final String TAG = "RequestFragment";
	
	static final String URL_FRIENDS = "https://api.foursquare.com/v2/users/self/friends";
	static final String URL_CHECKINS = "https://api.foursquare.com/v2/checkins/recent";
	static final String TOKEN_QUALIFIER = "?oauth_token=";
	static final int LIB_VERSION = 20131016;
	static final int FRIENDS_DATA = 1;
	static final int CHECKINS_DATA = 2;
	
	@ViewById
	ListView checkinList;
	
	@ViewById(R.id.resultText)
	TextView resultText;
	
	@AfterViews
	void onCreateListView() {
		checkinList.setAdapter(adapter);
	}
	
	@Bean
	CheckinListAdapter adapter;


	@Click(R.id.btnGetFriendsData)
	void onClickDataButton() {
		ExampleTokenStore ets = ExampleTokenStore.get();
		String token = ets.getToken(getActivity());
		String uri = URL_FRIENDS + TOKEN_QUALIFIER + token + "&limit=100" + "&v=" + LIB_VERSION;
		getHttpResponse(uri, token, FRIENDS_DATA);
	}
	
	@Click(R.id.btnGetFriendsCheckins)
	void onClickCheckinsButton() {
		ExampleTokenStore ets = ExampleTokenStore.get();
		String token = ets.getToken(getActivity());
		String uri = URL_CHECKINS + TOKEN_QUALIFIER + token + "&v=" + LIB_VERSION;
		getHttpResponse(uri, token, CHECKINS_DATA);
	}
	
	@Background
	void getHttpResponse(String uri, String token, int identifier) {
		String strResponse = "unprocessed";
		HttpClient hClient = new DefaultHttpClient();
		if (TextUtils.isEmpty(token)) {
			strResponse = "トークンが存在しません。";
		} else {
			try {
				HttpGet hGet = new HttpGet(uri);
				HttpResponse hResponse = hClient.execute(hGet);
				HttpEntity hEntity = hResponse.getEntity();
				JSONObject jsonObject = new JSONObject(EntityUtils.toString(hEntity));
				// jsonの全体を見たいときに実行
//				strResponse = jsonObject.toString(4);
				
//				InputStream stream = hEntity.getContent();
//				InputStreamReader isReader = new InputStreamReader(stream);
//				JsonReader jsonReader = new JsonReader(isReader);
				
				// jsonを解析したいときに実行
				// 呼び出し元により処理を区別する
				if (identifier == FRIENDS_DATA) {
					ArrayList<FriendData> friendList = FoursquareJsonParser.readFriends(jsonObject);
					if (friendList != null) {
						StringBuilder builder = new StringBuilder();
						for (FriendData fd : friendList) {
							builder.append("id : " + fd.getId());
							builder.append(", name : " + fd.getLastName());
							builder.append(fd.getFirstName());
							builder.append(", facebook : " + fd.getFacebook());
							builder.append(", photoUrl : " + fd.getPhotoUrl());
							builder.append("\n");
						}
						strResponse = builder.toString();
					}
				} else if (identifier == CHECKINS_DATA) {
					ArrayList<CheckinData> checkinList = FoursquareJsonParser.readCheckins(jsonObject);
					if (checkinList != null) {
						onCreateCheckinList(checkinList);
						StringBuilder builder = new StringBuilder();
						for (CheckinData cd : checkinList) {
							builder.append("checkinId : " + cd.getCheckinId() + "\n");
							builder.append("venueName : " + cd.getVenueName() + "\n");
							builder.append("venueId : " + cd.getVenueId() + "\n");
							builder.append("url : " + cd.getPhotoUrl() + "\n");
							builder.append("friendName : " + cd.getFriendName() + "\n");
							builder.append("lng : " + cd.getLng() + "\n");
							builder.append("lat : " + cd.getLat() + "\n");
							builder.append("\n\n");
						}
						strResponse = builder.toString();
					}
				}

			} catch (ClientProtocolException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}
		onFinishRequest(strResponse);
	}

	@UiThread
	void onFinishRequest(String strResponse) {
		resultText.setText(strResponse);
	}
	
	@UiThread
	void onCreateCheckinList(ArrayList<CheckinData> list) {
		adapter.addAll(list);
		adapter.notifyDataSetChanged();
	}

	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// // TODO 自動生成されたメソッド・スタブ
	//
	// btnRequest.setOnClickListener(new OnClickListener() {
	// @Background
	// @Override
	// public void onClick(View v) {
	// // TODO 自動生成されたメソッド・スタブ
	// HttpClient hClient = new DefaultHttpClient();
	// ExampleTokenStore ets = ExampleTokenStore.get();
	// String token = ets.getToken();
	// String strResponce = "unprocessed";
	// if (TextUtils.isEmpty(token)) {
	// strResponce = "トークンが存在しません。";
	// } else {
	// try {
	// HttpGet hGet = new HttpGet("https://api.foursquare.com/v2/user/self");
	// HttpResponse hResponse = hClient.execute(hGet);
	// HttpEntity hEntity = hResponse.getEntity();
	// strResponce = EntityUtils.toString(hEntity);
	// } catch (IOException e) {
	// strResponce = "例外が検出されました。";
	// }
	// }
	// resultText.setText(strResponce);
	// }
	// });
	// return inf;
	// }

}

