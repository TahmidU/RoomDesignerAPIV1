package com.aarrd.room_designer.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItemRepository extends JpaRepository<Item, Long>
{
    @Query("FROM Item WHERE variantId = ?1")
    List<Item> findByVariantId(Long variantId);

    @Query("FROM Item WHERE userId = ?1")
    List<Item> findByUserId(Long userId);
}
