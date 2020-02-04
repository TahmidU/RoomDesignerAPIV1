package com.aarrd.room_designer.item;

import org.springframework.data.domain.Page;

import java.util.List;

public interface IEnhancedItemRepository
{
    Page<Item> findAllItems(Integer pageNum, String itemName, Integer catId, Integer typeId, Boolean hasModel);
}
