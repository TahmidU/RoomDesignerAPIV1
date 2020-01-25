package com.aarrd.room_designer.favourite;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

public interface IFavouriteController
{
    HttpStatus addFavourite(Principal principal, @RequestParam Long itemId);
    HttpStatus removeFavourite(Principal principal, @RequestParam Long itemId);
}
