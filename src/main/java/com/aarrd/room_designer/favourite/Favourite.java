package com.aarrd.room_designer.favourite;

import com.aarrd.room_designer.item.Item;
import com.aarrd.room_designer.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "favourite")
@Data
public class Favourite
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favourite_id", nullable = false)
    private Long favouriteId;

    @Temporal(TemporalType.DATE)
    @Column(name = "favourited_date", nullable = false)
    private Date favouriteDate;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "itemId")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Favourite(){}

    public Favourite(Date favouriteDate) {
        this.favouriteDate = favouriteDate;
    }

    public Favourite(Date favouriteDate, User user, Item item) {
        this.favouriteDate = favouriteDate;
        this.user = user;
        this.item = item;
    }
}
