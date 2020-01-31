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

    /**
     * Users favourited item saved into the database.
     * @param principal currently logged in user.
     * @param itemId (request parameter) item that was favourited by the user.
     * @return HttpStatus.
     */
    @PostMapping(value = "/add")
    @Override
    public HttpStatus addFavourite(Principal principal, @RequestParam Long itemId)
    {
        favouriteService.addFavourite(principal, itemId);
        return HttpStatus.OK;
    }

    /**
     * Item unfavourited by the user.
     * @param principal currently logged in user.
     * @param itemId (request parameter) item that was unfavourited by the user.
     * @return HttpStatus.
     */
    @DeleteMapping(value = "/remove")
    @Override
    public HttpStatus removeFavourite(Principal principal, @RequestParam Long itemId)
    {
        favouriteService.removeFavourite(principal, itemId);
        return HttpStatus.OK;
    }

    /**
     * All items that were favourited by the user.
     * @param principal currently logged in user.
     * @return ResponseEntity containing a list of favouritedIds.
     */
    @GetMapping(value = "/fetch")
    @Override
    public ResponseEntity<List<Long>> fetchFavourited(Principal principal) {
        return new ResponseEntity<>(favouriteService.fetchFavourited(principal), HttpStatus.OK);
    }

    /**
     * Is the item favourited?
     * @param principal currently logged in user.
     * @param itemId (request param) item that is checked.
     * @return ResponseEntity containing a boolean.
     */
    @GetMapping(value = "fetch-item")
    @Override
    public ResponseEntity<Boolean> fetchItemFavourited(Principal principal, @RequestParam Long itemId) {
        return new ResponseEntity<Boolean>(favouriteService.fetchItemFavourited(principal,itemId), HttpStatus.OK);
    }
}
