package com.aarrd.room_designer.item;

import java.util.List;

public interface IItemService
{
    void addItem(Item item);
    void removeItem(Long id);
    void modifyItem(Item modItem);
    void changeCategory(Long itemId, String name);
    void changeType(Long itemId, String name);
    void mergeVariants(List<Long> itemIds);
    void separateVariants(List<Long> itemIds);
}
