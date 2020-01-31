package com.aarrd.room_designer.item.statistic.view;

import com.aarrd.room_designer.item.IItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class ItemViewService implements IItemViewService
{
    private final IItemViewRepository itemViewRepository;
    private final IItemRepository itemRepository;

    @Autowired
    public ItemViewService(IItemViewRepository itemViewRepository, IItemRepository itemRepository)
    {
        this.itemViewRepository = itemViewRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Increment the number of view for an item.
     * @param itemId ID of the item.
     */
    @Override
    public void incrementView(Long itemId)
    {
        System.out.println("ItemViewService :: Incrementing view for " + itemId);
        itemViewRepository.save(new ItemView(new Date(), itemRepository.getOne(itemId)));
    }

    /**
     * Return the number of view for an item.
     * @param itemId ID of the item.
     * @return Integer.
     */
    @Override
    public Integer getViews(Long itemId)
    {
        System.out.println("ItemViewService :: Returning number of views for " + itemId);
        return itemViewRepository.findByItemId(itemId).size();
    }
}
