package com.xebia.innovationportal.models;

import com.xebia.innovationportal.entities.User;

public class ZohoUserData {

	private User userData;
	private boolean isDataPresent;
	
	public ZohoUserData() {
		super();
	}
	
	public ZohoUserData(User userData, boolean isDataPresent) {
		super();
		this.userData = userData;
		this.isDataPresent = isDataPresent;
	}
	
	public User getUserData() {
		return userData;
	}
	
	public void setUserData(User userData) {
		this.userData = userData;
	}
	
	public boolean isDataPresent() {
		return isDataPresent;
	}
	
	public void setDataPresent(boolean isDataPresent) {
		this.isDataPresent = isDataPresent;
	}
	
}
