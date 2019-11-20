package com.aarrd.room_designer.item.statistic.downlaod;

import com.aarrd.room_designer.item.Item;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "item_download")
@Data
public class ItemDownload
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "download_id")
    private long downloadId;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public ItemDownload(){}

    public ItemDownload(long downloadId, Date date, Item item) {
        this.downloadId = downloadId;
        this.date = date;
        this.item = item;
    }
}
