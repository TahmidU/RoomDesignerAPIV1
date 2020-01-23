package com.aarrd.room_designer.item.statistic.download;

public interface IItemDownloadService
{
    void incrementDownload(Long itemId);
    int getDownloadsAggregate(Long itemId);
}
