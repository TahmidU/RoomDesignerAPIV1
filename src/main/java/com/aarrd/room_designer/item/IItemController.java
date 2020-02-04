package com.aarrd.room_designer.item;

import com.sun.mail.iap.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

public interface IItemController
{
    HttpStatus addItem(@RequestBody Item item, Principal principal, @RequestParam String catName,
                       @RequestParam String typeName);
    ResponseEntity<?> fetchItem(@RequestParam Long itemId);
    ResponseEntity<?> fetchItemsByUserId(@RequestParam Long userId);
    ResponseEntity<?> fetchItemByCategory(@RequestParam String catName, @RequestParam Integer pageNum);
    HttpStatus removeItem(@RequestParam Long id);
    HttpStatus modifyItem(@RequestBody Item modItem);
    HttpStatus changeCategory(@RequestParam Long itemId, @RequestParam String name);
    HttpStatus changeType(@RequestParam Long itemId, @RequestParam String name);
    HttpStatus mergeVariants(@RequestParam List<Long> itemIds);
    HttpStatus separateVariants(@RequestParam List<Long> itemIds);
    ResponseEntity<Long> getVariantId(@RequestParam Long itemId);
    ResponseEntity<?> fetchItemVariant(@RequestParam Long itemId);
    ResponseEntity<?> fetchItems(@RequestParam Integer pageNum,@RequestParam String itemName,@RequestParam Integer catId,
                                 @RequestParam Integer typeId,@RequestParam Boolean hasModel);
}
