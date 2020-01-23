package com.aarrd.room_designer.item.statistic.view;

public interface IItemViewService
{
    void incrementView(Long itemId);
    int getViews(Long itemId);
}
