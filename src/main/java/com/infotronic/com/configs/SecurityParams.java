package com.infotronic.com.configs;

public interface SecurityParams {
 static final String JWT_HEADER_NAME="Authorization";
 static final String JWT_SECRET="sous.218.bfa";
 static final long EXPIRATION=10*100*24*3600;
 static final String JWT_HEADER_PREFIX="Bearer ";
static final String LOGIN_URL = "/api/login";
}
