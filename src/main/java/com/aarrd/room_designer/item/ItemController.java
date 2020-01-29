package com.aarrd.room_designer.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public HttpStatus addItem(@RequestBody Item item, Principal principal, @RequestParam String catName,
                              @RequestParam String typeName)
    {
        itemService.addItem(item, principal, catName, typeName);
        return HttpStatus.OK;
    }

    @GetMapping(value = "/fetch-item")
    @Override
    public ResponseEntity<?> fetchItem(@RequestParam Long itemId)
    {
        return new ResponseEntity<>(itemService.fetchItem(itemId), HttpStatus.OK);
    }

    @GetMapping(value = "/fetch-items-userid")
    @Override
    public ResponseEntity<?> fetchItemsByUserId(@RequestParam Long userId)
    {
        return new ResponseEntity<>(itemService.fetchUserItems(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/fetch-by-cat")
    @Override
    public ResponseEntity<?> fetchItemByCategory(@RequestParam String catName, @RequestParam Integer pageNum)
    {
        return new ResponseEntity<>(itemService.fetchItemsByCat(catName, pageNum), HttpStatus.OK);
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

    @GetMapping(value = "/fetch-variants")
    @Override
    public ResponseEntity<?> fetchItemVariant(@RequestParam Long itemId)
    {
        return new ResponseEntity<>(itemService.fetchItemVariants(itemId), HttpStatus.OK);
    }

    @GetMapping(value = "/fetch-all")
    @Override
    public ResponseEntity<List<Object[]>> fetchItems(@RequestParam Integer pageNum)
    {
        return new ResponseEntity<>(itemService.fetchItems(pageNum), HttpStatus.OK);
    }
}
