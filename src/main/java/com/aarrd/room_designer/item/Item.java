package com.aarrd.room_designer.item;

import com.aarrd.room_designer.favourite.Favourite;
import com.aarrd.room_designer.image.Image;
import com.aarrd.room_designer.item.category.Category;
import com.aarrd.room_designer.item.statistic.download.ItemDownload;
import com.aarrd.room_designer.item.statistic.view.ItemView;
import com.aarrd.room_designer.item.type.Type;
import com.aarrd.room_designer.item.variant.ItemVariant;
import com.aarrd.room_designer.model.Model;
import com.aarrd.room_designer.user.User;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "item")
@Data
public class Item
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "has_model", nullable = false, columnDefinition = "BIT", length = 1)
    private Boolean hasModel;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @OneToOne(mappedBy = "item",orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private Model model;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "cat_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private ItemVariant itemVariant;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private List<Image> images;

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private List<ItemDownload> itemDownloads;

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private List<ItemView> itemViews;

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private List<Favourite> favourites;

    public Item(){}

    public Item(String name, String description, Date date, Boolean hasModel)
    {
        this.name = name;
        this.description = description;
        this.date = date;
        this.hasModel = hasModel;
    }

    public Item(Long itemId, String name, String description, Date date, Boolean hasModel) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.date = date;
        this.hasModel = hasModel;
    }

    public Item(String name, String description, Date date, Boolean hasModel, User user, Category category,
                ItemVariant itemVariant, Type type)
    {
        this.name = name;
        this.description = description;
        this.date = date;
        this.hasModel = hasModel;
        this.user = user;
        this.category = category;
        this.itemVariant = itemVariant;
        this.type = type;
    }
}
