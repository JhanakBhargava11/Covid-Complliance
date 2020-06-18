package com.xebia.innovationportal.utils;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public class HeaderUtil {

	private static final String PREFIX = "X-WebLink";

	private static final String SUCCESS = PREFIX + "-success";

	private static final String INFO = PREFIX + "-info";

	private static final String WARNING = PREFIX + "-warning";

	private static final String ERROR = PREFIX + "-error";

	public static HttpHeaders addInfo(String message) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(INFO, message);
		return headers;
	}

	public static HttpHeaders addSuccess(String message) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(SUCCESS, message);
		return headers;
	}

	public static HttpHeaders addWarning(String message) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(WARNING, message);
		return headers;
	}

	public static HttpHeaders addError(String message) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(ERROR, message);
		return headers;
	}

}
