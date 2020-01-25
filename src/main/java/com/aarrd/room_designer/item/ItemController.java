package com.aarrd.room_designer.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/item")
public class ItemController implements IItemController
{
    private final IItemService itemService;

    @Autowired
    public ItemController(IItemService itemService)
    {
        this.itemService = itemService;
    }

    @PostMapping(value = "/add")
    @Override
    public HttpStatus addItem(@RequestBody Item item)
    {
        itemService.addItem(item);
        return HttpStatus.OK;
    }

    @Override
    public ResponseEntity<Item> fetchItem(Long itemId)
    {
        return new ResponseEntity<Item>(itemService.fetchItem(itemId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Item>> fetchItemByUserId(Long userId)
    {
        return new ResponseEntity<List<Item>>(itemService.fetchUserItems(userId), HttpStatus.OK);
    }

    @PostMapping(value = "/remove")
    @Override
    public HttpStatus removeItem(@RequestParam Long id)
    {
        itemService.removeItem(id);
        return HttpStatus.OK;
    }

    @PutMapping(value = "/update")
    @Override
    public HttpStatus modifyItem(@RequestBody Item modItem)
    {
        itemService.modifyItem(modItem);
        return HttpStatus.OK;
    }

    @PutMapping(value = "/change-cat")
    @Override
    public HttpStatus changeCategory(@RequestParam Long itemId, @RequestParam String name)
    {
        itemService.changeCategory(itemId, name);
        return HttpStatus.OK;
    }

    @PutMapping(value = "/change-type")
    @Override
    public HttpStatus changeType(@RequestParam Long itemId, @RequestParam String name)
    {
        itemService.changeType(itemId, name);
        return HttpStatus.OK;
    }

    @PutMapping(value = "/merge-var")
    @Override
    public HttpStatus mergeVariants(@RequestParam List<Long> itemIds)
    {
        itemService.mergeVariants(itemIds);
        return HttpStatus.OK;
    }

    @PutMapping(value = "/separate-var")
    @Override
    public HttpStatus separateVariants(@RequestParam List<Long> itemIds)
    {
        itemService.separateVariants(itemIds);
        return HttpStatus.OK;
    }
}
