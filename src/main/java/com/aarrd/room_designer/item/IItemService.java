package com.aarrd.room_designer.item;

import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface IItemService
{
    ResponseEntity<?> addItem(Item item, Principal principal, String catName, String typeName);
    Item fetchItem(Long itemId);
    List<Item> fetchItems(Integer pageNum, String itemName, List<Integer> catIds, List<Integer> typeIds, Boolean hasModel);
    List<Item> fetchUserItems(Principal principal, Integer pageNum, String itemName, List<Integer> catIds,
                              List<Integer> typeIds, Boolean hasModel);
    void removeItem(Long itemId);
    void modifyItem(Item modItem, String catName, String typeName);
    void changeCategory(Long itemId, String name);
    void changeType(Long itemId, String name);
}
