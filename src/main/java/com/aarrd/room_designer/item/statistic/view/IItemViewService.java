package com.aarrd.room_designer.item.statistic.view;

public interface IItemViewService
{
    void incrementView(Long itemId);
    Integer getViews(Long itemId);
}
