package model;

public class CheckinData {
	String venueName;
	String checkinId;
	String venueId;
	long lng;
	long lat;
	String photoUrl;
	String category;
	String friendName;
	final String PHOTO_SIZE = "300x300";
	
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
	public long getLng() {
		return lng;
	}
	public void setLng(long lng) {
		this.lng = lng;
	}
	public long getLat() {
		return lat;
	}
	public void setLat(long lat) {
		this.lat = lat;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String prefix, String suffix) {
		prefix = prefix.replace("\\", "");
		suffix = suffix.replace("\\", "");
		photoUrl = prefix + PHOTO_SIZE + suffix;
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
