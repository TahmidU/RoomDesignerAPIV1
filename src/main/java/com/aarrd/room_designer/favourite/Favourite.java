package com.aarrd.room_designer.favourite;

import com.aarrd.room_designer.item.Item;
import com.aarrd.room_designer.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "favourite")
@Data
public class Favourite
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favourite_id")
    private long favouriteId;

    @Temporal(TemporalType.DATE)
    @Column(name = "favourited_date",nullable = false)
    private Date favouriteDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public Favourite(){}

    public Favourite(Date favouriteDate) {
        this.favouriteDate = favouriteDate;
    }
}
