package com.aarrd.room_designer.item.statistic.view;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface IItemViewController
{
    HttpStatus incrementView(@RequestParam Long itemId);
    ResponseEntity<Integer> getViews(@RequestParam Long itemId);
}
