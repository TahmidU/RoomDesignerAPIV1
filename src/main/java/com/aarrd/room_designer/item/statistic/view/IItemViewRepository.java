package com.aarrd.room_designer.item.statistic.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IItemViewRepository extends JpaRepository<ItemView, Long>
{
    @Query("SELECT * FROM ItemView WHERE itemId = ?1")
    List<ItemView> findByItemId(Long itemId);
}
