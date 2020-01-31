package com.aarrd.room_designer.item.statistic.download;

import com.aarrd.room_designer.item.IItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ItemDownloadService implements IItemDownloadService
{
    private final IItemDownloadRepository itemDownloadRepository;

    @Autowired
    public ItemDownloadService(IItemDownloadRepository itemDownloadRepository)
    {
        this.itemDownloadRepository = itemDownloadRepository;
    }

    /**
     * Return the number of entries from the database for a specific item.
     * @param itemId ID of the item.
     * @return Integer.
     */
    @Override
    public Integer getDownloadsAggregate(Long itemId)
    {
        System.out.println("ItemDownloadService :: Returning number of download for " + itemId);
        return (itemDownloadRepository.findByItemId(itemId)).size();
    }
}
