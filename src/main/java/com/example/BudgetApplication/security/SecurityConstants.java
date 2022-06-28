package com.example.BudgetApplication.security;

/**
 * This class contains constants used when generating tokens.
 * 
 * @author Benjamin Chang
 *
 */
public class SecurityConstants {
	public static final long EXPIRATION_TIME = 864000000;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	public static String getTokenSecret() {
		return "renjigrebjirebgeji";
	}

}