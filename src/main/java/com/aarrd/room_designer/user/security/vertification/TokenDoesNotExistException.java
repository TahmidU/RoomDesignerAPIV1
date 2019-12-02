package com.aarrd.room_designer.user.security.vertification;

public class TokenDoesNotExistException extends Throwable
{
    public TokenDoesNotExistException(String message) {
        super(message);
    }
}
