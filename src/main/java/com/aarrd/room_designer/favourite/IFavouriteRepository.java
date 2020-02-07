package com.aarrd.room_designer.favourite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFavouriteRepository extends JpaRepository<Favourite, Long>
{
    @Query("FROM Favourite f WHERE f.user.userId = ?1 AND f.item.itemId = ?2")
    Favourite findByUserIdAndItemId(Long userId, Long itemId);

    @Query("SELECT f.item.itemId FROM Favourite f WHERE f.user.userId = ?1")
    List<Long> findByUserId(Long userId);
}
