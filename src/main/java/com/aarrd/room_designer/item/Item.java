package com.aarrd.room_designer.item;

import com.aarrd.room_designer.favourite.Favourite;
import com.aarrd.room_designer.image.Image;
import com.aarrd.room_designer.item.category.Category;
import com.aarrd.room_designer.item.statistic.downlaod.ItemDownload;
import com.aarrd.room_designer.item.statistic.view.ItemView;
import com.aarrd.room_designer.item.tag.Tag;
import com.aarrd.room_designer.item.type.Type;
import com.aarrd.room_designer.item.variant.ItemVariant;
import com.aarrd.room_designer.model.Model;
import com.aarrd.room_designer.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private ItemVariant itemVariant;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @OneToMany(mappedBy = "item")
    private List<Image> images;

    @OneToMany(mappedBy = "item")
    private List<Tag> tags;

    @OneToMany(mappedBy = "item")
    private List<ItemDownload> itemDownloads;

    @OneToMany(mappedBy = "item")
    private List<ItemView> itemViews;

    @OneToMany(mappedBy = "item")
    private List<Favourite> favourites;

    public Item(){}

    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}