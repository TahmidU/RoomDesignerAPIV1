package com.aarrd.room_designer.user.security.login;

public class UserNotActiveException extends Throwable
{
    public UserNotActiveException(String message) {
        super(message);
    }
}
