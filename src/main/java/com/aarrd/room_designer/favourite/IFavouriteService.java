package com.aarrd.room_designer.favourite;

import org.springframework.data.domain.Page;
import java.security.Principal;

public interface IFavouriteService
{
    void addFavourite(Principal principal, Long itemId);
    void removeFavourite(Principal principal, Long itemId);
    Boolean fetchItemFavourited(Principal principal, Long itemId);
}
