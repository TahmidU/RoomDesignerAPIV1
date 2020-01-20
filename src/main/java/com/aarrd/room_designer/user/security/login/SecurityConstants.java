package com.aarrd.room_designer.user.security.login;

class SecurityConstants
{
    static final String SECRET = "sS1eT2curF3eF4t!";
    static final long EXPIRATION_TIME = 60*60*25*1000; //25 hours from milliseconds
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
}
