package com.xebia.innovationportal.constants;

import java.util.ArrayList;
import java.util.List;

public interface CommonConstants {

	String OTP_INVALID = "Invalid OTP.";

	String OTP_ALREADY_USED = "This OTP is already used. Please use new OTP.";

	String OTP_EXPIRED = "Your OTP has expired. Please register again.";

	String OTP_SUCCESS_MESSAGE = "You have logged in successfully. Your One Time Password is : ";

	String LOGIN_CONFIRMATION = "Login Confirmation.";

	public String DIRECTORY_LOCATION = "";

	public static final String STATUS_DRAFT = "Draft";

	public static final String STATUS_SUBMITTED = "Submitted";

	public static final String STATUS_REVIEWED = "Reviewed";

	public static final String STATUS_APPROVED = "Approved";

	public static final String STATUS_REJECTED = "Rejected";

	public static final String STATUS_CLOSED = "Closed";

	public static final String STATUS_COMPLETED = "Compleleted";

	public static final String STATUS_DEVELOPMENT = "Development";

	public static final Integer ROLE_ADMIN_ID = 1;

	public static final Integer ROLE_MANAGER_ID = 2;

	public static final Integer ROLE_USER_ID = 3;

	static List<String> getNextStatusList(String status) {
		List<String> statusList = new ArrayList<String>();
		switch (status) {
		case STATUS_SUBMITTED: {
			statusList.add(STATUS_APPROVED);
			statusList.add(STATUS_REJECTED);
			statusList.add(STATUS_REVIEWED);
			statusList.add(STATUS_CLOSED);
			break;
		}
		case STATUS_REVIEWED: {
			statusList.add(STATUS_SUBMITTED);
			break;
		}
		case STATUS_APPROVED: {
			statusList.add(STATUS_DEVELOPMENT);
			statusList.add(STATUS_CLOSED);
			break;
		}
		case STATUS_DEVELOPMENT: {
			statusList.add(STATUS_COMPLETED);
			break;
		}
		default: {
			statusList.add(STATUS_SUBMITTED);
		}
		}
		return statusList;
	}
}
