package model;

public class CheckinData {
	String venueName;
	String checkinId;
	String venueId;
	String message;
	double lng;
	double lat;
	String venueAddress;
	public String getVenueAddress() {
		return venueAddress;
	}
	public void setVenueAddress(String state, String city, String address) {
		this.venueAddress = state + city + address;
	}
	int checkinCount;
	String photoURL;
	String category;
	String friendName;
	String friendId;
	String userPhotoURL;
	final String PHOTO_SIZE = "300x300";
	
	public int getCheckinCount() {
		return checkinCount;
	}
	public void setCheckinCount(int checkinCount) {
		this.checkinCount = checkinCount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUserPhotoURL() {
		return userPhotoURL;
	}
	public void setUserPhotoURL(String userPhotoURL) {
		this.userPhotoURL = userPhotoURL;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getCheckinId() {
		return checkinId;
	}
	public void setCheckinId(String checkinId) {
		this.checkinId = checkinId;
	}
	public String getVenueId() {
		return venueId;
	}
	public void setVenueId(String venueId) {
		this.venueId = venueId;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public String getPhotoUrl() {
		return photoURL;
	}
	public void setPhotoURL(String prefix, String suffix) {
		prefix = prefix.replace("\\", "");
		suffix = suffix.replace("\\", "");
		photoURL = prefix + PHOTO_SIZE + suffix;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String lastName, String firstName) {
		friendName = lastName + " " + firstName;
	}
}
