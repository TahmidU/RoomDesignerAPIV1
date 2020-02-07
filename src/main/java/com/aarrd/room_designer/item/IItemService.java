package com.aarrd.room_designer.item;

import com.aarrd.room_designer.favourite.Favourite;
import com.aarrd.room_designer.item.variant.ItemVariant;
import com.aarrd.room_designer.user.User;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface IItemService
{
    void addItem(Item item, Principal principal, String catName, String typeName);
    Item fetchItem(Long itemId);
    Page<Item> fetchUserItems(Integer pageNum, String itemName, Integer catId, Integer typeId, Boolean hasModel, Long userId);
    Page<Item> fetchItems(Integer pageNum, String itemName, Integer catId, Integer typeId, Boolean hasModel);
    List<Item> fetchItemVariants(Long itemId);
    Page<Item> fetchUserItems(Principal principal, Integer pageNum, String itemName, Integer catId,
                                    Integer typeId, Boolean hasModel);
    Page<Item> fetchFavourites(Principal principal, Integer pageNum, String itemName, Integer catId,
                              Integer typeId, Boolean hasModel);
    void removeItem(Long itemId);
    void modifyItem(Item modItem);
    void changeCategory(Long itemId, String name);
    void changeType(Long itemId, String name);
    void mergeVariants(List<Long> itemIds);
    void separateVariants(List<Long> itemIds);
    Long getVariantId(Long itemId);
}
