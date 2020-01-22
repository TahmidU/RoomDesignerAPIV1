package com.aarrd.room_designer.favourite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping(value = "/favourite")
public class FavouriteController
{
    private final FavouriteService favouriteService;

    @Autowired
    public FavouriteController(FavouriteService favouriteService)
    {
        this.favouriteService = favouriteService;
    }

    @PostMapping(value = "/add")
    public HttpStatus addFavourite(Principal principal, @RequestParam Long itemId)
    {
        favouriteService.addFavourite(principal, itemId);
        return HttpStatus.OK;
    }

    @DeleteMapping(value = "/remove")
    public HttpStatus removeFavourite(Principal principal, @RequestParam Long itemId)
    {
        favouriteService.removeFavourite(principal, itemId);
        return HttpStatus.OK;
    }
}
