package com.aarrd.room_designer.favourite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/favourite")
public class FavouriteController implements IFavouriteController
{
    private final FavouriteService favouriteService;

    @Autowired
    public FavouriteController(FavouriteService favouriteService)
    {
        this.favouriteService = favouriteService;
    }

    @PostMapping(value = "/add")
    @Override
    public HttpStatus addFavourite(Principal principal, @RequestParam Long itemId)
    {
        favouriteService.addFavourite(principal, itemId);
        return HttpStatus.OK;
    }

    @DeleteMapping(value = "/remove")
    @Override
    public HttpStatus removeFavourite(Principal principal, @RequestParam Long itemId)
    {
        favouriteService.removeFavourite(principal, itemId);
        return HttpStatus.OK;
    }

    @GetMapping(value = "/fetch")
    @Override
    public ResponseEntity<List<Long>> fetchFavourited(Principal principal) {
        return new ResponseEntity<>(favouriteService.fetchFavourited(principal), HttpStatus.OK);
    }

    @GetMapping(value = "fetch-item")
    @Override
    public ResponseEntity<Boolean> fetchItemFavourited(Principal principal, @RequestParam Long itemId) {
        return new ResponseEntity<Boolean>(favouriteService.fetchItemFavourited(principal,itemId), HttpStatus.OK);
    }
}
