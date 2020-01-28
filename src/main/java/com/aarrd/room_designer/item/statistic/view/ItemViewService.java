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

    @Override
    public void incrementView(Long itemId) {
        itemViewRepository.save(new ItemView(new Date(), itemRepository.getOne(itemId)));
    }

    @Override
    public Integer getViews(Long itemId)
    {
        return itemViewRepository.findByItemId(itemId).size();
    }
}
