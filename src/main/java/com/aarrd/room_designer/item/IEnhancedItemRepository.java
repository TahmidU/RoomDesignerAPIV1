package com.aarrd.room_designer.item;

import org.springframework.data.domain.Page;

import java.util.List;

public interface IEnhancedItemRepository
{
    List<Item> findAllItems(Integer pageNum, String itemName, Integer catId, Integer typeId, Boolean hasModel);
    List<Item> findAllUserItems(Integer pageNum, String itemName, Integer catId, Integer typeId, Boolean hasModel,
                                Long userId);
    List<Item> findAllUserFavourites(Integer pageNum, String itemName, Integer catId, Integer typeId, Boolean hasModel,
                                     Long userId);
}
