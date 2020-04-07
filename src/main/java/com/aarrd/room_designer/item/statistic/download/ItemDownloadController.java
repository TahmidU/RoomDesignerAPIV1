package com.aarrd.room_designer.item.statistic.download;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/item-download")
public class ItemDownloadController
{
    private final IItemDownloadService itemDownloadService;

    @Autowired
    public ItemDownloadController(IItemDownloadService itemDownloadService)
    {
        this.itemDownloadService = itemDownloadService;
    }

    /**
     * Get the total number of download of the item.
     * @param itemId (request parameter) ID of the item.
     * @return ResponseEntity containing an integer.
     */
    @GetMapping(value = "/aggregate")
    public ResponseEntity<Integer> getDownloads(@RequestParam Long itemId)
    {
        return new ResponseEntity<>(itemDownloadService.getDownloadsAggregate(itemId), HttpStatus.OK);
    }
}
