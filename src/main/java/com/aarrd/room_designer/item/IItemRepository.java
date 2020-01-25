package com.aarrd.room_designer.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItemRepository extends JpaRepository<Item, Long>
{
    @Query("SELECT * FROM Item WHERE itemVariant = ?1")
    List<Item> findByVariantId(Long variantId);
}
