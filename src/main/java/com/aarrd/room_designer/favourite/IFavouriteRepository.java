package com.aarrd.room_designer.favourite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFavouriteRepository extends JpaRepository<Favourite, Long>
{
    @Query("FROM Favourite WHERE user_id = ?1 AND item_id = ?2")
    Favourite findByUserIdAndItemId(Long userId, Long itemId);

    @Query("FROM Favourite WHERE user_id = ?1")
    List<Favourite> findByUserId(Long userId);
}
