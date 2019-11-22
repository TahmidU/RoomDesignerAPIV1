package com.aarrd.room_designer.security;

class SecurityConstants
{
    static final String SECRET = "sS1eT2curF3eF4t!";
    static final long EXPIRATION_TIME = 60*60*5*1000;
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/user/sign-up";
}
