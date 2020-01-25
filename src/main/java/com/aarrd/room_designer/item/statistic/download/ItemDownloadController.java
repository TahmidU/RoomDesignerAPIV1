package com.aarrd.room_designer.item.statistic.download;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/item-download")
public class ItemDownloadController implements IItemDownloadController
{
    private final IItemDownloadService itemDownloadService;

    @Autowired
    public ItemDownloadController(IItemDownloadService itemDownloadService)
    {
        this.itemDownloadService = itemDownloadService;
    }

    @GetMapping(value = "/aggregate")
    @Override
    public ResponseEntity<Integer> getViews(Long itemId)
    {
        return new ResponseEntity<Integer>(itemDownloadService.getDownloadsAggregate(itemId), HttpStatus.OK);
    }
}
