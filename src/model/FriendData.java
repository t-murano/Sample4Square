package model;

public class FriendData {
	long id;
	String firstName;
	String lastName;
	long facebook;
	String photoUrl;


	public void setId(long id) {
		this.id = id;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setFacebook(long facebook) {
		this.facebook = facebook;
	}
	
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl.replace("\\", "");
	}
	
	public long getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public long getFacebook() {
		return facebook;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
}
