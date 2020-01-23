package com.aarrd.room_designer.favourite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFavouriteRepository extends JpaRepository<Favourite, Long>
{
    @Query("SELECT 1 FROM Favourite WHERE userId = ?1 AND itemId = ?2")
    Favourite findByUserIdAndItemId(Long userId, Long itemId);
}
