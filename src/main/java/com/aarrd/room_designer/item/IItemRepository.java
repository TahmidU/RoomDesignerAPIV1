package com.aarrd.room_designer.item;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Predicate;

@Repository
public interface IItemRepository extends JpaRepository<Item, Long>
{
    @Query("FROM Item i WHERE i.itemId = ?1")
    Item findByItemId(Long itemId);
}
