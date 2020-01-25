package com.aarrd.room_designer.item.statistic.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItemViewRepository extends JpaRepository<ItemView, Long>
{
    @Query("FROM ItemView WHERE itemId = ?1")
    List<ItemView> findByItemId(Long itemId);
}
