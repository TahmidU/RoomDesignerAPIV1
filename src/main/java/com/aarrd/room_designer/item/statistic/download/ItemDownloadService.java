package com.aarrd.room_designer.item.statistic.download;

import com.aarrd.room_designer.item.IItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ItemDownloadService implements IItemDownloadService
{
    private final IItemDownloadRepository itemDownloadRepository;
    private final IItemRepository itemRepository;

    @Autowired
    public ItemDownloadService(IItemDownloadRepository itemDownloadRepository, IItemRepository itemRepository)
    {
        this.itemDownloadRepository = itemDownloadRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public int getDownloadsAggregate(Long itemId)
    {
        return (itemDownloadRepository.findByItemId(itemId)).size();
    }
}
