package com.aarrd.room_designer.user.security.vertification;

public class TokenExpiredException extends Throwable
{
    public TokenExpiredException(String message) {
        super(message);
    }
}
