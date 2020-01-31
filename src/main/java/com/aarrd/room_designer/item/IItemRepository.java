package com.aarrd.room_designer.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItemRepository extends JpaRepository<Item, Long>
{
    @Query("SELECT i.itemId, i.name, i.description, i.date FROM Item i WHERE i.itemId = ?1")
    Object findByItemId(Long itemId);

    @Query("SELECT i.itemId, i.name, i.description, i.date FROM Item i WHERE i.itemVariant.variantId = ?1")
    List<Object[]> findByVariantId(Long variantId);

    @Query("SELECT i.itemId, i.name, i.description, i.date FROM Item i WHERE i.user.userId = ?1")
    List<Object[]> findByUserId(Long userId);

    @Query("SELECT i.itemId, i.name, i.description, i.date FROM Item i")
    List<Object[]> findAllItems(Pageable pageable);

    @Query("SELECT i.itemId, i.name, i.description, i.date FROM Item i WHERE i.category.name = ?1")
    List<Object[]> findByCategory(String catName, Pageable pageable);

    @Query("SELECT i.itemVariant.variantId FROM Item i WHERE i.itemId = ?1")
    Long findVariantIdByItemId(Long itemId);
}
