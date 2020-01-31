package com.aarrd.room_designer.favourite;

import com.aarrd.room_designer.item.IItemRepository;
import com.aarrd.room_designer.item.Item;
import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     * Users favourited item saved into the database.
     * @param principal Currently logged in user.
     * @param itemId Item that was favourited by the user.
     */
    @Override
    public void addFavourite(Principal principal, Long itemId)
    {
        User user = userRepository.findByEmail(principal.getName());
        Item item = itemRepository.getOne(itemId);

        if(user == null)
            throw new FavouriteException("User not found");

        System.out.println("FavouriteService :: " + principal.getName() + " favourited " + itemId);
        favouriteRepository.save(new Favourite(new Date(), user, item));
    }

    /**
     * Item unfavourited by the user.
     * @param principal Currently logged in user.
     * @param itemId Item that was unfavourited by the user.
     */
    @Override
    public void removeFavourite(Principal principal, Long itemId)
    {
        User user = userRepository.findByEmail(principal.getName());

        if(user == null)
            throw new FavouriteException("User not found");

        System.out.println("FavouriteService :: " + principal.getName() + " unfavourited " + itemId);
        favouriteRepository.delete(favouriteRepository.findByUserIdAndItemId(user.getUserId(), itemId));
    }

    /**
     * All items that were favourited by the user.
     * @param principal Currently logged in user.
     * @return
     */
    @Override
    public List<Long> fetchFavourited(Principal principal)
    {
        System.out.println("FavouriteService :: Fetching favourites for " + principal.getName());

        List<Favourite> favourites =
                favouriteRepository.findByUserId(userRepository.findByEmail(principal.getName()).getUserId());

        List<Long> favouriteIds = new ArrayList<>();
        for(Favourite f : favourites)
            favouriteIds.add(f.getFavouriteId());

        return favouriteIds;
    }

    /**
     * Is the item favourited?
     * @param principal Currently logged in user.
     * @param itemId Item that is checked.
     * @return
     */
    @Override
    public Boolean fetchItemFavourited(Principal principal, Long itemId)
    {
        System.out.println("FavouriteService :: Checking favourited between: " + principal.getName() + " and " + itemId);
        Long userId = userRepository.findByEmail(principal.getName()).getUserId();
        if(favouriteRepository.findByUserIdAndItemId(userId,itemId) != null)
            return true;
        return false;
    }
}
