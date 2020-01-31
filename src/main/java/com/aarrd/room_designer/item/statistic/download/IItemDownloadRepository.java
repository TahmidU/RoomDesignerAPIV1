package com.aarrd.room_designer.item.statistic.download;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItemDownloadRepository extends JpaRepository<ItemDownload, Long>
{
    @Query("FROM ItemDownload i WHERE i.item.itemId = ?1")
    List<ItemDownload> findByItemId(Long itemId);
}
