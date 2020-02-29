package com.aarrd.room_designer.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long>
{
    @Query("SELECT i.imageId FROM Image i WHERE i.item.itemId = ?1")
    List<Long> findImageIdByItemId(Long itemId);

    @Query("SELECT i.imageDirectory FROM Image i WHERE i.imageId = ?1")
    String findDirByImageId(Long imageId);

    @Query("FROM Image i WHERE i.item.itemId = ?1 AND i.isThumbnail = 1")
    Image findThumbnailImage(Long itemId);

    @Query("SELECT i.imageDirectory FROM Image i WHERE i.item.itemId = ?1 AND i.isThumbnail = 1")
    String findDirByItemIdAndThumbnail(Long itemId);
}
