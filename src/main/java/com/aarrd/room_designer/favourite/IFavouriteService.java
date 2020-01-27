package com.aarrd.room_designer.favourite;

import com.aarrd.room_designer.item.Item;
import com.aarrd.room_designer.user.User;

import java.security.Principal;
import java.util.Date;
import java.util.List;

public interface IFavouriteService
{
    void addFavourite(Principal principal, Long itemId);
    void removeFavourite(Principal principal, Long itemId);
    List<Long> fetchFavourited(Principal principal);
    Boolean fetchItemFavourited(Principal principal, Long itemId);
}
