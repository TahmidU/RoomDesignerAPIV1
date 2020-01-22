package com.aarrd.room_designer.favourite;

public class FavouriteException extends RuntimeException
{
    public FavouriteException(String message)
    {
        super(message);
    }

    public FavouriteException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
