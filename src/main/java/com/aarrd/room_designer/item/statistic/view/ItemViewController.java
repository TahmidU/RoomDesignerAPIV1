package com.aarrd.room_designer.item.statistic.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Override
    @PostMapping(value = "/increment")
    public HttpStatus incrementView(Long itemId) {
        itemViewService.incrementView(itemId);
        return HttpStatus.OK;
    }


    @Override
    @GetMapping(value = "/get-views")
    public ResponseEntity<Integer> getViews(Long itemId) {
        return new ResponseEntity<>(itemViewService.getViews(itemId),HttpStatus.OK);
    }
}
