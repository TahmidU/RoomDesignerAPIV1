package com.aarrd.room_designer.item;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IItemController
{
    HttpStatus addItem(@RequestBody Item item);
    ResponseEntity<Item> fetchItem(@RequestParam Long itemId);
    ResponseEntity<List<Item>> fetchItemByUserId(@RequestParam Long userId);
    HttpStatus removeItem(@RequestParam Long id);
    HttpStatus modifyItem(@RequestBody Item modItem);
    HttpStatus changeCategory(@RequestParam Long itemId, @RequestParam String name);
    HttpStatus changeType(@RequestParam Long itemId, @RequestParam String name);
    HttpStatus mergeVariants(@RequestParam List<Long> itemIds);
    HttpStatus separateVariants(@RequestParam List<Long> itemIds);
}
