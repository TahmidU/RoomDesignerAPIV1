package com.aarrd.room_designer.item;

import com.aarrd.room_designer.image.Image;
import com.aarrd.room_designer.item.category.Category;
import com.aarrd.room_designer.item.statistic.downlaod.ItemDownload;
import com.aarrd.room_designer.item.statistic.view.ItemView;
import com.aarrd.room_designer.item.tag.Tag;
import com.aarrd.room_designer.item.variant.ItemVariant;
import com.aarrd.room_designer.user.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Data
public class Item
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long itemId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "desc", length = 1000)
    private String desc;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cat_id", referencedColumnName = "cat_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "variant_id", referencedColumnName = "variant_id")
    private ItemVariant itemVariant;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private long type_id;

    @OneToMany(mappedBy = "item")
    @JoinColumn(name = "image_id", referencedColumnName = "image_id")
    private Image image;

    @OneToMany(mappedBy = "item")
    @JoinColumn(name = "tag_id", referencedColumnName = "tag_id")
    private Tag tag;

    @OneToMany(mappedBy = "item")
    @JoinColumn(name = "item_id")
    private ItemDownload itemDownload;

    @OneToMany(mappedBy = "item")
    @JoinColumn(name = "item_id")
    private ItemView itemView;

    public Item(){}

    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
