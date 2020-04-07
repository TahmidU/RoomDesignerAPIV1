package com.aarrd.room_designer.item;

import org.springframework.data.domain.Page;

import java.util.List;

public interface IEnhancedItemRepository
{
    List<Item> findAllItems(Integer pageNum, String itemName, List<Integer> catId, List<Integer> typeId, Boolean hasModel);
    List<Item> findAllUserItems(Integer pageNum, String itemName, List<Integer> catId, List<Integer> typeId, Boolean hasModel,
                                Long userId);
}
