package com.aarrd.room_designer.user.security.vertification;

public class EmailExistException extends Throwable
{
    public EmailExistException(String message) {
        super(message);
    }
}
