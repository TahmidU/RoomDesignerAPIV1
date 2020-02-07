package com.aarrd.room_designer.item.statistic.download;

import com.aarrd.room_designer.item.Item;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.Cascade;

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

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "itemId")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public ItemDownload(){}

    public ItemDownload(Date date) {
        this.date = date;
    }

    public ItemDownload(Date date, Item item) {
        this.date = date;
        this.item = item;
    }
}
