package com.aarrd.room_designer.favourite;

import com.aarrd.room_designer.item.IItemRepository;
import com.aarrd.room_designer.item.Item;
import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

@Service
public class FavouriteService implements IFavouriteService
{
    private final IFavouriteRepository favouriteRepository;
    private final IItemRepository itemRepository;
    private final IUserRepository userRepository;

    @Autowired
    public FavouriteService(IFavouriteRepository favouriteRepository, IItemRepository itemRepository,
                            IUserRepository userRepository)
    {
        this.favouriteRepository = favouriteRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addFavourite(Principal principal, Long itemId)
    {
        User user = userRepository.findByEmail(principal.getName());
        Item item = itemRepository.getOne(itemId);

        if(user == null)
            throw new FavouriteException("User not found");
        if(item == null)
            throw new FavouriteException("Item not found");

        favouriteRepository.save(new Favourite(new Date(), user, item));
    }

    @Override
    public void removeFavourite(Principal principal, Long itemId)
    {
        User user = userRepository.findByEmail(principal.getName());

        if(user == null)
            throw new FavouriteException("User not found");

        favouriteRepository.delete(favouriteRepository.findByUserIdAndItemId(user.getUserId(), itemId));
    }
}
