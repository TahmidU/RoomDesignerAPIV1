package com.aarrd.room_designer.item.statistic.download;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface IItemDownloadController
{
    ResponseEntity<Integer> getViews(@RequestParam Long itemId);
}
