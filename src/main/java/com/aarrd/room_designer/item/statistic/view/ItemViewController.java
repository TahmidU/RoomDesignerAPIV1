package com.aarrd.room_designer.item.statistic.view;

import com.aarrd.room_designer.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/item-views")
public class ItemViewController
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
    @PostMapping(value = "/increment")
    public HttpStatus incrementView(@RequestParam Long itemId) {
        Log.printMsg(this.getClass(), "Increment View count with itemId: " + itemId);
        itemViewService.incrementView(itemId);
        return HttpStatus.OK;
    }

    /**
     * Retrieve the number of views for an item.
     * @param itemId (request parameter) ID of the item.
     * @return ResponseEntity containing an integer.
     */
    @GetMapping(value = "/get-views")
    public ResponseEntity<Integer> getViews(@RequestParam Long itemId) {
        Log.printMsg(this.getClass(), "Fetch View counts with itemId: " + itemId);
        return new ResponseEntity<>(itemViewService.getViews(itemId),HttpStatus.OK);
    }
}
