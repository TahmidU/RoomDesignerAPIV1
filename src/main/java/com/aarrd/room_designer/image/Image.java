package com.aarrd.room_designer.image;

import com.aarrd.room_designer.item.Item;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "image")
@Data
public class Image
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private long imageId;

    @Temporal(TemporalType.DATE)
    @Column(name = "upload_date")
    private Date uploadDate;

    @Column(name = "image_directory")
    private String imageDirectory;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public Image(){}

    public Image(long imageId, Date uploadDate, String imageDirectory, Item item) {
        this.imageId = imageId;
        this.uploadDate = uploadDate;
        this.imageDirectory = imageDirectory;
        this.item = item;
    }
}
