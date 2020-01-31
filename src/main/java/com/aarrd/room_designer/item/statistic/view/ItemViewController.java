package com.aarrd.room_designer.item.statistic.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/item-view")
public class ItemViewController implements IItemViewController
{
    private final IItemViewService itemViewService;

    @Autowired
    public ItemViewController(IItemViewService itemViewService)
    {
        this.itemViewService = itemViewService;
    }

    /**
     * Increment the number of views for an item.
     * @param itemId (request parameter) ID of the item.
     * @return HttpStatus.
     */
    @Override
    @PostMapping(value = "/increment")
    public HttpStatus incrementView(@RequestParam Long itemId) {
        itemViewService.incrementView(itemId);
        return HttpStatus.OK;
    }

    /**
     * Retrieve the number of views for an item.
     * @param itemId (request parameter) ID of the item.
     * @return ResponseEntity containing an integer.
     */
    @Override
    @GetMapping(value = "/get-views")
    public ResponseEntity<Integer> getViews(@RequestParam Long itemId) {
        return new ResponseEntity<>(itemViewService.getViews(itemId),HttpStatus.OK);
    }
}
