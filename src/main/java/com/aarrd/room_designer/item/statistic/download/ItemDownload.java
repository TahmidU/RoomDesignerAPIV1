package com.aarrd.room_designer.item.statistic.download;

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
    @Column(name = "download_id", nullable = false)
    private Long downloadId;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public ItemDownload(){}

    public ItemDownload(Date date, Item item) {
        this.date = date;
        this.item = item;
    }
}
