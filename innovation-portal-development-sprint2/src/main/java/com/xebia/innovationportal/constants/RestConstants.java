package com.xebia.innovationportal.constants;

public interface RestConstants {

	String SLASH = "/";

	String API = SLASH + "api";

	String VERSION_v1 = "v1";

	String ID = "{id}";

	String CURRENT_VERSION = VERSION_v1;

	String INNOVATION_PORTAL_API = API + SLASH + CURRENT_VERSION;

	interface UserDetails {

		String USER_OTP = SLASH + "otp";

		String USER_OTP_VERIFY = USER_OTP + SLASH + "verify";

		String USER_AUTH_TOKEN_VERIFY = SLASH + "token" + SLASH + "verify";

		String USER_LOGOUT = SLASH + "logout";

	}

	interface ideaDetails {
		String IDEAS = SLASH + "ideas";

		String USERS = SLASH + "users";

		String ALL_IDEAS = IDEAS + SLASH + "all";

		String IDEAS_BY_ID = IDEAS + "/{id}";

		String USER_BY_ID_IDEAS = USERS + "/{id}" + IDEAS;

		String IDEAS_LIKE_BY_ID = IDEAS_BY_ID + SLASH + "like";

		String RECENT_SUBMITTED_IDEAS = IDEAS + SLASH + "recent";

		String RECENT_IDEA_REQUEST = IDEAS + SLASH + "request";

		String IDEA_DISLIKE_BY_ID = IDEAS_BY_ID + SLASH + "dislike";

		String ALL_IDEAS_REQUEST = RECENT_IDEA_REQUEST + SLASH + "{status}";

		String IDEAS_UPLOAD = IDEAS + SLASH + "upload";

		String IDEAS_DOWNLOAD = IDEAS + SLASH + "download";

		String IDEAS_UPDATE_STATUS = IDEAS + SLASH + "updatestatus";
	}

	interface dashboardDetails {
		String DASHBOARD = SLASH + "dashboard";

		String TOP_CONTRIBUTOR = DASHBOARD + SLASH + "topcontributor";

		String TRENDING = DASHBOARD + SLASH + "toptrending";

		String IDEA_STATS = DASHBOARD + SLASH + "ideastats";

	}

	interface Management {

		String USER_URI = SLASH + "users";

		String USER_UPDATE_STATUS_URI = USER_URI + SLASH + ID + SLASH + "status";

		String SUB_CATEGORIES_URI = SLASH + "categories";

		String SUB_CATEGORIES_BY_ID = SUB_CATEGORIES_URI + SLASH + "/{id}";
		String ACTIVE_SUB_CATEGORIES_URI = SLASH + "active" + SUB_CATEGORIES_URI;

	}

}
