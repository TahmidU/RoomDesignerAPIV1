package com.aarrd.room_designer.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long>
{
    @Query("FROM Image WHERE itemId = ?1")
    List<Image> findByItemId(Long itemId);

    @Query("SELECT imageDirectory FROM Image WHERE itemId = ?1 AND isThumbnail = 1")
    String findDirByItemId(Long itemId);
}
