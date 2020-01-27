package com.aarrd.room_designer.item;

import com.aarrd.room_designer.user.User;

import java.security.Principal;
import java.util.List;

public interface IItemService
{
    void addItem(Item item, Principal principal, String catName, String typeName);
    Item fetchItem(Long itemId);
    List<Item> fetchUserItems(Long userId);
    void removeItem(Long id);
    void modifyItem(Item modItem);
    void changeCategory(Long itemId, String name);
    void changeType(Long itemId, String name);
    void mergeVariants(List<Long> itemIds);
    void separateVariants(List<Long> itemIds);
    User getUser(Long itemId);
}
