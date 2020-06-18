package com.xebia.innovationportal.constants;

public interface SecurityConstant {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; //10 Days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTH = "Authorization";
}
