package com.aarrd.room_designer.image;

import com.aarrd.room_designer.item.Item;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "image")
@Data
public class Image
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long imageId;

    @Temporal(TemporalType.DATE)
    @Column(name = "upload_date", nullable = false)
    private Date uploadDate;

    @Column(name = "image_directory", unique = true, nullable = false)
    private String imageDirectory;

    @Column(name = "is_thumbnail", nullable = false, columnDefinition = "BIT", length = 1)
    private Boolean isThumbnail;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Image(){}

    public Image(Date uploadDate, String imageDirectory, Boolean isThumbnail) {
        this.uploadDate = uploadDate;
        this.imageDirectory = imageDirectory;
        this.isThumbnail = isThumbnail;
    }

    public Image(Date uploadDate, String imageDirectory, Boolean isThumbnail, Item item) {
        this.uploadDate = uploadDate;
        this.imageDirectory = imageDirectory;
        this.isThumbnail = isThumbnail;
        this.item = item;
    }
}
